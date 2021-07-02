package com.hcf.nszh.common.utils;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author  Furant
 * 2019/7/16 15:25
 */
public class UUIDUtils implements SessionIdGenerator {

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Override
    public Serializable generateId(Session session) {
        return UUIDUtils.uuid();
    }
}
