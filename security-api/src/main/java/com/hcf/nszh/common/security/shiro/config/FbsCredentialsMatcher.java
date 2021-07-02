package com.hcf.nszh.common.security.shiro.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author huangxiong
 * @Date 2019/7/8
 **/
public class FbsCredentialsMatcher extends HashedCredentialsMatcher {
    @Autowired
    private ShiroProperties shiroProperties;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        if(shiroProperties.getNoPassword() == true){
            return true;
        }
        boolean matches = super.doCredentialsMatch(authcToken, info);
        return matches;
    }
}
