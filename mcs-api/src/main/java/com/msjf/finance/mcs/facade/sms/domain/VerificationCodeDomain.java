package com.msjf.finance.mcs.facade.sms.domain;

import com.msjf.finance.msjf.core.domian.BaseDomain;

public class VerificationCodeDomain extends BaseDomain {

    private String seqNum;

    private String activeSeconds;

    public String getActiveSeconds() {
        return activeSeconds;
    }

    public void setActiveSeconds(String activeSeconds) {
        this.activeSeconds = activeSeconds;
    }

    public String getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(String seqNum) {
        this.seqNum = seqNum;
    }

}
