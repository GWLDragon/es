package com.hcf.nszh.provider.mz.enums;

import com.hcf.nszh.provider.mz.vo.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author gwl
 * @date 2021/6/7 17:16
 * @since 1.0.0
 */

public enum TypeEnum {

    FWQK(1, 0, 2, FwqkVO.class, "服务趋势（智能客服）数据为空，导入失败"),
    RD(2, 1, 1, RdVO.class, "热点问题、咨询热词（智能客服）数据为空，导入失败"),
    ZS(3, 2, 1, ZsVO.class, "知识总量、知识类别（智能客服）数据为空，导入失败"),
    ZSZL(4, 2, 1, ZszlVO.class, "知识总量、知识类别（智能客服）数据为空，导入失败"),
    RDWT(5, 3, 1, RdwtVO.class, "热点问题（数据展示平台）数据为空，导入失败");
    private int type;

    private int sheet;

    private int headRowNumber;

    private Class classType;

    private String message;

    public static int getSheetByType(int type) {
        for (TypeEnum c : TypeEnum.values()) {
            if (c.getType() == type) {
                return c.sheet;
            }
        }
        return 0;
    }

    public static int getHeadRowNumberByType(int type) {
        for (TypeEnum c : TypeEnum.values()) {
            if (c.getType() == type) {
                return c.headRowNumber;
            }
        }
        return 0;
    }

    public static Class getClassTypeByType(int type) {
        for (TypeEnum c : TypeEnum.values()) {
            if (c.getType() == type) {
                return c.classType;
            }
        }
        return null;
    }

    public static String getMessageByType(int type) {
        for (TypeEnum c : TypeEnum.values()) {
            if (c.getType() == type) {
                return c.message;
            }
        }
        return null;
    }

    TypeEnum(int type, int sheet, int headRowNumber, Class classType, String message) {
        this.type = type;
        this.sheet = sheet;
        this.headRowNumber = headRowNumber;
        this.classType = classType;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSheet() {
        return sheet;
    }

    public void setSheet(int sheet) {
        this.sheet = sheet;
    }

    public int getHeadRowNumber() {
        return headRowNumber;
    }

    public void setHeadRowNumber(int headRowNumber) {
        this.headRowNumber = headRowNumber;
    }

    public Class getClassType() {
        return classType;
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }}
