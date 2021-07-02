package com.hcf.nszh.common.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author maruko
 * @Date 2019/6/13 0013
 * @Order 控制多个Aspect的执行顺序，越小越先执行
 **/
@Aspect
@Order(-99)
@Slf4j
@Component
public class RequestLoggingAspect {

    @Around(value = "within(com..*) " +
            "&& (@annotation(org.springframework.web.bind.annotation.ResponseBody)" +
            "|| @annotation(org.springframework.web.bind.annotation.RequestMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.GetMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping))" +
            "&& @annotation(com.hcf.nszh.common.annotation.RequestLogging)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        // 定义返回对象、得到方法需要的参数
        Object obj = null;
        Object[] args = joinPoint.getArgs();
        List<Object> logArgs = Arrays.stream(joinPoint.getArgs())
                .filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                .collect(Collectors.toList());
        long startTime = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        try {
            obj = joinPoint.proceed(args);
        }finally {
            long endTime = System.currentTimeMillis();
            long diffTime = endTime - startTime;
            log.info("公共日志调用的方法：{},入参：{},返回值:{},执行耗时:{}毫秒",methodName,
                    JSON.toJSONString(logArgs),obj!=null? JSON.toJSONString(obj):obj,diffTime);
        }
        return obj;
    }
}
