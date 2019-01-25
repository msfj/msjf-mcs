package com.msjf.finance.mcs.modules.utils;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.msjf.finance.mcs.facade.sms.domain.VerificationCodeDomain;
import com.msjf.finance.mcs.modules.sms.dao.AusVerificateCodeEntityMapper;
import com.msjf.finance.mcs.modules.sms.dao.CifInviteCodeEntityMapper;
import com.msjf.finance.mcs.modules.sms.dao.SysParamsConfigEntityMapper;
import com.msjf.finance.mcs.modules.sms.entity.*;
import com.msjf.finance.mcs.modules.sms.service.impl.SmsService;
import com.msjf.finance.mcs.modules.utils.emun.CommonUtilEmun;
import com.msjf.finance.msjf.core.response.Response;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collector;

public class CommonUtil {
    /**
     * 日期格式 yyyyMMdd
     */
    public static final String DATE_FMT_DATE = "yyyyMMdd";

    /**
     * 日期格式 HHmmss
     */
    public static final String DATE_FMT_TIME = "HHmmss";

    /**
     * 日期格式 yyyyMMddHHmmss
     */
    public static final String DATE_FMT_DATETIME = "yyyyMMddHHmmss";
    /**
     * 交易所编号
     */
    public static final String EXCHANGEID = "1";

    /**
     * 经纪商代码
     */
    private static final String DISTRIBUTORID = "100";

    /**
     * 是
     */
    public static final String YES = "1";

    /**
     * 否
     */
    public static final String NO = "0";

    /**
     * 是
     */
    public static final Integer I_YES = 1;

    /**
     * 否
     */
    public static final Integer I_NO = 0;
    /**
     * 认证类型 0-服务平台注册 1-管理平台登录 2-修改密码 3-手机换绑
     */
    public static final String SMS_REGISTER_TYPE = "0";
    public static final String SMS_LOGIN_TYPE = "1";
    public static final String SMS_CHANGE_PWD_TYPE = "2";
    public static final String SMS_CHANGE_MOBILE_TYPE = "3";

    public static String getSysConfigValue(String paramId, String paramType){
        SysParamsConfigEntityKey sysParamsConfigKey=new SysParamsConfigEntityKey();
        sysParamsConfigKey.setDistributorId(DISTRIBUTORID);
        sysParamsConfigKey.setExchangeId(EXCHANGEID);
        sysParamsConfigKey.setParamId(paramId);
        sysParamsConfigKey.setParamType(paramType);
        SysParamsConfigEntityMapper sysParamsConfigEntityMapper=SpringContextUtil.getBean("sysParamsConfigEntityMapper");
        SysParamsConfigEntity sysParamsConfig=sysParamsConfigEntityMapper.selectByPrimaryKey(sysParamsConfigKey);
        return sysParamsConfig.getParamValue();
    }

    /**
     * 获取邀请码(邀请码已发送未失效时，给出提示不允许重复发送)
     * 有且仅有一条  ismember=0 customerno(邀请者客户代码) certificateno status=0 failuretime>当前时间
     * 有且仅有一条  ismember=1 orgcustomerno(企业客户代码) certificateno status=0 failuretime>当前时间
     *
     * @param customerno    企业迁入时传入邀请者客户代码，其他情况传入企业客户代码
     * @param certificateno 受邀人证件号
     * @param rs            返回""时无需发送邀请码
     * @return
     */
    public static Map<String,String> getInviteCode(final String customerno, String certificateno, final String isMember,
                                                     Response rs) {
        HashMap<String, String> map = Maps.newHashMap();
        //是否重新获取一个邀请码
        String isNewCode="1";
        String inviteCode;
        if (!NO.equals(isMember) && !YES.equals(isMember)) {
            rs.fail(CommonUtilEmun.MSG_PARAM_ERROR);
            throw new RuntimeException(rs.getMsg());
        }
        CifInviteCodeEntity c = new CifInviteCodeEntity();
        if (YES.equals(isMember)) {
            c.setOrgcustomerno(customerno);
            c.setCertificateno(certificateno);
            c.setStatus(NO);
        } else {
            c.setCertificateno(certificateno);
            c.setStatus(NO);
        }
        CifInviteCodeEntityMapper cifInviteCodeEntityMapper=SpringContextUtil.getBean("cifInviteCodeEntityMapper");
        List<CifInviteCodeEntity> cs = cifInviteCodeEntityMapper
                .selectByEntity(c);
        if (ObjectUtils.isEmpty(cs)) {
            inviteCode = CommonUtil.getVerifyCode(6);
            map.put("isNewCode",isNewCode);
            map.put("inviteCode",inviteCode);
            return map;
        }
        CifInviteCodeEntity cc = Iterators.find(cs.iterator(), new Predicate<CifInviteCodeEntity>() {

            @Override
            public boolean apply(CifInviteCodeEntity a) {
                String custid = "";
                if (YES.equals(isMember)) {
                    custid = a.getOrgcustomerno();//企业客户代码
                } else {
                    custid = a.getCustomerno();//邀请者客户代码
                }
                //是否存在未使用且未失效的邀请码
                return customerno.equals(custid) && isMember.equals(a.getIsmember())
                        && DateUtil.getUserDate(DATE_FMT_DATETIME).compareTo(a.getFailuretime()) <= 0;
            }

        }, null);
        if (!ObjectUtils.isEmpty(cc)) {
            inviteCode = cc.getInvitecode();
            isNewCode="0";
        }else {
            inviteCode = CommonUtil.getVerifyCode(6);
        }
        map.put("isNewCode",isNewCode);
        map.put("inviteCode",inviteCode);
        return map;
    }
    /**
     * 发送邀请认证短信
     *
     * @param companyName
     * @param mobile
     * @param invitecode
     * @param rs
     */
    public static Response<VerificationCodeDomain> sendInviteCode(String companyName, String mobile, String invitecode, String customerno,
                                                                  String smsIp, String certificateno, Response rs) {
//        if (CheckUtil.checkNull(companyName, "企业名称", rs)) {
//            throw new WsRuntimeException(rs.getErrorMessage());
//        }
//        if (CheckUtil.checkNull(mobile, "手机号", rs)) {
//            throw new WsRuntimeException(rs.getErrorMessage());
//        }
//        if (CheckUtil.checkNull(invitecode, "邀请码", rs)) {
//            throw new WsRuntimeException(rs.getErrorMessage());
//        }
//        if (CheckUtil.checkNull(customerno, "客户代码", rs)) {
//            throw new WsRuntimeException(rs.getErrorMessage());
//        }
        String validtime = getSysConfigValue("invitecode_failure_time", "code_failure_time");
        //{name}企业邀请您关注{dot}邀请码{code}在{validtime}内有效{mark}
        HashMap<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("name", companyName);
        mapParam.put("dot", "，");
        mapParam.put("code", invitecode);
        mapParam.put("validtime", validtime + "小时");
        mapParam.put("mark", "。");
        mapParam.put("templateId", "2031012026748");
        mapParam.put("mobile", mobile);
        mapParam.put("loginName",certificateno);
        mapParam.put("smsIp", smsIp);
        SmsService api = (SmsService) SpringContextUtil.getBean("SmsServiceApi");
        return api.doService(mapParam);
    }
    /**
     * 获取系统参数
     *
     * @param paramId
     * @param paramType
     * @return
     */
//    public static String getSysConfigValue1(String paramId, String paramType) {
//        String key = CACHE_SPM_SYS_PARAMS_CONFIG + ":" + EXCHANGEID + ":" + DISTRIBUTORID + ":" + paramId + paramType;
//        RedisClientTemplate rct = (RedisClientTemplate) SpringContextHelper.getBean("redisClientTemplate");
//        String sysParamVal = rct.hget(key, "paramValue");
//        if (!CheckUtil.isNull(sysParamVal)) {
//            return sysParamVal;
//        } else {
//            SysParamsConfigEntity sysParamsConfigEntity = PersistenceUtil
//                    .getPersistence(SysParamsConfigPersistence.class)
//                    .queryEntity(EXCHANGEID, DISTRIBUTORID, paramType, paramId);
//            if (CheckUtil.isNull(sysParamsConfigEntity)) {
//                throw new WsRuntimeException("系统参数获取失败");
//            }
//            rct.hset(key, "paramValue", sysParamsConfigEntity.getParamValue());
//            return sysParamsConfigEntity.getParamValue();
//        }
//
//    }
    /**
     * 获取指定位数的验证码
     *
     * @param verifySize
     * @return
     */
    public static String getVerifyCode(int verifySize) {
        String sources = "123456789";
        int codesLen = sources.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(verifySize);
        for (int i = 0; i < verifySize; i++) {
            verifyCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
        }
        return verifyCode.toString();
    }
    /**
     * 检查短信验证码是否正确
     *
     * @param msgcode         验证码
     * @param mobile          手机号码
     * @param verificate_type 认证类型 0-服务平台注册 1-管理平台登录
     * @param delcode         是否删除认证记录
     * @return
     */
    public static Response checkMsgCode(String msgcode, String mobile, String verificate_type, Boolean delcode) {

        String open = getSysConfigValue("sms_open_params_config", "sms_open_params_config");

        if (CommonUtil.YES.equals(open)) {
            if (StringUtils.isEmpty(msgcode)) {
                return new Response<>().fail(CommonUtilEmun.MSG_PARAM_NULL);
            }
            if (StringUtils.isEmpty(mobile)) {
                return new Response<>().fail(CommonUtilEmun.MSG_PARAM_NULL);
            }
            if (StringUtils.isEmpty(verificate_type)) {
                return new Response<>().fail(CommonUtilEmun.MSG_PARAM_NULL);
            }
            AusVerificateCodeEntityKey ausVerificateCodeEntityKey = new AusVerificateCodeEntityKey();
            ausVerificateCodeEntityKey.setMobile(mobile);
            ausVerificateCodeEntityKey.setVerificatetype(verificate_type);
            AusVerificateCodeEntityMapper ausVerificateCodeEntityMapper=SpringContextUtil.getBean("ausVerificateCodeEntityMapper");
            AusVerificateCodeEntity ausVerificateCodeEntity = ausVerificateCodeEntityMapper.selectByPrimaryKey(ausVerificateCodeEntityKey);
            if (ObjectUtils.isEmpty(ausVerificateCodeEntity)) {
                return new Response<>().fail(CommonUtilEmun.VALIDE_CODE_NULL);
            }
            if (!msgcode.equals(ausVerificateCodeEntity.getVerificatecode())) {
                return new Response<>().fail(CommonUtilEmun.VALIDE_CODE_ERROR);

            }
            if (!NO.equals(ausVerificateCodeEntity.getStatus())) {//0-未校验 1-已校验
                return new Response<>().fail(CommonUtilEmun.VALIDE_CODE_IS_USED);

            }
            if (DateUtil.getUserDate(DATE_FMT_DATETIME).compareTo(ausVerificateCodeEntity.getFailuretime()) > 0) {
                return new Response<>().fail(CommonUtilEmun.VALIDE_CODE_OVRE_TIME);

            }
            if (delcode) {
                ausVerificateCodeEntityMapper.deleteByPrimaryKey(ausVerificateCodeEntityKey);
            } else {
                AusVerificateCodeEntity e = new AusVerificateCodeEntity();
                e.setMobile(mobile);
                e.setVerificatetype(verificate_type);
                e.setStatus(YES);//1-已校验 0-未校验
                ausVerificateCodeEntityMapper.updateByPrimaryKeySelective(e);
            }
        }
        return new Response().success(CommonUtilEmun.CHECK_SUCCESS);
    }
    /**
     * 检查短信验证码是否正确
     *
     * @param msgcode         验证码
     * @param mobile          手机号码
     * @param verificate_type 认证类型 3- 手机号码换绑
     * @param delcode         是否删除认证记录

     * @return
     */
    public static Response checkMsgCodeMoblieChange(String customerno,String msgcode, String mobile, String verificate_type, Boolean delcode) {
        String open =getSysConfigValue("sms_open_params_config", "sms_open_params_config");

        if (CommonUtil.YES.equals(open)) {
            if (StringUtils.isEmpty(customerno)) {
                return new Response<>().fail(CommonUtilEmun.MSG_PARAM_NULL);
            }
            if (StringUtils.isEmpty(msgcode)) {
                return new Response<>().fail(CommonUtilEmun.MSG_PARAM_NULL);
            }
            if (StringUtils.isEmpty(mobile)) {
                return new Response<>().fail(CommonUtilEmun.MSG_PARAM_NULL);
            }
            if (StringUtils.isEmpty(verificate_type)) {
                return new Response<>().fail(CommonUtilEmun.MSG_PARAM_NULL);
            }
            AusVerificateCodeEntityKey ausVerificateCodeEntityKey=new AusVerificateCodeEntityKey();
            ausVerificateCodeEntityKey.setVerificatetype(verificate_type);
            ausVerificateCodeEntityKey.setMobile(mobile);
            AusVerificateCodeEntity paramAusVerificateCodeEntity=new AusVerificateCodeEntity();
            paramAusVerificateCodeEntity.setCustomerno(customerno);
            paramAusVerificateCodeEntity.setMobile(mobile);
            paramAusVerificateCodeEntity.setVerificatetype(verificate_type);
            AusVerificateCodeEntityMapper ausVerificateCodeEntityMapper=SpringContextUtil.getBean("ausVerificateCodeEntityMapper");
            List<AusVerificateCodeEntity> ausVerificateCodeEntityList = ausVerificateCodeEntityMapper.selectByEntity(paramAusVerificateCodeEntity);
            AusVerificateCodeEntity ausVerificateCodeEntity=ausVerificateCodeEntityList.get(0);
            if (ObjectUtils.isEmpty(ausVerificateCodeEntity)) {
                return new Response<>().fail(CommonUtilEmun.VALIDE_CODE_NULL);
            }
            if (!msgcode.equals(ausVerificateCodeEntity.getVerificatecode())) {
                return new Response<>().fail(CommonUtilEmun.VALIDE_CODE_ERROR);

            }
            if (!NO.equals(ausVerificateCodeEntity.getStatus())) {//0-未校验 1-已校验
                return new Response<>().fail(CommonUtilEmun.VALIDE_CODE_IS_USED);

            }
            if (DateUtil.getUserDate(DATE_FMT_DATETIME).compareTo(ausVerificateCodeEntity.getFailuretime()) > 0) {
                return new Response<>().fail(CommonUtilEmun.VALIDE_CODE_OVRE_TIME);

            }
            if (delcode) {
                ausVerificateCodeEntityMapper.deleteByPrimaryKey(ausVerificateCodeEntityKey);
            } else {
                AusVerificateCodeEntity e = new AusVerificateCodeEntity();
                e.setMobile(mobile);
                e.setCustomerno(customerno);
                e.setVerificatetype(verificate_type);
                e.setStatus(YES);//1-已校验 0-未校验
                ausVerificateCodeEntityMapper.updateByPrimaryKeySelective(e);
            }
        }
        return new Response().success(CommonUtilEmun.CHECK_SUCCESS);
    }
}
