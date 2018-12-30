package com.msjf.finance.mcs.modules.sms.entity;

public class SpmMessageEntityWithBLOBs extends SpmMessageEntity {
    private String messagecontent;

    private String messageRemark;

    private String sendmailreply;

    private String sendnotereply;

    public String getMessagecontent() {
        return messagecontent;
    }

    public void setMessagecontent(String messagecontent) {
        this.messagecontent = messagecontent == null ? null : messagecontent.trim();
    }

    public String getMessageRemark() {
        return messageRemark;
    }

    public void setMessageRemark(String messageRemark) {
        this.messageRemark = messageRemark == null ? null : messageRemark.trim();
    }

    public String getSendmailreply() {
        return sendmailreply;
    }

    public void setSendmailreply(String sendmailreply) {
        this.sendmailreply = sendmailreply == null ? null : sendmailreply.trim();
    }

    public String getSendnotereply() {
        return sendnotereply;
    }

    public void setSendnotereply(String sendnotereply) {
        this.sendnotereply = sendnotereply == null ? null : sendnotereply.trim();
    }
}