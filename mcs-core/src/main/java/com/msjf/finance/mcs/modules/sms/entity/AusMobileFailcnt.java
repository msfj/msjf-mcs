package com.msjf.finance.mcs.modules.sms.entity;

public class AusMobileFailcnt {
    private String mobile;

    private String failcnt;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getFailcnt() {
        return failcnt;
    }

    public void setFailcnt(String failcnt) {
        this.failcnt = failcnt == null ? null : failcnt.trim();
    }
}