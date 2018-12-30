package com.msjf.finance.mcs.modules.sms.dao;

import com.msjf.finance.mcs.common.dao.MyBatisDao;
import com.msjf.finance.mcs.modules.sms.entity.SpmSmsControlTbEntity;
@MyBatisDao
public interface SpmSmsControlTbEntityMapper {
    int deleteByPrimaryKey(String controlKey);

    int insert(SpmSmsControlTbEntity record);

    int insertSelective(SpmSmsControlTbEntity record);

    SpmSmsControlTbEntity selectByPrimaryKey(String controlKey);

    int updateByPrimaryKeySelective(SpmSmsControlTbEntity record);

    int updateByPrimaryKey(SpmSmsControlTbEntity record);
}