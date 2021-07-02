package com.hcf.nszh.common.security.shiro.filter;

import com.hcf.nszh.common.security.shiro.realm.FbsShiroRealm;
import com.hcf.nszh.common.security.shiro.condition.FbsShiroCondition;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.crazycake.shiro.RedisCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;
@Slf4j
@Conditional(FbsShiroCondition.class)
@Component
public class KickoutSessionControlFilter  extends AccessControlFilter {

    /**
     * 踢出后到的地址
     */
    private String kickoutUrl;

    /**
     * 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
     */
    private boolean kickoutAfter = false;
    /**
     * 同一个帐号最大会话数 默认1
     */
    private int maxSession = 1;

    private String kickoutAttrName = "kickout";
    @Autowired
    DefaultWebSessionManager defaultWebSessionManager;
    private Cache<String, Deque<Serializable>> cache;

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(DefaultWebSessionManager defaultWebSessionManager) {
        this.defaultWebSessionManager = defaultWebSessionManager;
    }

    /**
     *  设置Cache的key的前缀
     */
    public void setCacheManager(RedisCacheManager cacheManager) {
        this.cache = cacheManager.getCache("ssqb-shiro-kickout-session");
    }

    @Override protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        Subject subject = getSubject(request, response);
        if(!subject.isAuthenticated() && !subject.isRemembered())
        {
            //如果没有登录，直接进行之后的流程
            return true;
        }
        // 判断当前用户登陆数量是否超出
        Session session = subject.getSession();
        FbsShiroRealm.Principal principal =  (FbsShiroRealm.Principal) subject.getPrincipal();


        String loginName = principal.getLoginName();
        Serializable sessionId = session.getId();
        log.info("sessionId:"+sessionId);
        //读取缓存 没有就存入
        Deque<Serializable> deque = cache.get(loginName);
        if(deque == null || deque.size() ==0) {
            deque = new LinkedList<>();
            cache.put(loginName, deque);
        }

        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if(!deque.contains(sessionId) && session.getAttribute(kickoutAttrName) == null) {
            //将sessionId存入队列
            deque.push(sessionId);
            cache.put(loginName, deque);
        }
        //如果队列里的sessionId数超出最大会话数，开始踢人
        while(deque.size() > maxSession) {
            Serializable kickoutSessionId ;
            if(kickoutAfter) {
                //如果踢出后者
                kickoutSessionId = deque.removeFirst();
            } else {
                //否则踢出前者
                kickoutSessionId = deque.removeLast();
            }

            //踢出后再更新下缓存队列
            cache.put(loginName, deque);
            try {
                //获取被踢出的sessionId的session对象
                Session kickoutSession = defaultWebSessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if(kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute(kickoutAttrName, true);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        //如果被踢出了，直接退出，重定向到踢出后的地址
        if (session.getAttribute(kickoutAttrName) != null && (Boolean)session.getAttribute(kickoutAttrName) == true) {
            //会话被踢出了
            try {
                //退出登录
                subject.logout();
                // 根据请求类型作出处理
                HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-with"))) {
                    // 普通请求
                    //直接跳转
                    WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                } else {
                    // ajax 请求
                    httpServletResponse.setStatus(430);
                }

            } catch (Exception e) {
                // do nothing
            }
            return false;
        }
        return true;
    }
}
