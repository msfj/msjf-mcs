package com.msjf.finance.mcs.modules.organ.dao;

import com.msjf.finance.msjf.core.dao.MyBatisDao;
import com.msjf.finance.mcs.modules.organ.entity.CifCustEntity;

import java.util.List;
@MyBatisDao
public interface CifCustDao{
    List<CifCustEntity> queryCifCustList();
}
