package com.msjf.finance.mcs.modules.sms.dao;

import com.msjf.finance.mcs.common.dao.MyBatisDao;
import com.msjf.finance.mcs.modules.sms.entity.SpmMessageEntity;
import com.msjf.finance.mcs.modules.sms.entity.SpmMessageEntityWithBLOBs;
import com.msjf.finance.mcs.modules.sms.entity.SpmMsgTemplateEntity;

@MyBatisDao
public interface SpmMessageEntityMapper {
    int deleteByPrimaryKey(String seqNum);

    int insert(SpmMessageEntityWithBLOBs record);

    int insertSelective(SpmMessageEntityWithBLOBs record);

    SpmMessageEntityWithBLOBs selectByPrimaryKey(String seqNum);

    int updateByPrimaryKeySelective(SpmMessageEntityWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SpmMessageEntityWithBLOBs record);

    int updateByPrimaryKey(SpmMessageEntity record);
}