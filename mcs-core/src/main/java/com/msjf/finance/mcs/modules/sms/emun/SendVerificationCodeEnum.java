package com.msjf.finance.mcs.modules.sms.emun;

import com.msjf.finance.msjf.core.emun.ResponseService;

public enum SendVerificationCodeEnum implements ResponseService {
    MSG_PARAM_ERROR("MCS0001", "入参不合法"),
    TEMPLE_ID_NULL("MCS0002", "模板ID不能为空"),
    MOBILE_NULL("MCS0003", "手机号码不能为空"),
    SYSTEM_PARA_ERROR("MCS0004", "系统参数异常"),
    MSG_CHECK_EXCEPTION("MCS0005", "信息校验异常"),
    MSG_SEND_EXCEPTION("MCS0006", "发送信息异常"),
    SEND_SMS_FAILD("MCS0006", "短信发送失败"),
    OLD_MOBILE_CHECK_ERROR("MCS0007", "原手机号码校验不通过"),
    CHANGE_MOBILE_EXCEPTION("MCS0008", "换绑手机不能与原手机相同"),
    VERIFICATION_SUCCESS("MCS0009", "校验成功"),
    MSGCODE_NOT_EXIST("MCS0010", "校验码不存在"),
    MSGCODE_NULL("MCS0011", "验证码不能为空"),
    CUSTOMERNO_NULL("MCS0012", "客户代码不能为空"),
    OLD_MOBILE_NULL("MCS0013", "原手机不能为空"),
    VERIFICATE_TYPE_NULL("MCS0013", "认证类型不能为空");

    private String errorCode;
    private String errorDesc;

    SendVerificationCodeEnum(String errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    @Override
    public String getResponseCode() {
        return errorCode;
    }

    @Override
    public String getResponseMessage() {
        return errorDesc;
    }
}
