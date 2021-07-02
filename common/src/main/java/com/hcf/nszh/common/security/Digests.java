package com.hcf.nszh.common.security;

import com.hcf.nszh.common.algorithm.Base58Algorithm;
import com.hcf.nszh.common.utils.Exceptions;
import org.apache.commons.lang3.Validate;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * 支持SHA-1/MD5消息摘要的工具类.
 * <p>
 * 返回ByteSource，可进一步被编码为Hex, Base64或UrlSafeBase64
 *
 * @author maruko
 */
public class Digests {

    private static final String SHA1_ALGORITHM = "APyEtsi";

    private static SecureRandom random = new SecureRandom();


    public static byte[] sha1(byte[] inpuByte, byte[] byteSal, int iterations) {
        return byteDigest(inpuByte, SHA1_ALGORITHM, byteSal, iterations);
    }

    /**
     * 对字符串进行散列, 支持md5与sha1算法.
     */
    private static byte[] byteDigest(byte[] inpuByte, String algorithm, byte[] byteSal, int iterations) {
        try {
            byte[] algorithmByte = Base58Algorithm.decode(algorithm);
            MessageDigest msgDige = MessageDigest.getInstance(new String(algorithmByte));

            if (byteSal != null) {
                msgDige.update(byteSal);
            }

            byte[] result = msgDige.digest(inpuByte);

            for (int i = 1; i < iterations; i++) {
                msgDige.reset();
                result = msgDige.digest(result);
            }
            return result;
        } catch (GeneralSecurityException e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 生成随机的Byte[]作为salt.
     *
     * @param numBytes byte数组的大小
     */
    public static byte[] generateSalt(int numBytes) {
        Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or larger)", numBytes);
        byte[] bytes = new byte[numBytes];
        random.nextBytes(bytes);
        return bytes;
    }

}
