package com.hcf.nszh.common.exception;


import com.hcf.nszh.common.enums.ErrorEnum;

/**
 * @author hx
 * 业务异常
 */
public class BusinessException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 2897424491337847469L;

    /**
     * 错误码
     */
    private int errorCode;
    /**
     * 信息
     */
    private String message;

    public BusinessException(String message) {
        super(message);
        this.setMessage(message);
    }

    public BusinessException(int errorCode, String message) {
        super(message);
        this.setErrorCode(errorCode);
        this.setMessage(message);
    }

    public BusinessException(ErrorEnum errorEnum) {
        super(errorEnum.getMessage());
        this.setErrorCode(errorEnum.getErrorCode());
        this.setMessage(errorEnum.getMessage());
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.setErrorCode(errorCode);
        this.setMessage(message);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
