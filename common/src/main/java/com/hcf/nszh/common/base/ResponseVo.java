package com.hcf.nszh.common.base;


import com.hcf.nszh.common.enums.ErrorEnum;

import java.io.Serializable;

/**
 * @author Administrator on 2018/7/5 0005.
 */
public class ResponseVo<T> implements Serializable {

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误信息
     */
    private String error;

    /**
     * 返回数据
     */
    private T data;

    public ResponseVo() {
        super();
    }

    public ResponseVo(ErrorEnum errorEnum) {
        this(errorEnum, null);
    }

    public ResponseVo(ErrorEnum errorEnum, T data) {
        this.code = errorEnum.getErrorCode();
        this.error = errorEnum.getMessage();
        this.data = data;
    }

    public ResponseVo(int errorCode, String message) {
        this.code = errorCode;
        this.error = message;
    }

    public ResponseVo(int errorCode, String message, T data) {
        this.code = errorCode;
        this.error = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
