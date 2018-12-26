package com.msjf.finance.mcs.modules.sms.entity;

public class SpmSmsControlTb {
    private String controlKey;

    private Integer cumulativeTotal;

    private Integer todayTotal;

    private String insertDate;

    private String insertTime;

    private String updateDate;

    private String updateTime;

    public String getControlKey() {
        return controlKey;
    }

    public void setControlKey(String controlKey) {
        this.controlKey = controlKey == null ? null : controlKey.trim();
    }

    public Integer getCumulativeTotal() {
        return cumulativeTotal;
    }

    public void setCumulativeTotal(Integer cumulativeTotal) {
        this.cumulativeTotal = cumulativeTotal;
    }

    public Integer getTodayTotal() {
        return todayTotal;
    }

    public void setTodayTotal(Integer todayTotal) {
        this.todayTotal = todayTotal;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate == null ? null : insertDate.trim();
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime == null ? null : insertTime.trim();
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate == null ? null : updateDate.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }
}