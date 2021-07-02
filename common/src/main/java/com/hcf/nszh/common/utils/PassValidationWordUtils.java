package com.hcf.nszh.common.utils;

import com.hcf.nszh.common.enums.NumberEnum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 20191116
 *
 * @author maruko
 */
public class PassValidationWordUtils {


    /**
     * 允许键盘最大连续次数
     */
    private static int maxSeqCnt = 3;

    private static String[] SEQSTR = {"qwertyuiopasdfghjklzxcvbnm",
            "QWERYUIOPASDFGHJKLZXCVBNM",
            "1234567890-=",
            "qwertyuiop[]\\asdfghjkl;'zxcvbnm,./",
    };
    private static final String PW_PATTERN = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";
    private static final Pattern PATTERN = Pattern.compile("(\\w)\\1{2,}");

    /**
     * 判断连续字符串、连续键盘,返回true代表密码不符合规定
     */
    public static boolean isSeqString(String str) {
        Matcher m = PATTERN.matcher(str);
        boolean result = m.find();
        if (result) {
            return false;

        }
        int size = str.length();
        String temp;
        int offSet;
        for (String s : SEQSTR) {
            //提前定义的连续字符串
            offSet = 0;
            for (int i = maxSeqCnt; i <= size; i++) {
                //直接从最大允许的字符开始截取字符
                temp = str.substring(offSet++, i);
                if (s.contains(temp)) {
                    //出现连续字符，直接返回true了
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean continuouskb(String str) {
        str = str.toLowerCase();

        char[][] c1 = {
                {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+'},
                {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '{', '}', '|'},
                {'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', ':', '"'},
                {'z', 'x', 'c', 'v', 'b', 'n', 'm', '<', '>', '?'}
        };
        char[][] c2 = {
                {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-', '='},
                {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '{', '}', '\\'},
                {'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', ';', '\''},
                {'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/'}
        };

        for (char[][] c : new char[][][]{c1, c2}) {

            //横向
            for (int i = 0; i < c.length; i++) {
                for (int j = 0; j < c[i].length - NumberEnum.TWO; j++) {
                    //创建连续字符
                    StringBuilder sb = new StringBuilder();
                    for (int k = j; k < j + NumberEnum.THREE; k++) {
                        sb.append(c[i][k]);
                    }
                    String keyStr = sb.toString();
                    if (str.contains(keyStr)) {
                        return true;
                    }
                }
            }

            //纵向
            for (int i = 0; i < c[NumberEnum.THREE].length; i++) {
                //创建连续字符--每列只有4个
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < NumberEnum.FOUR; j++) {
                    sb.append(c[j][i]);
                }
                String keyStr = sb.toString();

                String z = keyStr.substring(0, 3);
                String zz = new StringBuilder(z).reverse().toString();
                String f = (new StringBuilder(keyStr).reverse().toString()).substring(0, 3);
                String ff = new StringBuilder(f).reverse().toString();
                if (str.contains(keyStr) || str.contains(z) || str.contains(f) || str.contains(zz) || str.contains(ff)) {
                    return true;
                }
            }

        }


        return false;

    }

    /**
     * 密码至少8位，且至少包含字母、特殊字符、数字、大小，返回true代表密码符合
     *
     */
    public static boolean isTruePassword(String str) {
        return str.matches(PW_PATTERN);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        String s = "aa011a";
        Matcher m = PATTERN.matcher(s);
        System.out.println(m);
        System.out.println(continuouskb("#ED"));
        System.out.println(continuouskb("VFR"));
        System.out.println(continuouskb("1QA"));
        System.out.println(continuouskb("12q"));

    }
}