package com.msjf.finance.mcs.modules.sms.entity;


/***
 *SysSmsConfigEntity
 *短信配置表
 */
public class SysSmsConfigEntity{
    private String exchangeId;
    private String distributorId;
    private String smsId;
    //账号
    private String userId;
    //子账号(暂不用)
    private String account;
    //密码
    private String password;
    //短接口地址
    private String url;
    //状态（0.默认关闭。1.开启）
    private Integer status;
    //短信接口类型
    private String smsType;
    //服务商名称
    private String serviceProvider;
    //控制参数（暂不用）
    private String ctrlParam;
    //备注
    private String ctrlComment;
    private String createDatetime;
    private String createBy;
    private String editDatetime;
    private String editBy;

    public void init() {
    }

    public String getExchangeId() {
        return this.exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getDistributorId() {
        return this.distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getSmsId() {
        return this.smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSmsType() {
        return this.smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getServiceProvider() {
        return this.serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getCtrlParam() {
        return this.ctrlParam;
    }

    public void setCtrlParam(String ctrlParam) {
        this.ctrlParam = ctrlParam;
    }

    public String getCtrlComment() {
        return this.ctrlComment;
    }

    public void setCtrlComment(String ctrlComment) {
        this.ctrlComment = ctrlComment;
    }

    public String getCreateDatetime() {
        return this.createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getEditDatetime() {
        return this.editDatetime;
    }

    public void setEditDatetime(String editDatetime) {
        this.editDatetime = editDatetime;
    }

    public String getEditBy() {
        return this.editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }
}