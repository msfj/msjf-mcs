package com.msjf.finance.mcs.modules.sms.dao;

import com.msjf.finance.mcs.modules.sms.entity.SpmMsgTemplate;
import com.msjf.finance.mcs.modules.sms.entity.SpmMsgTemplateKey;

public interface SpmMsgTemplateMapper {
    int deleteByPrimaryKey(SpmMsgTemplateKey key);

    int insert(SpmMsgTemplate record);

    int insertSelective(SpmMsgTemplate record);

    SpmMsgTemplate selectByPrimaryKey(SpmMsgTemplateKey key);

    int updateByPrimaryKeySelective(SpmMsgTemplate record);

    int updateByPrimaryKey(SpmMsgTemplate record);
}