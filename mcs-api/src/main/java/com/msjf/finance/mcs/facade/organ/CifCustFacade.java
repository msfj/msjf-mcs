package com.msjf.finance.mcs.facade.organ;
import com.msjf.finance.mcs.facade.organ.domain.CifCustDomain;
import com.msjf.finance.msjf.core.response.Response;


import java.util.List;

public interface CifCustFacade {
    public Response<List<CifCustDomain>> queryCifCustList();
}
