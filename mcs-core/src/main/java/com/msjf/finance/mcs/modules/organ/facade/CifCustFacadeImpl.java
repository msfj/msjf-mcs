package com.msjf.finance.mcs.modules.organ.facade;

import com.msjf.finance.mcs.common.response.Response;
import com.msjf.finance.mcs.facade.organ.CifCustFacade;
import com.msjf.finance.mcs.facade.organ.domain.CifCustDomain;
import com.msjf.finance.mcs.facade.organ.domain.OrganInfoDomain;
import com.msjf.finance.mcs.modules.organ.service.CifCustService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("cifCustFacade")
public class CifCustFacadeImpl implements CifCustFacade {
    @Resource
    CifCustService cifCustService;
    @Override
    public Response<List<CifCustDomain>> queryCifCustList() {
        try {
            List<CifCustDomain> cifCustDomainList = cifCustService.queryCifCustList();
            return new Response<>().success(cifCustDomainList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>().fail();
        }
    }
}
