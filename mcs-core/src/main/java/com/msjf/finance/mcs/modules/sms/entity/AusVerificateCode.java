package com.msjf.finance.mcs.modules.sms.entity;

public class AusVerificateCode extends AusVerificateCodeKey {
    private String status;

    private String verificatecode;

    private String failuretime;

    private String customerno;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getVerificatecode() {
        return verificatecode;
    }

    public void setVerificatecode(String verificatecode) {
        this.verificatecode = verificatecode == null ? null : verificatecode.trim();
    }

    public String getFailuretime() {
        return failuretime;
    }

    public void setFailuretime(String failuretime) {
        this.failuretime = failuretime == null ? null : failuretime.trim();
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }
}