package com.msjf.finance.mcs.modules.sms.dao;

import com.msjf.finance.mcs.common.dao.MyBatisDao;
import com.msjf.finance.mcs.modules.sms.entity.AusMobileFailcntEntity;
@MyBatisDao
public interface AusMobileFailcntEntityMapper {
    int deleteByPrimaryKey(String mobile);

    int insert(AusMobileFailcntEntity record);

    int insertSelective(AusMobileFailcntEntity record);

    AusMobileFailcntEntity selectByPrimaryKey(String mobile);

    int updateByPrimaryKeySelective(AusMobileFailcntEntity record);

    int updateByPrimaryKey(AusMobileFailcntEntity record);
}