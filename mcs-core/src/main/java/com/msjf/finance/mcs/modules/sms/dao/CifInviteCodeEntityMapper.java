package com.msjf.finance.mcs.modules.sms.dao;

import com.msjf.finance.mcs.modules.sms.entity.CifInviteCodeEntity;
import com.msjf.finance.msjf.core.dao.MyBatisDao;

import java.util.List;

@MyBatisDao
public interface CifInviteCodeEntityMapper {
    int deleteByPrimaryKey(String serialno);

    int insert(CifInviteCodeEntity record);

    int insertSelective(CifInviteCodeEntity record);

    CifInviteCodeEntity selectByPrimaryKey(String serialno);

    int updateByPrimaryKeySelective(CifInviteCodeEntity record);

    int updateByPrimaryKey(CifInviteCodeEntity record);

    List<CifInviteCodeEntity> selectByEntity(CifInviteCodeEntity record);
}