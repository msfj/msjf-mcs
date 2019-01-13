package com.msjf.finance.mcs.modules.sms.facade;

import com.msjf.finance.mcs.facade.sms.SendVerificationCodeFacade;
import com.msjf.finance.mcs.facade.sms.domain.VerificationCodeDomain;
import com.msjf.finance.mcs.modules.sms.emun.SendVerificationCodeEnum;
import com.msjf.finance.mcs.modules.sms.service.SendVerificationCodeService;
import com.msjf.finance.msjf.core.response.Response;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
@Service("sendVerificationCodeFacade")
public class SendVerificationCodeFacadeImpl implements SendVerificationCodeFacade {
    @Resource
    SendVerificationCodeService sendVerificationCodeService;
    @Override
    public Response<VerificationCodeDomain> SendRegisterVerificationCode(HashMap<String, Object> mapParam) {
        try{
            return sendVerificationCodeService.SendRegisterVerificationCode(mapParam);
        }catch (Exception e){
            return new Response<>().fail(SendVerificationCodeEnum.MSG_SEND_EXCEPTION);
        }
    }

    @Override
    public Response<VerificationCodeDomain> checkVerificationCode(HashMap<String, Object> mapParam) {
        try{
           return sendVerificationCodeService.checkVerificationCode(mapParam);
        }catch (Exception e){
            return new Response<>().fail(SendVerificationCodeEnum.MSG_CHECK_EXCEPTION);
        }
    }

    @Override
    public Response<VerificationCodeDomain> isExistVerificationCode(HashMap<String, Object> mapParam) {
        try{
            return sendVerificationCodeService.isExistVerificationCode(mapParam);
        }catch (Exception e){
            return new Response<>().fail(SendVerificationCodeEnum.MSG_CHECK_EXCEPTION);
        }
    }
}
