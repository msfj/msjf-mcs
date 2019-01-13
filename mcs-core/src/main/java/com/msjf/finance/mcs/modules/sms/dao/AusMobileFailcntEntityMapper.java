package com.msjf.finance.mcs.modules.sms.dao;

import com.msjf.finance.mcs.modules.sms.entity.AusMobileFailcntEntity;
import com.msjf.finance.msjf.core.dao.MyBatisDao;

@MyBatisDao
public interface AusMobileFailcntEntityMapper {
    int deleteByPrimaryKey(String mobile);

    int insert(AusMobileFailcntEntity record);

    int insertSelective(AusMobileFailcntEntity record);

    AusMobileFailcntEntity selectByPrimaryKey(String mobile);

    int updateByPrimaryKeySelective(AusMobileFailcntEntity record);

    int updateByPrimaryKey(AusMobileFailcntEntity record);
}