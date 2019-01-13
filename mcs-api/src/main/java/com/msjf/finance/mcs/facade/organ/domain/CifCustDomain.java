package com.msjf.finance.mcs.facade.organ.domain;
import com.msjf.finance.msjf.core.domian.BaseDomain;

public class CifCustDomain extends BaseDomain {
    private String customerno; //客户号
    private String loginname;  //企业名称
    private String menbertype;  //企业类型
    public String getCustomerno() {
        return customerno;
    }
    public void setCustomerno(String customerno) {
        this.customerno = customerno;
    }
    public String getLoginname() {
        return loginname;
    }
    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }
    public String getMenbertype() {
        return menbertype;
    }
    public void setMenbertype(String menbertype) {
        this.menbertype = menbertype;
    }
}
