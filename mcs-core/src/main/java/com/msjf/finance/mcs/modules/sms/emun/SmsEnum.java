package com.msjf.finance.mcs.modules.sms.emun;

import com.msjf.finance.msjf.core.emun.ResponseService;

public enum SmsEnum implements ResponseService {
    MSG_PARAM_ERROR("MCS0001", "入参不合法"),
    TEMPLE_ID_NULL("MCS0002", "模板ID不能为空"),
    MOBILE_NULL("MCS0003", "手机号码不能为空"),
    SYSTEM_PARA_ERROR("MCS0004", "系统参数异常"),
    VALIDE_CODE_CHECK_ERROR("MCS0005", "记录流水失败"),
    ROLL_BACK_ERROR("MCS0006", "回滚失败"),
    SEND_SMS_FAILD("MCS0006", "短信发送失败"),
    TEMPLATE_CONTENT_NULL("MCS0007", "========短信模板为空=========="),
    TEMPLATE_NOT_OPEN("MCS0008", "请检查短信模板是否开启或配置"),
    SMS_CONFIG_NULL("MCS0009", "未查询到短信配置信息"),
    SMS_SEND_SUCCESS("MCS0010", "发送成功"),
    SMS_CONFIG_ERROR("MCS0011", "短信配置出错"),
    SMS_SEND_NETWORK_ERROR("MCS0012", "短信发送失败,网络异常"),
    TEMPLATE_KEYWORD_NULL("MCS0013", "模板关键字在模板中不存在"),
    TEMPLATE_KEYWORD_VALUE_NULL("MCS0013", "模板关键字的值不能为空");

    private String errorCode;
    private String errorDesc;

    SmsEnum(String errorCode, String errorDesc) {
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
