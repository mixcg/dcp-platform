package com.dunzung.cloud.framework.rest;

import java.util.HashMap;
import java.util.Map;

public class R extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public R() {
        put("code", ResponseCode.SUCCESS.getCode());
        put("msg", ResponseCode.SUCCESS.getMessage());
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public static R error() {
        return error(ResponseCode.INTERNAL_ERROR.getCode(), ResponseCode.INTERNAL_ERROR.getMessage());
    }

    public static R error(String msg) {
        return error(ResponseCode.INTERNAL_ERROR.getCode(), msg);
    }

    public static R error(Integer code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R error(ResponseCode aa) {
        R r = new R();
        r.put("code", aa.getCode());
        r.put("msg", aa.getMessage());
        return r;
    }

    public R data(Object data) {
        super.put("data", data);
        return this;
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static R noright( String msg) {
        R r = new R();
        r.put("code", ResponseCode.NOT_AUTHORIZED.getCode());
        r.put("msg", msg);
        return r;
    }
}
