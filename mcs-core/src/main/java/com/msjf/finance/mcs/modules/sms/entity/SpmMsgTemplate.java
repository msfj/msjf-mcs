package com.msjf.finance.mcs.modules.sms.entity;

public class SpmMsgTemplate extends SpmMsgTemplateKey {
    private String templateName;

    private String templateContent;

    private Integer status;

    private String templateKeys;

    private Integer templateSort;

    private String datePattern;

    private String apiId;

    private String templateSmsContent;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent == null ? null : templateContent.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTemplateKeys() {
        return templateKeys;
    }

    public void setTemplateKeys(String templateKeys) {
        this.templateKeys = templateKeys == null ? null : templateKeys.trim();
    }

    public Integer getTemplateSort() {
        return templateSort;
    }

    public void setTemplateSort(Integer templateSort) {
        this.templateSort = templateSort;
    }

    public String getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern == null ? null : datePattern.trim();
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId == null ? null : apiId.trim();
    }

    public String getTemplateSmsContent() {
        return templateSmsContent;
    }

    public void setTemplateSmsContent(String templateSmsContent) {
        this.templateSmsContent = templateSmsContent == null ? null : templateSmsContent.trim();
    }
}