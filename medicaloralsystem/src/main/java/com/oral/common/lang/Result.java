package com.oral.common.lang;

import lombok.Data;

/**
 * @author tanyongpeng
 * @Date 2021/7/4 18:08
 * @Version 1.0
 *
 * 数据封装类
 */
@Data
public class Result {

    private int code;
    private String msg;
    private Object data;

    public static Result success(Object data) {
        return success(200,"操作成功",data);
    }

    public static Result success(int code, String msg, Object data) {
       Result r = new Result();
       r.setCode(code);
       r.setMsg(msg);
       r.setData(data);
       return r;
    }

    public static Result fail(String msg) {
        return fail(400,msg,null);
    }

    public static Result fail(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
}
