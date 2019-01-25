package com.msjf.finance.mcs.facade.sms;

import com.msjf.finance.mcs.facade.sms.domain.InviteCodeDomain;
import com.msjf.finance.mcs.facade.sms.domain.VerificationCodeDomain;
import com.msjf.finance.msjf.core.response.Response;

import java.util.HashMap;

public interface InviteCodeFacade {
    /**
     * 邀请认证
     * 生成并得到邀请码
     * @param mapParam
     * @return
     */
    Response<InviteCodeDomain> getInviteCode(HashMap<String, Object> mapParam);

    /**
     * 邀请认证
     * 发送邀請碼短信
     * @param mapParam
     * @return
     */
    Response<InviteCodeDomain> sendInvitecode(HashMap<String, Object> mapParam);

}
