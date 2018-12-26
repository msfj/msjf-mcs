package com.msjf.finance.mcs.modules.sms.dao;

import com.msjf.finance.mcs.modules.sms.entity.SysParamsConfig;
import com.msjf.finance.mcs.modules.sms.entity.SysParamsConfigKey;

public interface SysParamsConfigMapper {
    int deleteByPrimaryKey(SysParamsConfigKey key);

    int insert(SysParamsConfig record);

    int insertSelective(SysParamsConfig record);

    SysParamsConfig selectByPrimaryKey(SysParamsConfigKey key);

    int updateByPrimaryKeySelective(SysParamsConfig record);

    int updateByPrimaryKey(SysParamsConfig record);
}