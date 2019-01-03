package com.msjf.finance.mcs.modules.sms.facade;

import com.msjf.finance.mcs.common.response.Response;
import com.msjf.finance.mcs.facade.sms.InviteCodeFacade;
import com.msjf.finance.mcs.facade.sms.domain.VerificationCodeDomain;

import java.util.HashMap;

public class InviteCodeFacadeImpl implements InviteCodeFacade {
    @Override
    public Response<VerificationCodeDomain> getInviteCode(HashMap<String, Object> mapParam) {
        return null;
    }

    @Override
    public Response<VerificationCodeDomain> sendInvitecode(HashMap<String, Object> mapParam) {
        return null;
    }
}
