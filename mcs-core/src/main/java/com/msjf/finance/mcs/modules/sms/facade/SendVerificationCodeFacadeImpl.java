package com.msjf.finance.mcs.modules.sms.facade;

import com.msjf.finance.mcs.facade.sms.SendVerificationCodeFacade;
import com.msjf.finance.mcs.facade.sms.domain.ReqSendVerificationCodeDomain;
import com.msjf.finance.mcs.facade.sms.domain.VerificationCodeDomain;
import com.msjf.finance.mcs.modules.sms.emun.SendVerificationCodeEnum;
import com.msjf.finance.mcs.modules.sms.service.SendVerificationCodeService;
import com.msjf.finance.msjf.core.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
@Service("sendVerificationCodeFacade")
@Transactional(rollbackFor = RuntimeException.class)
public class SendVerificationCodeFacadeImpl implements SendVerificationCodeFacade {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    SendVerificationCodeService sendVerificationCodeService;
    @Override
    public Response<VerificationCodeDomain> SendRegisterVerificationCode(ReqSendVerificationCodeDomain reqSendVerificationCodeDomain) {
        try{
            return sendVerificationCodeService.SendRegisterVerificationCode(reqSendVerificationCodeDomain);
        }catch (Exception e){
            logger.error(e.getMessage());
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
