package com.msjf.finance.mcs.modules.organ.facade;

import com.msjf.finance.mcs.facade.organ.OrganInfoFacade;
import com.msjf.finance.mcs.facade.organ.domain.OrganInfoDomain;
import com.msjf.finance.mcs.modules.organ.service.OrganInfoService;
import com.msjf.finance.msjf.core.response.Response;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 11509 on 2018/12/18.
 */
@Service("OrganInfoFacadeImpl")
public class OrganInfoFacadeImpl implements OrganInfoFacade {
    @Resource
    OrganInfoService organInfoService;

    public Response<List<OrganInfoDomain>> queryOrganInfoList() {
        try {
            List<OrganInfoDomain> organInfoDomainList = organInfoService.queryOrganInfoList();
            return new Response<>().success(organInfoDomainList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>().fail();
        }
    }
}
