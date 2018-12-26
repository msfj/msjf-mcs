package com.msjf.finance.mcs.modules.sms.entity;

public class CifInviteCode {
    private String serialno;

    private String customerno;

    private String orgcustomerno;

    private String certificatetype;

    private String certificateno;

    private String ismember;

    private String invitecode;

    private String status;

    private String failuretime;

    private String insertdate;

    private String inserttime;

    private String updatedate;

    private String updatetime;

    private String issendsms;

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno == null ? null : serialno.trim();
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }

    public String getOrgcustomerno() {
        return orgcustomerno;
    }

    public void setOrgcustomerno(String orgcustomerno) {
        this.orgcustomerno = orgcustomerno == null ? null : orgcustomerno.trim();
    }

    public String getCertificatetype() {
        return certificatetype;
    }

    public void setCertificatetype(String certificatetype) {
        this.certificatetype = certificatetype == null ? null : certificatetype.trim();
    }

    public String getCertificateno() {
        return certificateno;
    }

    public void setCertificateno(String certificateno) {
        this.certificateno = certificateno == null ? null : certificateno.trim();
    }

    public String getIsmember() {
        return ismember;
    }

    public void setIsmember(String ismember) {
        this.ismember = ismember == null ? null : ismember.trim();
    }

    public String getInvitecode() {
        return invitecode;
    }

    public void setInvitecode(String invitecode) {
        this.invitecode = invitecode == null ? null : invitecode.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getFailuretime() {
        return failuretime;
    }

    public void setFailuretime(String failuretime) {
        this.failuretime = failuretime == null ? null : failuretime.trim();
    }

    public String getInsertdate() {
        return insertdate;
    }

    public void setInsertdate(String insertdate) {
        this.insertdate = insertdate == null ? null : insertdate.trim();
    }

    public String getInserttime() {
        return inserttime;
    }

    public void setInserttime(String inserttime) {
        this.inserttime = inserttime == null ? null : inserttime.trim();
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate == null ? null : updatedate.trim();
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }

    public String getIssendsms() {
        return issendsms;
    }

    public void setIssendsms(String issendsms) {
        this.issendsms = issendsms == null ? null : issendsms.trim();
    }
}