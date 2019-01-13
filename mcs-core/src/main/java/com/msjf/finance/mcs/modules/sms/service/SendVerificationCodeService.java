package com.msjf.finance.mcs.modules.sms.service;

import com.msjf.finance.mcs.facade.sms.domain.VerificationCodeDomain;
import com.msjf.finance.msjf.core.response.Response;

import java.util.HashMap;
import java.util.List;

public interface SendVerificationCodeService {

    Response<VerificationCodeDomain> SendRegisterVerificationCode(HashMap<String, Object> mapParam);

    Response<VerificationCodeDomain>  checkVerificationCode(HashMap<String, Object> mapParam);

    Response<VerificationCodeDomain>  isExistVerificationCode(HashMap<String, Object> mapParam);

}
