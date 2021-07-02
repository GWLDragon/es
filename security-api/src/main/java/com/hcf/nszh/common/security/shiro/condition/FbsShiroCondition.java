package com.hcf.nszh.common.security.shiro.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

/**
 * @Author huangxiong
 * @Date 2019/6/29
 **/
@Component
public class FbsShiroCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        if(null==context.getEnvironment().getProperty("fbs.shiro.enabled")||
                context.getEnvironment().getProperty("fbs.shiro.enabled").isEmpty()) {
            return false;
        }
        return "true".equals(context.getEnvironment().getProperty("fbs.shiro.enabled"));
    }
}
