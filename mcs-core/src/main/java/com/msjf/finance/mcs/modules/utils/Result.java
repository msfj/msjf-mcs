package com.msjf.finance.mcs.modules.utils;

import com.alibaba.fastjson.JSONObject;

public class Result<T> {
    public static final String CODE = "code";
    public static final String MSG = "message";
    public static final String DATA = "data";
    /**
     * 错误码
     */
    private String code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 具体的内容
     */
    private T data;

    private JSONObject Resultjson;

    /**
     * 成功时候的调用
     * @param
     * @return
     */
    public void success(T data) {
        this.msg=ResultEnum.SUCCESS.getMsg();
        this.code=ResultEnum.SUCCESS.getCode();
        this.data=data;
    }
    /**
     * 成功时候的调用
     * @param
     * @return
     */
    public void success(String Msg,T data) {
        this.msg=Msg;
        this.code=ResultEnum.SUCCESS.getCode();
        this.data=data;
    }
    /**
     * 失败的调用
     * @param codeMsg
     * @param
     * @return
     */
    public void failed(String codeMsg) {
        this.msg=ResultEnum.ERROR.getMsg();
        this.code=codeMsg;
    }
    /**
     * 失败的调用 将返回结果传入
     * @param data
     * @param
     * @return
     */
    public void error(String codeMsg,T data) {
        this.msg=ResultEnum.ERROR.getMsg();
        this.code=codeMsg;
    }
    public JSONObject createResp(ResultEnum resultEnum) {
        JSONObject result = new JSONObject();
        result.put(CODE, resultEnum.getCode());
        result.put(MSG, resultEnum.getMsg());
        return result;
    }
    /**
     * 根据返回的状态对象， 构建返回结果
     * @param resultEnum
     * @param <T>
     * @return
     */
    public static <T> Result<T> build(ResultEnum resultEnum) {
        return new Result<>(resultEnum);
    }

    /**
     * 根据 code， 和  msg 构建返回结果
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> build(String code, String msg) {
        return new Result<T>(code, msg);
    }

    /**
     * 根据 code， 和  msg, 和 data 构建返回结果
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> build(String code, String msg, T data) {
        return new Result<T>(code, msg, data);
    }

    public Result() {
        this.code = ResultEnum.SUCCESS.getCode();
        this.msg = ResultEnum.SUCCESS.getMsg();
    }
    private Result(T data) {
        this.code = ResultEnum.SUCCESS.getCode();
        this.msg = ResultEnum.SUCCESS.getMsg();
        this.data = data;
    }

    private Result(String msg) {
        this.code = ResultEnum.ERROR.getCode();
        this.data = null;
        this.msg = msg;
    }

    private Result(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    private Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}