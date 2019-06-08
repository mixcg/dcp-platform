package com.dunzung.cloud.framework.mvc.rest;

public enum ResponseCode {

    VALIDATION_ERROR(10001, "验证错误"),

    JSON_ERROR(10002, "解析错误"),

    // 参数错误
    PARAM_ERROR(10003, "参数错误"),

    // 必要的请求参数不能为空
    REQ_CANNOT_EMPTY(10004, "必要的请求参数不能为空"),

    //用户不存在
    USER_DOES_NOT_EXIST(10005, "用户不存在"),

    //数据查询成功
    QUERY_SUCCESS(10006, "数据查询成功"),

    //无数据或者结果
    NO_DATA(10008, "无数据或者结果"),

    //数据内容不合法
    ERROR_DATA(10007, "数据内容不合法"),

    //操作成功
    SUCCESS(200, "操作成功"),

    //操作成功
    FAILURE(-1, "操作成功"),

    //未登录异常
    UNLOGIN_ERROR(421, "未登录异常"),

    //无权访问该系统
    NO_PERMISSION(403, "无权访问该系统"),

    //系统内部异常
    INTERNAL_ERROR(500, "系统内部异常"),

    //上游服务端网关超时
    TIME_OUT(504, "上游服务端网关超时"),

    NOT_AUTHORIZED(11001, "鉴权错误"),

    REQUEST_FORBIDDEN(11002, "禁止访问"),

    USER_NOT_EXISTS_ERROR(11004, "用户不存在"),

    USER_EXISTS_ERROR(11005, "用户已存在"),

    SERVICE_NO_AVAILABLE(20001, "服务不可用");

    private Integer code; //状态码

    private String message; //返回消息

    private ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
