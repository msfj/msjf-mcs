package com.msjf.finance.mcs.modules.sms.service.impl;

import com.msjf.finance.mcs.common.response.Response;
import com.msjf.finance.mcs.facade.sms.domain.VerificationCodeDomain;
import com.msjf.finance.mcs.modules.sms.SmsSend;
import com.msjf.finance.mcs.modules.sms.dao.CifInviteCodeEntityMapper;
import com.msjf.finance.mcs.modules.sms.entity.CifInviteCodeEntity;
import com.msjf.finance.mcs.modules.sms.service.InviteCodeService;
import com.msjf.finance.mcs.modules.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("InviteCodeServiceImpl")
@Scope("prototype")
public class InviteCodeServiceImpl extends SmsSend implements InviteCodeService {
    /** 邀请码表流水号*/
    private String serialno;

    /** 企业客户代码  */
    private String customerno;

    /** 成员类型      */
    private String membertype;

    /** 成员名称或姓名*/
    private String membername;

    /** 证件类型 */
    private String certificatetype;

    /** 证件号码      */
    private String certificateno;

    /** 联系电话      */
    private String mobile;

    /** 备注          */
    private String remark;

    /** 邀请码      */
    private String invitecode;

    /** 邀请码返回      */
    private String getinvitecode;

    /**
     * 会员管理中邀请为空
     * 其它情况下传企业代码
     * **/
    private String orgcustomerno;

    /** 是否发送短信 0-未发送 1-已发送 */
    private String issendsms;
    @Resource
    CifInviteCodeEntityMapper cifInviteCodeEntityMapper;
    @Autowired
    CommonUtil commonUtil;
    @Override
    public Response<VerificationCodeDomain> getInviteCode(HashMap<String, Object> mapParam) {
        Response rs=new Response();
        rs.fail("操作失败");
        //生成并返回邀请码
        Map<Integer, Object> inviteCodeMap =  commonUtil.getInviteCode1(customerno, certificateno, CommonUtil.YES, rs);
        Set<Map.Entry<Integer, Object>> set=inviteCodeMap.entrySet();
        Iterator<Map.Entry<Integer, Object>> iter=set.iterator();
        while(iter.hasNext()){
            Map.Entry<Integer, Object> me1=iter.next();
            if (me1.getKey().equals(0)) {  //没有邀请码，生产邀请码
                getinvitecode = (String) me1.getValue();
                addInviteCode(rs);//写邀请码表
            } else {
                getinvitecode = (String) me1.getValue();  //已有邀请码
            }
        }

        CifInviteCodeEntity c = new CifInviteCodeEntity();
        c.setOrgcustomerno(customerno);
        c.setCertificateno(certificateno);
        c.setInvitecode(getinvitecode);
        c.setStatus(CommonUtil.NO);
        List<CifInviteCodeEntity> cs = cifInviteCodeEntityMapper
                .selectByEntity(c);
        serialno = cs.get(0).getSerialno();
        issendsms = cs.get(0).getIssendsms();
        //更新企業成員信息表
        return null;
    }
    private void getParam(HashMap<String, Object> mapParam, Response rs) {
        invitecode = StringUtil.valueOf(mapParam.get("invitecode"));
        customerno = StringUtil.valueOf(mapParam.get("customerno"));
        certificateno=StringUtil.valueOf(mapParam.get("certificateno"));
        orgcustomerno = StringUtil.valueOf(mapParam.get("orgcustomerno"));
    }
    @Override
    public Response<VerificationCodeDomain> sendInvitecode(HashMap<String, Object> mapParam) {
        return null;
    }
    /**
     * 写邀请码表
     *
     * @param
     * @param
     */
    private void addInviteCode(Response rs) {
        int seconds = Integer.valueOf(
                commonUtil.getSysConfigValue(SYS_PARAM_ID_FAILURE_TIME_INVITECODE, SYS_PARAM_TYPE_FAILURE_TIME)) * 3600;
        String failuretime = DateUtil.addSeconds(DateUtil.getUserDate(DATE_FMT_DATETIME), DATE_FMT_DATETIME, seconds,rs);
        CifInviteCodeEntity cifInviteCodeEntity = new CifInviteCodeEntity();
        //cifInviteCodeEntity.setSerialno(CommonUtil.getOnlyId());
        cifInviteCodeEntity.setCustomerno(customerno);
        cifInviteCodeEntity.setOrgcustomerno(orgcustomerno);
        cifInviteCodeEntity.setCertificatetype(certificatetype);
        cifInviteCodeEntity.setCertificateno(certificateno);
        //cifInviteCodeEntity.setIsmember(isMember);
        cifInviteCodeEntity.setInvitecode(getinvitecode);
        cifInviteCodeEntity.setStatus(NO);
        cifInviteCodeEntity.setFailuretime(failuretime);
        cifInviteCodeEntity.setInsertdate(DateUtil.getUserDate(DATE_FMT_DATE));
        cifInviteCodeEntity.setInserttime(DateUtil.getUserDate(DATE_FMT_TIME));
        cifInviteCodeEntity.setUpdatedate(DateUtil.getUserDate(DATE_FMT_DATE));
        cifInviteCodeEntity.setUpdatetime(DateUtil.getUserDate(DATE_FMT_TIME));
        cifInviteCodeEntity.setIssendsms(NO);
        cifInviteCodeEntityMapper.insert(cifInviteCodeEntity);
    }
}
