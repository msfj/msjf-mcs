package com.msjf.finance.mcs.modules.sms.dao;

import com.msjf.finance.mcs.common.dao.MyBatisDao;
import com.msjf.finance.mcs.modules.sms.entity.AusVerificateCodeEntity;
import com.msjf.finance.mcs.modules.sms.entity.AusVerificateCodeEntityKey;
@MyBatisDao
public interface AusVerificateCodeEntityMapper {
    int deleteByPrimaryKey(AusVerificateCodeEntityKey key);

    int insert(AusVerificateCodeEntity record);

    int insertSelective(AusVerificateCodeEntity record);

    AusVerificateCodeEntity selectByPrimaryKey(AusVerificateCodeEntityKey key);

    int updateByPrimaryKeySelective(AusVerificateCodeEntity record);

    int updateByPrimaryKey(AusVerificateCodeEntity record);
}