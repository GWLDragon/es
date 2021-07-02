package com.hcf.nszh.consumer.system.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hcf.nszh.common.constant.CacheConstant;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.common.exception.BusinessException;
import com.hcf.nszh.common.security.shiro.utils.UserUtils;
import com.hcf.nszh.provider.system.api.vo.MenuVo;
import com.hcf.nszh.provider.system.api.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 填充service入参中lastUpdateBy的公共类
 * 
 * @author lc
 * @date 2018年11月26日
 */
@Order(5)
@Component
@Aspect
@Slf4j
public class FilterNonAuthor {

	private static List<JSONObject> authorJsonArray;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public FilterNonAuthor() throws IOException {
		Resource resource = new ClassPathResource("author.json");
		InputStream inputStream = resource.getInputStream();
		String authorJson = IOUtils.toString(inputStream);
		authorJsonArray = JSON.parseArray(authorJson, JSONObject.class);
	}

	@Around("execution(* com.hcf.nszh.consumer.system.controller.*.*(..))")
	public Object saveOpt(ProceedingJoinPoint joinPoint) throws Throwable {
		String methodName = joinPoint.getSignature().toString();
		if (methodName.contains("CaptchaController.getCaptcha") || methodName.contains("UserController.loginPost")
				|| methodName.contains("LoginController.") || methodName.contains("UserController.infoData")
				|| methodName.contains("AreaController.")) {
			return joinPoint.proceed();
		}
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

		if (null == requestAttributes) {
			// 可能是定时任务，不能获取到request
			return joinPoint.proceed();
		}
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		UserVo user = UserUtils.getUser();
		// 从缓存里取用户信息
		String cacheKey = CacheConstant.USER_CACHE + CacheConstant.USER_CACHE_LOGIN_NAME_ + user.getLoginName();
		UserVo userVo = JSON.parseObject(redisTemplate.opsForValue().get(cacheKey), UserVo.class);
		if (null == userVo) {
			log.warn("获取到用户缓存为空，key为：{}", cacheKey);
			return joinPoint.proceed();
		}
		List<MenuVo> menuVoList = userVo.getMenuVoList();
		// 防止没有存入
		if (null == menuVoList || menuVoList.isEmpty()) {
			return joinPoint.proceed();
		}
		for (MenuVo menuVo : menuVoList) {
			for (JSONObject obj : authorJsonArray) {
				if (obj.containsKey(menuVo.getHref())) {
					JSONArray array = (JSONArray) obj.get(menuVo.getHref());
					for (Object operateUri : array) {
						String operateUrl = operateUri.toString();
						if (operateUrl.contains(request.getRequestURI()) || request.getRequestURI().contains(operateUrl)) {
							return joinPoint.proceed();
						}
					}
					log.warn("访问方法：{}", methodName);
					throw new BusinessException(ErrorEnum.USER_NO_PERMISSION);
				}
			}
		}
		log.warn("访问方法：{}", methodName);
		throw new BusinessException(ErrorEnum.USER_NO_PERMISSION);
	}

}
