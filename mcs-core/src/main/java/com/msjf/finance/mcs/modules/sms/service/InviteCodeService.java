package com.msjf.finance.mcs.modules.sms.service;

import com.msjf.finance.mcs.common.response.Response;
import com.msjf.finance.mcs.facade.sms.domain.VerificationCodeDomain;

import java.util.HashMap;

public interface InviteCodeService {
    /**
     * 邀请认证
     * 生成并得到邀請碼
     * @param mapParam
     * @return
     */
    Response<VerificationCodeDomain> getInviteCode(HashMap<String, Object> mapParam);

    /**
     * 邀请认证
     * 发送邀請碼短信
     * @param mapParam
     * @return
     */
    Response<VerificationCodeDomain> sendInvitecode(HashMap<String, Object> mapParam);
}
