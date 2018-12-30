package com.msjf.finance.mcs.modules.utils;

public enum  ResultEnum {
    SUCCESS("01", "成功"),
    ERROR("00", "失败"),

    WAIT("1111", "正在处理结果");

    private String code;

    private String msg;

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
