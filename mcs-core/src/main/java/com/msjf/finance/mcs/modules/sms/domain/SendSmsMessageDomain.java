package com.msjf.finance.mcs.modules.sms.domain;

import com.msjf.finance.msjf.core.domian.BaseDomain;

public class SendSmsMessageDomain extends BaseDomain {
    private String result;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
