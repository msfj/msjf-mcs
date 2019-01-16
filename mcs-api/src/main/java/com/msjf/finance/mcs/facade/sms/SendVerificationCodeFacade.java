package com.msjf.finance.mcs.facade.sms;
import com.msjf.finance.mcs.facade.sms.domain.ReqSendVerificationCodeDomain;
import com.msjf.finance.mcs.facade.sms.domain.VerificationCodeDomain;
import com.msjf.finance.msjf.core.response.Response;

import java.util.HashMap;

public interface SendVerificationCodeFacade {
    /*
        发动注册码和手机号码换绑
     */
    Response<VerificationCodeDomain> SendRegisterVerificationCode(ReqSendVerificationCodeDomain reqSendVerificationCodeDomain) ;
    /*
       验证手机号码是否正确
     */
    Response<VerificationCodeDomain>  checkVerificationCode(HashMap<String, Object> mapParam);

    /**
     * 校验验证码是否已经验证通过
     * @param mapParam
     * @return
     */
    Response<VerificationCodeDomain>  isExistVerificationCode(HashMap<String, Object> mapParam);
}
