package com.msjf.finance.mcs.modules.sms.facade;

import com.msjf.finance.mcs.facade.sms.InviteCodeFacade;
import com.msjf.finance.mcs.facade.sms.domain.InviteCodeDomain;
import com.msjf.finance.mcs.facade.sms.domain.VerificationCodeDomain;
import com.msjf.finance.msjf.core.response.Response;

import java.util.HashMap;

public class InviteCodeFacadeImpl implements InviteCodeFacade {
    @Override
    public Response<InviteCodeDomain> getInviteCode(HashMap<String, Object> mapParam) {
        return null;
    }

    @Override
    public Response sendInvitecode(HashMap<String, Object> mapParam) {
        return null;
    }
}
