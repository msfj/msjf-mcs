package com.msjf.finance.mcs.modules.sms.dao;

import com.msjf.finance.mcs.modules.sms.entity.SpmMsgTemplateEntity;
import com.msjf.finance.mcs.modules.sms.entity.SpmMsgTemplateEntityKey;
import com.msjf.finance.msjf.core.dao.MyBatisDao;

import java.util.List;

@MyBatisDao
public interface SpmMsgTemplateEntityMapper {
    int deleteByPrimaryKey(SpmMsgTemplateEntityKey key);

    int insert(SpmMsgTemplateEntity record);

    int insertSelective(SpmMsgTemplateEntity record);

    SpmMsgTemplateEntity selectByPrimaryKey(SpmMsgTemplateEntityKey key);

    int updateByPrimaryKeySelective(SpmMsgTemplateEntity record);

    int updateByPrimaryKey(SpmMsgTemplateEntity record);

    List<SpmMsgTemplateEntity> selectByEntityLock(SpmMsgTemplateEntity record);
}
