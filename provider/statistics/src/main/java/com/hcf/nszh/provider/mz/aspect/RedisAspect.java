package com.hcf.nszh.provider.mz.aspect;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.provider.mz.annotation.ClearCache;
import com.hcf.nszh.provider.mz.annotation.PutCache;
import com.hcf.nszh.provider.mz.annotation.RemoveCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 定义一个通知
 *
 * @author lc
 * @Component: 被spring容器管理(此类在高并发应该加读写锁)
 * @Aspect: 声明一个切面(切点 + 通知)
 * @date 2019-01-03
 */
@Component
@Aspect
@Slf4j
public class RedisAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 配置环绕通知 + annotation的切点表达式
     *
     * @throws Throwable
     * @annotation 注解形式的通知, 那个方法上添加了该注解, 则执行该通知
     */
    @Around("@annotation(cache)")
    public Object around(ProceedingJoinPoint joinPoint, PutCache cache) throws Throwable {

        log.debug("方法执行前，先判断是否有缓存");
        /**
         * 确定redis的 key 和 value key : 类名 + 方法名 + 参数列表(确定唯一的key) value : 对象序列化的的byte数组(redis天然支持byte数组)
         */

        // 包名
        String packageName = joinPoint.getTarget().getClass().getPackage().getName();
        // 类名
        String className = joinPoint.getTarget().getClass().getSimpleName();

        // 组装key
        String key = getCacheKey(joinPoint, className);
        log.debug("获取到缓存key，缓存的key={}", key);
        int flushTime = cache.flushTime();

        String cacheKey = packageName + ":" + key;
        String cacheValue = stringRedisTemplate.opsForValue().get(cacheKey);
        log.debug("从缓存中获取数据结果，key={},cacheValue={}", key, cacheValue);
        // 判断是否获取到数据
        if (null == cacheValue) {
            // 如果缓存中没有数据, 则代表缓存穿透, 则去数据库查询, 再将数据存入缓存; 放行, 执行目标方法 proceed = 返回值目标对象
            Object returnValue = joinPoint.proceed();

            // controller 做特殊处理
            if (returnValue instanceof ResponseVo) {
                ResponseVo<?> resultDto = (ResponseVo<?>) returnValue;
                if (ErrorEnum.SUCCESS.getErrorCode() != resultDto.getCode()) {
                    log.debug("在controller中出现异常，直接返回，不做缓存处理");
                    return cacheValue;
                }
            }

            log.debug(String.format("将key=%s的缓存放入redis", key));
            stringRedisTemplate.opsForValue().set(cacheKey, JSON.toJSONString(returnValue, SerializerFeature.WriteClassName), flushTime, TimeUnit.SECONDS);

            // 将查询到的数据返回
            return returnValue;
        } else {
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method targetMethod = methodSignature.getMethod();

            Class<?> returnType = targetMethod.getReturnType();
            return JSON.parseObject(cacheValue, returnType);
        }
    }

    @Around("@annotation(cache)")
    public Object remove(ProceedingJoinPoint joinPoint, RemoveCache cache) throws Throwable {
        // 等方法执行完成在进行缓存删除操作
        Object proceed = joinPoint.proceed();
        // 类名
        String className = joinPoint.getTarget().getClass().getName();

        String cacheKey = cache.key();
        String methodName = cache.methodName();
        String key = null;
        String cacheId = null;
        if (StringUtils.isNotBlank(cacheKey)) {
            // 有方法全路径的时候，从全路径中提起类名
            if (cacheKey.contains(".")) {
                cacheId = cacheKey.substring(0, cacheKey.lastIndexOf("."));
            } else {
                key = cacheKey;
                cacheId = className;
            }
        } else if (StringUtils.isNotBlank(methodName)) {
            key = className + "." + methodName;
            cacheId = className;
        } else {
            key = getCacheKey(joinPoint, className);
            cacheId = className;
        }
        stringRedisTemplate.delete(key);
        return proceed;
    }

    @Around("@annotation(clearCache)")
    public Object clear(ProceedingJoinPoint joinPoint, ClearCache clearCache) throws Throwable {
        // 等方法执行完成在进行缓存删除操作
        Object proceed = joinPoint.proceed();
        String cacheId = clearCache.cacheId();

        if (StringUtils.isBlank(cacheId)) {
            // 类名
            String className = joinPoint.getTarget().getClass().getName();
            cacheId = className;
        }
        stringRedisTemplate.delete(cacheId);
        return proceed;
    }

    /**
     * 组装缓存key
     *
     * @param joinPoint
     * @param className
     * @return
     */
    private String getCacheKey(ProceedingJoinPoint joinPoint, String className) {
        StringBuilder sb = new StringBuilder();
        // 方法名
        String methodName = joinPoint.getSignature().getName();
        // 拼接字符串
        sb.append(className).append(": ").append(methodName);

        // 循环遍历得到参数的类型
        Object[] args = joinPoint.getArgs();
        // 判断避免空指针
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                sb.append(":").append(JSON.toJSONString(arg));
            }
        }
        // 最终得到的key
        String key = sb.toString();
        return key;
    }
}
