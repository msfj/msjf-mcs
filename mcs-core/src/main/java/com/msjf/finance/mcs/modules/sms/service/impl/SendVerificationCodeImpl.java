package com.msjf.finance.mcs.modules.sms.service.impl;

import com.google.common.collect.Maps;
import com.msjf.finance.mcs.facade.sms.domain.ReqSendVerificationCodeDomain;
import com.msjf.finance.mcs.facade.sms.domain.VerificationCodeDomain;
import com.msjf.finance.mcs.modules.Message;
import com.msjf.finance.mcs.modules.sms.dao.AusVerificateCodeEntityMapper;
import com.msjf.finance.mcs.modules.sms.emun.SendVerificationCodeEnum;
import com.msjf.finance.mcs.modules.sms.entity.AusVerificateCodeEntity;
import com.msjf.finance.mcs.modules.sms.entity.AusVerificateCodeEntityKey;
import com.msjf.finance.mcs.modules.sms.service.SendVerificationCodeService;
import com.msjf.finance.mcs.modules.utils.*;
import com.msjf.finance.msjf.core.response.Response;
import com.xiaoleilu.hutool.util.NumberUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sendVerificationCodeImpl")
@Scope("prototype")
public class SendVerificationCodeImpl extends Message implements SendVerificationCodeService {
    @Resource
    AusVerificateCodeEntityMapper ausVerificateCodeEntityMapper;
    /**
     * 登陆名
     */
    private String loginName;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 客户代码
     */
    private String customerno;
    /**
     * 模板id
     */
    private String templateId;
    /**
     * 校验类型
     */
    private String verificateType;
    /**
     * 原验证码
     */
    private String oldMsgCode;
    /**
     * 原手机
     */
    private String oldMobile;
    /**
     * 验证码
     */
    private String msgCode;
    /**
     * <pre>
     *     发送短信验证码服务
     *     入参必须有手机号和模板ID
     * </pre>
     *
     * @param mapParam
     */
    public void getParam(HashMap<String, Object> mapParam){
        mobile = StringUtils.isEmpty(mapParam.get("mobile")) ? "" : String.valueOf(mapParam.get("mobile"));

        /** 登录账号 */
        loginName = StringUtils.isEmpty(mapParam.get("loginName")) ? "" : String.valueOf(mapParam.get("loginName"));
        customerno = StringUtils.isEmpty(mapParam.get("customerno")) ? "" : String.valueOf(mapParam.get
                ("customerno"));
        templateId = StringUtils.isEmpty(mapParam.get("templateId")) ? "" : String.valueOf(mapParam.get
                ("templateId"));
        verificateType = StringUtils.isEmpty(mapParam.get("verificateType")) ? "" : String.valueOf(mapParam.get
                ("verificateType"));
        oldMsgCode=StringUtils.isEmpty(mapParam.get("oldMsgCode")) ? "" : String.valueOf(mapParam.get
                ("oldMsgCode"));
        oldMobile=StringUtils.isEmpty(mapParam.get("oldMobile")) ? "" : String.valueOf(mapParam.get
                ("oldMobile"));
    }
    public Response<VerificationCodeDomain> SendRegisterVerificationCode(ReqSendVerificationCodeDomain reqSendVerificationCodeDomain ){
        Response<VerificationCodeDomain> rs=new Response();
        rs.fail();
        mobile =reqSendVerificationCodeDomain.getMobile();

        /** 登录账号 */
        verificateType = reqSendVerificationCodeDomain.getVerificateType();
        customerno = reqSendVerificationCodeDomain.getCustomerno();
        templateId =reqSendVerificationCodeDomain.getTemplateId();
//        if ( CheckUtil.isNull(customerno)) {
//            rs.fail("客户代码不能为空");
//            return rs;
//        }
        if (StringUtils.isEmpty(templateId)) {
            return rs.fail(SendVerificationCodeEnum.TEMPLE_ID_NULL);
        }
        if (StringUtils.isEmpty(mobile) ) {
            return rs.fail(SendVerificationCodeEnum.MOBILE_NULL);
        }
        //认证类型 0-服务平台注册 1-管理平台登录 2-修改密码 3-手机换绑
        if (StringUtils.isEmpty(verificateType)) {
            return rs.fail(SendVerificationCodeEnum.VERIFICATE_TYPE_NULL);
        }
        /**
         * 换绑第一次发送邀请码传customerno和mobile
         * 换绑输入新手机发送验证码customerno和mobile和oldmsgcode
         **/
        if(CommonUtil.SMS_CHANGE_MOBILE_TYPE.equals(verificateType)){
            if(!StringUtils.isEmpty(oldMsgCode)){
                if(StringUtils.isEmpty(oldMobile)){
                    rs.fail(SendVerificationCodeEnum.OLD_MOBILE_NULL);
                    return rs;
                }
                if(StringUtils.isEmpty(customerno)){
                    rs.fail(SendVerificationCodeEnum.CUSTOMERNO_NULL);
                    return rs;
                }
                AusVerificateCodeEntity ausVerificateCodeEntity=new AusVerificateCodeEntity();
                ausVerificateCodeEntity.setMobile(oldMobile);
                ausVerificateCodeEntity.setCustomerno(customerno);
                ausVerificateCodeEntity.setStatus(CommonUtil.YES);
                ausVerificateCodeEntity.setVerificatetype(CommonUtil.SMS_CHANGE_MOBILE_TYPE);
                List<AusVerificateCodeEntity> ausVerificateCodeEntityList=ausVerificateCodeEntityMapper.selectByEntity(ausVerificateCodeEntity);
                if(StringUtils.isEmpty(ausVerificateCodeEntityList)){
                    return rs.fail(SendVerificationCodeEnum.OLD_MOBILE_CHECK_ERROR);
                }
                if(mobile.equals(oldMobile)){
                    return rs.fail(SendVerificationCodeEnum.CHANGE_MOBILE_EXCEPTION);
                }
            }else{
                if(StringUtils.isEmpty(customerno)){
                    return rs.fail(SendVerificationCodeEnum.CUSTOMERNO_NULL);
                }
            }
        }
//        if (CheckUtil.isNull(mobile)) {
////            if (!CheckUtil.isNull(loginname)) {
////                CifCustEntity entity = new CifCustEntity();
////                entity.setLoginname(loginname);
////                List<CifCustEntity> entitys = cifCustPersistence.queryEntityList(entity);
////                if (CheckUtil.isNull(entitys)) {
////                    rs.failed("用户名不存在");
////                    return;
////                }
////                mobile = entitys.get(0).getMobile();
////            }
//        }
        if (!CheckUtil.isNum(mobile, 11)) {
//            rs.failed("手机号格式不正确");
            return rs;
        }
        //获取验证码有效时间
        String msgCodeFailureTime =CommonUtil.getSysConfigValue("msgcode_failure_time", "code_failure_time");
        if (StringUtils.isEmpty(msgCodeFailureTime)) {
            return rs.fail(SendVerificationCodeEnum.SYSTEM_PARA_ERROR);
        }
        if (!CheckUtil.isInteger(msgCodeFailureTime)) {
            return rs.fail(SendVerificationCodeEnum.SYSTEM_PARA_ERROR);
        }
//
//
//        //您的验证码是{verifyCode}在{activeSeconds}内有效{symbol}
//        //您的验证码是{xxxxxxxxxxxxxxx}在{xxxxxxxx}内有效{x}
//        //您的验证码是3212，在30分钟内有效。
            String code = UtilTools.getRandomCode(new BigDecimal(0), 4);
            HashMap mapParam= Maps.newHashMap();
            mapParam.put("verifyCode", code + ",");
            mapParam.put("activeSeconds", Integer.parseInt(msgCodeFailureTime) / 60 + "分钟");
            mapParam.put("symbol", "。");
            mapParam.put("templateId", templateId);
            mapParam.put("mobile", mobile);
            mapParam.put("loginName", loginName);

          SmsService api = SpringContextUtil.getBean("SmsServiceApi");
            Response<VerificationCodeDomain> irs =api.doService(mapParam);
            if (!irs.checkIfSuccess()) {
                rs.fail(irs);
                return rs;
            }

        //将短信验证码写入kpfsp.aus_verificate_code 表
        AusVerificateCodeEntity ausVerificateCodeEntity = new AusVerificateCodeEntity();
        ausVerificateCodeEntity.setMobile(mobile);
        ausVerificateCodeEntity.setVerificatetype(verificateType);
        ausVerificateCodeEntity.setStatus(CommonUtil.NO);
        ausVerificateCodeEntity.setVerificatecode(code);
        ausVerificateCodeEntity.setCustomerno(customerno); //手机换绑时（客户为登陆状态）写入客户代码

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, Integer.parseInt(msgCodeFailureTime));
        SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
        ausVerificateCodeEntity.setFailuretime(time.format(calendar.getTime()));

        //查询数据并检查
        AusVerificateCodeEntityKey ausVerificateCodeEntityKey=new AusVerificateCodeEntityKey();
        ausVerificateCodeEntityKey.setVerificatetype(verificateType);
        ausVerificateCodeEntityKey.setMobile(mobile);
        AusVerificateCodeEntity verificateCodeEntity = ausVerificateCodeEntityMapper.selectByPrimaryKey(ausVerificateCodeEntityKey);
        if (StringUtils.isEmpty(verificateCodeEntity)) {
            ausVerificateCodeEntityMapper.insert(ausVerificateCodeEntity);
        } else {
            ausVerificateCodeEntityMapper.updateByPrimaryKeySelective(ausVerificateCodeEntity);
        }

        //返回数据
        VerificationCodeDomain verificationCodeDomain=irs.getData();
//        Map map = Maps.newHashMap();
//        map.put("seqNum", String.valueOf(irsResult.get(0).get("seqNum")));
//        map.put("activeSeconds", msgCodeFailureTime);
//        ResultUtil.makerSusResults(irs.getErrorMessage(), map, rs);
        rs.success(verificationCodeDomain);
        return rs;
    }

    @Override
    public Response<VerificationCodeDomain> checkVerificationCode(HashMap<String, Object> mapParam){
        Response rs=new Response();
        rs.fail(SendVerificationCodeEnum.VERIFICATION_FAILD);
        mobile = StringUtils.isEmpty(mapParam.get("mobile")) ? "" : String.valueOf(mapParam.get("mobile"));

        /** 登录账号 */
        customerno = StringUtils.isEmpty(mapParam.get("customerno")) ? "" : String.valueOf(mapParam.get
                ("customerno"));
        verificateType = StringUtils.isEmpty(mapParam.get("verificateType")) ? "" : String.valueOf(mapParam.get
                ("verificateType"));
        msgCode=StringUtils.isEmpty(mapParam.get("msgCode")) ? "" : String.valueOf(mapParam.get
                ("msgCode"));

        if(!checkIsExist(rs)){
            return rs;
        }
        /**
         * 根据不同类型去调不同的校验方式
         */
        Boolean flag=false;
        if(CommonUtil.SMS_CHANGE_MOBILE_TYPE.equals(verificateType)){
            return CommonUtil.checkMsgCodeMoblieChange(customerno,msgCode,mobile,verificateType,false);
        }else {
            return CommonUtil.checkMsgCode(msgCode,mobile,verificateType,false);
        }
    }
    public Boolean checkIsExist(Response rs){
        if(StringUtils.isEmpty(verificateType)){
            rs.fail(SendVerificationCodeEnum.VERIFICATE_TYPE_NULL);
            return false;
        }
        if(StringUtils.isEmpty(msgCode)){
            rs.fail(SendVerificationCodeEnum.MSGCODE_NULL);
            return false;
        }
        if(StringUtils.isEmpty(mobile)){
            rs.fail(SendVerificationCodeEnum.MOBILE_NULL);
            return false;
        }
        if(CommonUtil.SMS_CHANGE_MOBILE_TYPE.equals(verificateType)){
            if(StringUtils.isEmpty(customerno)){
                rs.fail(SendVerificationCodeEnum.CUSTOMERNO_NULL);
                return false;
            }
        }
        return true;
    }
    @Override
    public Response<VerificationCodeDomain> isExistVerificationCode(HashMap<String, Object> mapParam) {
        Response<VerificationCodeDomain> rs=new Response();
        mobile = StringUtils.isEmpty(mapParam.get("mobile")) ? "" : String.valueOf(mapParam.get("mobile"));
        /** 登录账号 */
        customerno = StringUtils.isEmpty(mapParam.get("customerno")) ? "" : String.valueOf(mapParam.get
                ("customerno"));
        verificateType = StringUtils.isEmpty(mapParam.get("verificateType")) ? "" : String.valueOf(mapParam.get
                ("verificateType"));
        msgCode=StringUtils.isEmpty(mapParam.get("msgCode")) ? "" : String.valueOf(mapParam.get
                ("msgCode"));
        if(!checkIsExist(rs)){
            return rs;
        }
        List<AusVerificateCodeEntity> ausVerificateCodeEntityList=null;
        if(CommonUtil.SMS_CHANGE_MOBILE_TYPE.equals(verificateType)){
            AusVerificateCodeEntity ausVerificateCodeEntity=new AusVerificateCodeEntity();
            ausVerificateCodeEntity.setCustomerno(customerno);
            ausVerificateCodeEntity.setVerificatecode(msgCode);
            ausVerificateCodeEntity.setVerificatetype(verificateType);
            ausVerificateCodeEntity.setMobile(mobile);
            ausVerificateCodeEntityList=ausVerificateCodeEntityMapper.selectByEntity(ausVerificateCodeEntity);
        }else{
            AusVerificateCodeEntity ausVerificateCodeEntity=new AusVerificateCodeEntity();
            ausVerificateCodeEntity.setVerificatecode(msgCode);
            ausVerificateCodeEntity.setVerificatetype(verificateType);
            ausVerificateCodeEntity.setMobile(mobile);
            ausVerificateCodeEntityList=ausVerificateCodeEntityMapper.selectByEntity(ausVerificateCodeEntity);
        }
        if(ObjectUtils.isEmpty(ausVerificateCodeEntityList)){
            rs.fail(SendVerificationCodeEnum.MSGCODE_NOT_EXIST);
        }
        rs.success(SendVerificationCodeEnum.VERIFICATION_SUCCESS);
        return rs;
    }

}
