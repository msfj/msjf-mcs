package com.msjf.finance.mcs.modules.sms.entity;

import com.msjf.finance.mcs.common.response.BaseEntity;

public class SpmMessageEntity extends BaseEntity {
    private String seqNum;

    private String title;

    private String email;

    private String mobilephone;

    private String customerno;

    private String scheduletime;

    private String sendchanel;

    private String senddisplay;

    private String businessid;

    private String senddate;

    private String sendmailstatus;

    private String sendnotestatus;

    private String readstatus;

    private Long sendnotecount;

    private Long sendmailcount;

    private String messageparam;

    public String getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(String seqNum) {
        this.seqNum = seqNum == null ? null : seqNum.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone == null ? null : mobilephone.trim();
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }

    public String getScheduletime() {
        return scheduletime;
    }

    public void setScheduletime(String scheduletime) {
        this.scheduletime = scheduletime == null ? null : scheduletime.trim();
    }

    public String getSendchanel() {
        return sendchanel;
    }

    public void setSendchanel(String sendchanel) {
        this.sendchanel = sendchanel == null ? null : sendchanel.trim();
    }

    public String getSenddisplay() {
        return senddisplay;
    }

    public void setSenddisplay(String senddisplay) {
        this.senddisplay = senddisplay == null ? null : senddisplay.trim();
    }

    public String getBusinessid() {
        return businessid;
    }

    public void setBusinessid(String businessid) {
        this.businessid = businessid == null ? null : businessid.trim();
    }

    public String getSenddate() {
        return senddate;
    }

    public void setSenddate(String senddate) {
        this.senddate = senddate == null ? null : senddate.trim();
    }

    public String getSendmailstatus() {
        return sendmailstatus;
    }

    public void setSendmailstatus(String sendmailstatus) {
        this.sendmailstatus = sendmailstatus == null ? null : sendmailstatus.trim();
    }

    public String getSendnotestatus() {
        return sendnotestatus;
    }

    public void setSendnotestatus(String sendnotestatus) {
        this.sendnotestatus = sendnotestatus == null ? null : sendnotestatus.trim();
    }

    public String getReadstatus() {
        return readstatus;
    }

    public void setReadstatus(String readstatus) {
        this.readstatus = readstatus == null ? null : readstatus.trim();
    }

    public Long getSendnotecount() {
        return sendnotecount;
    }

    public void setSendnotecount(Long sendnotecount) {
        this.sendnotecount = sendnotecount;
    }

    public Long getSendmailcount() {
        return sendmailcount;
    }

    public void setSendmailcount(Long sendmailcount) {
        this.sendmailcount = sendmailcount;
    }

    public String getMessageparam() {
        return messageparam;
    }

    public void setMessageparam(String messageparam) {
        this.messageparam = messageparam == null ? null : messageparam.trim();
    }
}