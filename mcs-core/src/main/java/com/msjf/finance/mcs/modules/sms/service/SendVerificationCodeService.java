package com.msjf.finance.mcs.modules.sms.service;

import com.msjf.finance.mcs.common.response.Response;
import com.msjf.finance.mcs.facade.sms.domain.VerificationCodeDomain;

import java.util.HashMap;
import java.util.List;

public interface SendVerificationCodeService {

    Response<VerificationCodeDomain> SendRegisterVerificationCode(HashMap<String, Object> mapParam);
}
