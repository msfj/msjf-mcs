package com.msjf.finance.mcs.modules.sms.facade;

import com.msjf.finance.mcs.common.response.Response;
import com.msjf.finance.mcs.facade.sms.SendVerificationCodeFacade;
import com.msjf.finance.mcs.facade.sms.domain.VerificationCodeDomain;
import com.msjf.finance.mcs.modules.sms.service.SendVerificationCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
@Service("sendVerificationCodeFacade")
public class SendVerificationCodeFacadeImpl implements SendVerificationCodeFacade {
    @Resource
    SendVerificationCodeService sendVerificationCodeService;
    @Override
    public Response<VerificationCodeDomain> SendRegisterVerificationCode(HashMap<String, Object> mapParam) {
        Response rs=null;
        try{
            rs=sendVerificationCodeService.SendRegisterVerificationCode(mapParam);
        }catch (Exception e){
            rs.fail(e.getMessage());
        }
        return rs;
    }
}
