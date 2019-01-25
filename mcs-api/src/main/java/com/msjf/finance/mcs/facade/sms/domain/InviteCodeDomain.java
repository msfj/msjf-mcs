package com.msjf.finance.mcs.facade.sms.domain;

/**
 * Created by kanglong on 2019/1/25.
 */
public class InviteCodeDomain {
    private String serialno;
    private String issendSms;
    private String inviteCode;

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public String getIssendSms() {
        return issendSms;
    }

    public void setIssendSms(String issendSms) {
        this.issendSms = issendSms;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
