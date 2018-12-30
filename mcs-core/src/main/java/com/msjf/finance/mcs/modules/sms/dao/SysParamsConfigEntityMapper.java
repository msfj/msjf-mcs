package com.msjf.finance.mcs.modules.sms.dao;

import com.msjf.finance.mcs.common.dao.MyBatisDao;
import com.msjf.finance.mcs.modules.sms.entity.SysParamsConfigEntity;
import com.msjf.finance.mcs.modules.sms.entity.SysParamsConfigEntityKey;
@MyBatisDao
public interface SysParamsConfigEntityMapper {
    int deleteByPrimaryKey(SysParamsConfigEntityKey key);

    int insert(SysParamsConfigEntity record);

    int insertSelective(SysParamsConfigEntity record);

    SysParamsConfigEntity selectByPrimaryKey(SysParamsConfigEntityKey key);

    int updateByPrimaryKeySelective(SysParamsConfigEntity record);

    int updateByPrimaryKey(SysParamsConfigEntity record);
}