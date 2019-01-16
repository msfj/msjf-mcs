package com.msjf.finance.mcs.facade.sms.domain;

import com.msjf.finance.msjf.core.domian.BaseDomain;

public class ReqSendVerificationCodeDomain extends BaseDomain {
    private String mobile;
    private String templateId;
    private String verificateType;
    private String customerno;

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getVerificateType() {
        return verificateType;
    }

    public void setVerificateType(String verificateType) {
        this.verificateType = verificateType;
    }
}
