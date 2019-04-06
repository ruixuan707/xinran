package com.monco.common.bean;

/**
 * ApiResult
 *
 * @author Lijin
 * @version 1.0.0
 */
public class ApiResult {

    /** 响应业务状态 */
    private Integer code;

    /** 响应消息 */
    private String msg;

    /** 响应中的数据 */
    private Object data;

    public ApiResult() {
        this.msg = "OK";
        this.code = 200;
    }

    public ApiResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ApiResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiResult(Object data) {
        this.code = 200;
        this.msg = "OK";
        this.data = data;
    }

    public static ApiResult build(Integer status, String msg, Object data) {
        return new ApiResult(status, msg, data);
    }

    public static ApiResult ok(Object data) {
        return new ApiResult(data);
    }

    public static ApiResult ok() {
        return new ApiResult(null);
    }

    public static ApiResult error(String msg) {
        return new ApiResult(500, msg, null);
    }

    public static ApiResult unauthorized(String msg) {
        return new ApiResult(401, msg, null);
    }

    public static ApiResult build(Integer status, String msg) {
        return new ApiResult(status, msg, null);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}