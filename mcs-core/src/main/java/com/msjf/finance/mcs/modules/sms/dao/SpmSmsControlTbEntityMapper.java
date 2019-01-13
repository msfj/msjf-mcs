package com.msjf.finance.mcs.modules.sms.dao;

import com.msjf.finance.mcs.modules.sms.entity.SpmSmsControlTbEntity;
import com.msjf.finance.msjf.core.dao.MyBatisDao;

@MyBatisDao
public interface SpmSmsControlTbEntityMapper {
    int deleteByPrimaryKey(String controlKey);

    int insert(SpmSmsControlTbEntity record);

    int insertSelective(SpmSmsControlTbEntity record);

    SpmSmsControlTbEntity selectByPrimaryKey(String controlKey);

    int updateByPrimaryKeySelective(SpmSmsControlTbEntity record);

    int updateByPrimaryKey(SpmSmsControlTbEntity record);
}