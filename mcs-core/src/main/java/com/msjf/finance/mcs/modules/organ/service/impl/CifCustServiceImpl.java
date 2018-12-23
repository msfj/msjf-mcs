package com.msjf.finance.mcs.modules.organ.service.impl;

import com.msjf.finance.mcs.facade.organ.domain.CifCustDomain;
import com.msjf.finance.mcs.facade.organ.domain.OrganInfoDomain;
import com.msjf.finance.mcs.modules.organ.dao.CifCustDao;
import com.msjf.finance.mcs.modules.organ.dao.OrganInfoDao;
import com.msjf.finance.mcs.modules.organ.entity.CifCustEntity;
import com.msjf.finance.mcs.modules.organ.entity.OrganInfoEntity;
import com.msjf.finance.mcs.modules.organ.service.CifCustService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("cifCustService")
public class CifCustServiceImpl implements CifCustService {
    @Resource
    CifCustDao cifCustDao;
    public List queryCifCustList() {
        try {
            List<CifCustEntity> cifCustEntityList = cifCustDao.queryCifCustList();
            List<CifCustDomain> cifCustDomainList = new ArrayList();
            cifCustEntityList.stream().forEach(cifCustEntity ->
                    {
                        CifCustDomain cifCustDomain = new CifCustDomain();
                        BeanUtils.copyProperties(cifCustEntity, cifCustDomain);
                        cifCustDomainList.add(cifCustDomain);
                    }
            );
            return cifCustDomainList;
        }catch (Exception e){
            //打印错误日志
            e.printStackTrace();
        }
        return null;
    }
}
