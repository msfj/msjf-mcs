package com.msjf.finance.mcs.modules.sms.entity;

import com.msjf.finance.msjf.core.entity.BaseEntity;

public class SysSmsConfigEntity extends BaseEntity {
    private String smsId;

    private String exchangeId;

    private String distributorId;

    private String userId;

    private String account;

    private String password;

    private String url;

    private Integer status;

    private String smsType;

    private String serviceProvider;

    private String ctrlParam;

    private String ctrlComment;

    private String createDatetime;

    private String createBy;

    private String editDatetime;

    private String editBy;

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId == null ? null : smsId.trim();
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId == null ? null : exchangeId.trim();
    }

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId == null ? null : distributorId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType == null ? null : smsType.trim();
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider == null ? null : serviceProvider.trim();
    }

    public String getCtrlParam() {
        return ctrlParam;
    }

    public void setCtrlParam(String ctrlParam) {
        this.ctrlParam = ctrlParam == null ? null : ctrlParam.trim();
    }

    public String getCtrlComment() {
        return ctrlComment;
    }

    public void setCtrlComment(String ctrlComment) {
        this.ctrlComment = ctrlComment == null ? null : ctrlComment.trim();
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime == null ? null : createDatetime.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getEditDatetime() {
        return editDatetime;
    }

    public void setEditDatetime(String editDatetime) {
        this.editDatetime = editDatetime == null ? null : editDatetime.trim();
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy == null ? null : editBy.trim();
    }
}