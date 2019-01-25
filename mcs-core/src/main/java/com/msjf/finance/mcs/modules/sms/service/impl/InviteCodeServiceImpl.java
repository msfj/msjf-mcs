package com.msjf.finance.mcs.modules.sms.service.impl;

import com.msjf.finance.mcs.facade.sms.domain.InviteCodeDomain;
import com.msjf.finance.mcs.facade.sms.domain.VerificationCodeDomain;
import com.msjf.finance.mcs.modules.sms.SmsSend;
import com.msjf.finance.mcs.modules.sms.dao.CifInviteCodeEntityMapper;
import com.msjf.finance.mcs.modules.sms.entity.CifInviteCodeEntity;
import com.msjf.finance.mcs.modules.sms.service.InviteCodeService;
import com.msjf.finance.mcs.modules.utils.*;
import com.msjf.finance.msjf.core.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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


    /** 邀请码返回      */
    private String getinvitecode;

    /**
     * 会员管理中邀请为空
     * 其它情况下传企业代码
     * **/
    private String orgcustomerno;

    /** 是否发送短信 0-未发送 1-已发送 */
    private String issendsms;

    /** 邀请类型 1-设立 2-变更 3-会员邀请 4-迁入*/
    private String inviteType;


    private String isMember;
    @Resource
    CifInviteCodeEntityMapper cifInviteCodeEntityMapper;
    @Override
    public Response<InviteCodeDomain> getInviteCode(HashMap<String, Object> mapParam) {
        Response<InviteCodeDomain> rs=new Response();
        rs.fail();
        getParam(mapParam);
        if(StringUtils.isEmpty(customerno)){
            return rs.fail();
        }
        if(StringUtils.isEmpty(certificateno)){
            return rs.fail();
        }
        //除了迁入为非会员邀请，其他会员邀请都必须传企业客户代码
        if(!"4".equals(inviteType)){
            if(StringUtils.isEmpty(orgcustomerno)){
                return rs.fail();
            }
        }

        if(!"4".equals(inviteType)){
            if(StringUtils.isEmpty(orgcustomerno)){
                return rs.fail();
            }
        }
        String inviteCodeCust;
        if(!"4".equals(inviteType)){
            inviteCodeCust=orgcustomerno;
            isMember=YES;
        }else {
            inviteCodeCust=customerno;
            isMember=NO;
        }
        //生成并返回邀请码
        Map<String, String> inviteCodeMap =  CommonUtil.getInviteCode(inviteCodeCust, certificateno, isMember, rs);
        if("1".equals(inviteCodeMap.get("isNewCode"))){
            getinvitecode = StringUtil.valueOf(inviteCodeMap.get("inviteCode"));
            addInviteCode(rs);//写邀请码表
        }else {
            getinvitecode =  StringUtil.valueOf(inviteCodeMap.get("inviteCode"));  //已有邀请码
        }
        CifInviteCodeEntity c = new CifInviteCodeEntity();
        c.setOrgcustomerno(customerno);
        c.setCertificateno(certificateno);
        c.setInvitecode(getinvitecode);
        c.setStatus(CommonUtil.NO);
        List<CifInviteCodeEntity> cs = cifInviteCodeEntityMapper
                .selectByEntity(c);
        //更新企業成員信息表
        InviteCodeDomain inviteCodeDomain=new InviteCodeDomain();
        inviteCodeDomain.setSerialno(cs.get(0).getSerialno());
        inviteCodeDomain.setInviteCode(getinvitecode);
        inviteCodeDomain.setIssendSms(cs.get(0).getIssendsms());
        return rs.success(inviteCodeDomain);
    }
    private void getParam(HashMap<String, Object> mapParam) {
        customerno = StringUtils.isEmpty(mapParam.get("customerno")) ? "" : String.valueOf(mapParam.get("customerno"));
        certificateno = StringUtils.isEmpty(mapParam.get("certificateno")) ? "" : String.valueOf(mapParam.get("certificateno"));
        orgcustomerno = StringUtils.isEmpty(mapParam.get("orgcustomerno")) ? "" : String.valueOf(mapParam.get("orgcustomerno"));
    }
    @Override
    public Response sendInvitecode(HashMap<String, Object> mapParam) {
        Response rs=new Response();
        return rs;
//        rs.failed("邀请码生成失败");
//        getParam(mapParam, rs);
//        if (CheckUtil.isNull(mobile)) {
//            rs.failed("手机号码未填写，无法发送");
//            throw new WsRuntimeException(rs.getErrorMessage());
//        }
//        if (CheckUtil.isNull(serialno)) {
//            rs.failed("流水号不能为空");
//            throw new WsRuntimeException(rs.getErrorMessage());
//        }
//        if (CheckUtil.isNull(invitecode)) {
//            rs.failed("邀请码不能为空");
//            throw new WsRuntimeException(rs.getErrorMessage());
//        }
//        if (CheckUtil.isNull(issendsms)) {
//            rs.failed("issendsms不能为空");
//            throw new WsRuntimeException(rs.getErrorMessage());
//        }
//        //前端传1.流水号  2、邀请码  3、是否发送短信
//        //发送短信验证码给受邀人
//        CifOrganInfoEntity cifOrganInfoEntity = cifOrganInfoPersistence.queryEntity(orgcustomerno);
//        if (CheckUtil.isNull(cifOrganInfoEntity)) {
//            rs.failed("企业基本信息不存在");
//            return;
//        }
//        if (issendsms.equals(NO)) {
//            CommonUtil.sendInviteCode(cifOrganInfoEntity.getMembername(), mobile, invitecode, customerno, "", rs);
//            CifInviteCodeEntity cifInviteCodeEntity = new CifInviteCodeEntity();
//            cifInviteCodeEntity.setSerialno(serialno);
//            cifInviteCodeEntity.setIssendsms(YES);
//            PersistenceUtil.getPersistence(CifInviteCodePersistence.class).update(cifInviteCodeEntity);
//            rs.successful("发送成功");
//        } else {
//            rs.failed("邀请码还在有效期内，请勿重复发送！");
//            return rs;
//        }
    }
    /**
     * 写邀请码表
     *
     * @param
     * @param
     */
    private void addInviteCode(Response rs) {
        int seconds = Integer.valueOf(
                CommonUtil.getSysConfigValue(SYS_PARAM_ID_FAILURE_TIME_INVITECODE, SYS_PARAM_TYPE_FAILURE_TIME)) * 3600;
        String failuretime = DateUtil.addSeconds(DateUtil.getUserDate(DATE_FMT_DATETIME), DATE_FMT_DATETIME, seconds,rs);
        CifInviteCodeEntity cifInviteCodeEntity = new CifInviteCodeEntity();
        //cifInviteCodeEntity.setSerialno(CommonUtil.getOnlyId());
        cifInviteCodeEntity.setCustomerno(customerno);
        cifInviteCodeEntity.setOrgcustomerno(orgcustomerno);
        cifInviteCodeEntity.setCertificatetype(certificatetype);
        cifInviteCodeEntity.setCertificateno(certificateno);
        cifInviteCodeEntity.setIsmember(isMember);
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
