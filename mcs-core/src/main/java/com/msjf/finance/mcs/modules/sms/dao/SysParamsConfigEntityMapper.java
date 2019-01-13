package com.msjf.finance.mcs.modules.sms.dao;

import com.msjf.finance.mcs.modules.sms.entity.SysParamsConfigEntity;
import com.msjf.finance.mcs.modules.sms.entity.SysParamsConfigEntityKey;
import com.msjf.finance.msjf.core.dao.MyBatisDao;

@MyBatisDao
public interface SysParamsConfigEntityMapper {
    int deleteByPrimaryKey(SysParamsConfigEntityKey key);

    int insert(SysParamsConfigEntity record);

    int insertSelective(SysParamsConfigEntity record);

    SysParamsConfigEntity selectByPrimaryKey(SysParamsConfigEntityKey key);

    int updateByPrimaryKeySelective(SysParamsConfigEntity record);

    int updateByPrimaryKey(SysParamsConfigEntity record);
}