package com.dunzung.cloud.framework.exception;

/**
 * Created by Wooola on 2018/9/8.
 */
public class PortalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;

    private int code = 500;

    public PortalException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public PortalException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public PortalException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public PortalException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
