package com.hcf.nszh.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 20191116
 *
 * @author ygr
 */
public class PassValidationWordUtils {
    static int maxSeqCnt = 3;//允许键盘最大连续次数
    static String seqStr[] = {"qwertyuiopasdfghjklzxcvbnm",
            "QWERYUIOPASDFGHJKLZXCVBNM",
            "1234567890-=",
            "qwertyuiop[]\\asdfghjkl;'zxcvbnm,./",
    };
    static final String PW_PATTERN = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";
    static final Pattern p = Pattern.compile("(\\w)\\1{2,}");

    /**
     * 判断连续字符串、连续键盘,返回true代表密码不符合规定
     */
    public static boolean isSeqString(String str) {
        Matcher m = p.matcher(str);
        boolean result = m.find() ? true : false;
        if (result) {
            return result;

        }
        int size = str.length();
        String temp = "";
        int offet = 0;
        for (String s : seqStr) {//提前定义的连续字符串
            offet = 0;
            for (int i = maxSeqCnt; i <= size; i++) {//直接从最大允许的字符开始截取字符
                temp = str.substring(offet++, i);
                if (s.contains(temp)) {//出现连续字符，直接返回true了
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean continuousKB1(String str) {
        str = str.toLowerCase();
        boolean result = false;
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
                for (int j = 0; j < c[i].length - 2; j++) {
                    //创建连续字符
                    StringBuffer sb = new StringBuffer();
                    for (int k = j; k < j + 3; k++) {
                        sb.append(c[i][k]);
                    }
                    String keyStr = sb.toString();
                    if (str.contains(keyStr)) {
                        return true;
                    }
                }
            }

            //纵向
            for (int i = 0; i < c[3].length; i++) {
                //创建连续字符--每列只有4个
                StringBuffer sb = new StringBuffer();
                for (int j = 0; j < 4; j++) {
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


        return result;

    }

    /**
     * 密码至少8位，且至少包含字母、特殊字符、数字、大小，返回true代表密码符合
     *
     * @param str
     * @return
     */
    public static boolean isTruePassword(String str) {
        return str.matches(PW_PATTERN);
    }

    /**
     * 测试
     */
    public static void main(String[] args) throws Exception {
        String pw1 = "ABCDEFGHIG";
        String s = "aa011a";
        Matcher m = p.matcher(s);
        System.out.println(continuousKB1("#ED"));
        System.out.println(continuousKB1("VFR"));
        System.out.println(continuousKB1("1QA"));
        System.out.println(continuousKB1("12q"));

    }
}