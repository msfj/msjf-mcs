package com.msjf.finance.mcs.modules.utils.emun;

import com.msjf.finance.msjf.core.emun.ResponseService;

public enum  CommonUtilEmun implements ResponseService {
    MSG_PARAM_ERROR("MCS0001", "入参不合法"),
    MSG_PARAM_NULL("MCS0002", "入参不能为空"),
    VALIDE_CODE_NULL("MCS0003", "请先获取验证码"),
    VALIDE_CODE_ERROR("MCS0004", "验证码有误"),
    VALIDE_CODE_IS_USED("MCS0005", "验证码已校验，请勿重复使用"),
    VALIDE_CODE_OVRE_TIME("MCS0006", "验证码已失效，请重新获取"),
    SEND_SMS_FAILD("MCS0006", "短信发送失败");
    private String errorCode;
    private String errorDesc;

    CommonUtilEmun(String errorCode, String errorDesc) {
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
        return null;
    }

    @Override
    public String getResponseMessage() {
        return null;
    }
}
