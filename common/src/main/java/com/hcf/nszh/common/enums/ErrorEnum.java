package com.hcf.nszh.common.enums;

import com.hcf.nszh.common.exception.BusinessException;

/**
 * @author hx
 */

public enum ErrorEnum {

    // 基础返回码 --- begin
    SUCCESS(0, "success"),
    FAILED(-1, "服务错误"),
    PARAMS_WRONG(-2, "参数错误或缺失"),
    TOKEN_INVALID(-3, "token 无效"),
    USER_SERVICE_ERROR(10001, "调用用户服务接口出错"),
    USER_PASSWORD_ERROR(10002, "登录密码错误"),
    USER_EXCESSIVE_ATTEMPTS(10003, "登录失败次数过多，请5分钟后再登录!"),
    USER_LOCK_ERROR(10004, "帐号已被锁定"),
    USER_DISABLED_ERROR(10005, "帐号已被禁用"),
    USER_EXPIRED_ERROR(10006, "帐号已过期"),
    USER_ACCOUNT_UNKNOWN(10007, "帐号不存在"),
    USER_NO_PERMISSION(10008, "您没有得到相应的授权！"),
    USER_NO_CAPTCHA(10009, "验证码有误"),
    USER_TOKEN_ERROR(10011, "token错误"),
    USER_ROLE_EMPTY(10012, "没设置用户角色"),
    USER_USRE_REPEAT(10013, "用户名重复"),
    USER_EMPTY(10015, "用户为空"),
    NOT_EXIST_ROLE(10016, "不存在该角色信息"),
    NAME_EXIST(10017, "名称已经存在"),
    CANNOT_DELETE(10018, "该菜单下存在子菜单，不能删除"),
    MENU_EXIST(10019, "菜单名重复"),
    SELECT_PARENT_OFFICE(10020, "请选择上级机构"),
    OFFICE_NOT_EXIST(10021, "上级机构不存在"),
    MENU_NOT_EXIST(10022, "上级菜单不存在"),
    OFFICE_CANNOT_DELETE_IN_USE(10023, "该机构正在使用，无法删除"),
    CHILD_EXIST_CANNOT_DELETE(10024, "该机构不可删除。只有最底层机构才可执行删除操作"),
    USER_PASSWORD_EMPTY(10025, "登录密码不能为空"),
    USER_NOT_EXIST(10026, "用户不存在"),
    USER_NOT_OFFICE(10027, "用户没有所属机构"),

    NOT_SELECT_OFFICE_EXIST(10028, "没有查询到相应的机构存在"),
    CODE_EXIST(10029, "编码已存在"),
    OFFICE_EXIST(10030, "机构名称重复");


    /**
     * 错误码
     */
    private int errorCode;

    /**
     * 错误信息
     */
    private String message;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ErrorEnum(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    ErrorEnum(BusinessException e) {
        this.errorCode = e.getErrorCode();
        this.message = e.getMessage();
    }
}
