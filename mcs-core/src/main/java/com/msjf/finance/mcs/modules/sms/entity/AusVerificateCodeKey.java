package com.msjf.finance.mcs.modules.sms.entity;

public class AusVerificateCodeKey {
    private String mobile;

    private String verificatetype;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getVerificatetype() {
        return verificatetype;
    }

    public void setVerificatetype(String verificatetype) {
        this.verificatetype = verificatetype == null ? null : verificatetype.trim();
    }
}