package com.msjf.finance.mcs.modules.sms.dao;

import com.msjf.finance.mcs.common.dao.MyBatisDao;
import com.msjf.finance.mcs.modules.sms.entity.SysSmsConfigEntity;

import java.util.List;

@MyBatisDao
public interface SysSmsConfigEntityMapper {
    int deleteByPrimaryKey(String smsId);

    int insert(SysSmsConfigEntity record);

    int insertSelective(SysSmsConfigEntity record);

    SysSmsConfigEntity selectByPrimaryKey(String smsId);

    int updateByPrimaryKeySelective(SysSmsConfigEntity record);

    int updateByPrimaryKey(SysSmsConfigEntity record);

    List<SysSmsConfigEntity> selectByEntity(SysSmsConfigEntity record);
}