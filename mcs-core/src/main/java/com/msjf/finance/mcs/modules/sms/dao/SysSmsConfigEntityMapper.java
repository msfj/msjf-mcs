package com.msjf.finance.mcs.modules.sms.dao;

import com.msjf.finance.mcs.common.dao.MyBatisDao;
import com.msjf.finance.mcs.modules.sms.entity.SysSmsConfigEntity;
@MyBatisDao
public interface SysSmsConfigEntityMapper {
    int deleteByPrimaryKey(String smsId);

    int insert(SysSmsConfigEntity record);

    int insertSelective(SysSmsConfigEntity record);

    SysSmsConfigEntity selectByPrimaryKey(String smsId);

    int updateByPrimaryKeySelective(SysSmsConfigEntity record);

    int updateByPrimaryKey(SysSmsConfigEntity record);
}