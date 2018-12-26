package com.msjf.finance.mcs.modules.sms.dao;

import com.msjf.finance.mcs.modules.sms.entity.SpmSmsControlTb;

public interface SpmSmsControlTbMapper {
    int deleteByPrimaryKey(String controlKey);

    int insert(SpmSmsControlTb record);

    int insertSelective(SpmSmsControlTb record);

    SpmSmsControlTb selectByPrimaryKey(String controlKey);

    int updateByPrimaryKeySelective(SpmSmsControlTb record);

    int updateByPrimaryKey(SpmSmsControlTb record);
}