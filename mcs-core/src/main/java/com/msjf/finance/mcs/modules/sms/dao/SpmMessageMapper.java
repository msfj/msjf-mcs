package com.msjf.finance.mcs.modules.sms.dao;

import com.msjf.finance.mcs.modules.sms.entity.SpmMessage;
import com.msjf.finance.mcs.modules.sms.entity.SpmMessageWithBLOBs;

public interface SpmMessageMapper {
    int deleteByPrimaryKey(String seqNum);

    int insert(SpmMessageWithBLOBs record);

    int insertSelective(SpmMessageWithBLOBs record);

    SpmMessageWithBLOBs selectByPrimaryKey(String seqNum);

    int updateByPrimaryKeySelective(SpmMessageWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SpmMessageWithBLOBs record);

    int updateByPrimaryKey(SpmMessage record);
}