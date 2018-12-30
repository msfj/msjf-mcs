package com.msjf.finance.mcs.modules.sms.dao;

import com.msjf.finance.mcs.common.dao.MyBatisDao;
import com.msjf.finance.mcs.modules.sms.entity.SpmMsgTemplateEntity;
import com.msjf.finance.mcs.modules.sms.entity.SpmMsgTemplateEntityKey;
@MyBatisDao
public interface SpmMsgTemplateEntityMapper {
    int deleteByPrimaryKey(SpmMsgTemplateEntityKey key);

    int insert(SpmMsgTemplateEntity record);

    int insertSelective(SpmMsgTemplateEntity record);

    SpmMsgTemplateEntity selectByPrimaryKey(SpmMsgTemplateEntityKey key);

    int updateByPrimaryKeySelective(SpmMsgTemplateEntity record);

    int updateByPrimaryKey(SpmMsgTemplateEntity record);
}