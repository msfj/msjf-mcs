package com.msjf.finance.mcs.facade.sms;

import com.msjf.finance.mcs.common.response.Response;
import com.msjf.finance.mcs.facade.organ.domain.OrganInfoDomain;
import com.msjf.finance.mcs.facade.sms.domain.VerificationCodeDomain;

import java.util.HashMap;
import java.util.List;

public interface SendVerificationCodeFacade {
    /*
        发动注册码和手机号码换绑
     */
    Response<VerificationCodeDomain> SendRegisterVerificationCode(HashMap<String, Object> mapParam) ;
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
