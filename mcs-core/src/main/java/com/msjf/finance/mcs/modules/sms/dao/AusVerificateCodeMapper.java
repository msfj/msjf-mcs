package com.msjf.finance.mcs.modules.sms.dao;

import com.msjf.finance.mcs.modules.sms.entity.AusVerificateCode;
import com.msjf.finance.mcs.modules.sms.entity.AusVerificateCodeKey;

public interface AusVerificateCodeMapper {
    int deleteByPrimaryKey(AusVerificateCodeKey key);

    int insert(AusVerificateCode record);

    int insertSelective(AusVerificateCode record);

    AusVerificateCode selectByPrimaryKey(AusVerificateCodeKey key);

    int updateByPrimaryKeySelective(AusVerificateCode record);

    int updateByPrimaryKey(AusVerificateCode record);
}