package com.msjf.finance.mcs.modules.sms.dao;

import com.msjf.finance.mcs.modules.sms.entity.AusMobileFailcnt;

public interface AusMobileFailcntMapper {
    int deleteByPrimaryKey(String mobile);

    int insert(AusMobileFailcnt record);

    int insertSelective(AusMobileFailcnt record);

    AusMobileFailcnt selectByPrimaryKey(String mobile);

    int updateByPrimaryKeySelective(AusMobileFailcnt record);

    int updateByPrimaryKey(AusMobileFailcnt record);
}