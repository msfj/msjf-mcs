package com.msjf.finance.mcs.modules.sms.dao;

import com.msjf.finance.mcs.modules.sms.entity.CifInviteCode;

public interface CifInviteCodeMapper {
    int deleteByPrimaryKey(String serialno);

    int insert(CifInviteCode record);

    int insertSelective(CifInviteCode record);

    CifInviteCode selectByPrimaryKey(String serialno);

    int updateByPrimaryKeySelective(CifInviteCode record);

    int updateByPrimaryKey(CifInviteCode record);
}