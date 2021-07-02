package com.hcf.nszh.common.constant;

/**
 * @Author hx
 * @Date 2019/7/1
 **/
public class SystemConstant {
    /**
     * 删除标记（0：正常；1：删除；2：审核；）
     */
    public static final String DEL_FLAG_NORMAL = "0";
    public static final String DEL_FLAG_DELETE = "1";

    /**
     * 是/否
     */
    public static final String YES = "1";
    public static final String NO = "0";

    public static final String USER_HEADER_TOKEN = "fbsToken";

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;

    /**
     * 密钥 (需要前端和后端保持一致)
     */
    public static final String AES_KEY = "abcdefgabcdefg12";
    /**
     * 算法
     */
    public static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";
    public static final int SALT_SIZE = 8;
}
