package com.msjf.finance.mcs.modules.sms.dao;

import com.msjf.finance.mcs.modules.sms.entity.SysSmsConfig;

public interface SysSmsConfigMapper {
    int deleteByPrimaryKey(String smsId);

    int insert(SysSmsConfig record);

    int insertSelective(SysSmsConfig record);

    SysSmsConfig selectByPrimaryKey(String smsId);

    int updateByPrimaryKeySelective(SysSmsConfig record);

    int updateByPrimaryKey(SysSmsConfig record);
}