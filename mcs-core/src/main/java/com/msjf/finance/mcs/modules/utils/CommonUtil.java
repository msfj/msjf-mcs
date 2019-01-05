package com.msjf.finance.mcs.modules.utils;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.msjf.finance.mcs.common.response.Response;
import com.msjf.finance.mcs.modules.sms.dao.AusVerificateCodeEntityMapper;
import com.msjf.finance.mcs.modules.sms.dao.CifInviteCodeEntityMapper;
import com.msjf.finance.mcs.modules.sms.dao.SysParamsConfigEntityMapper;
import com.msjf.finance.mcs.modules.sms.entity.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component("commonUtil")
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
    @Resource
    SysParamsConfigEntityMapper sysParamsConfigMapper;
    @Resource
    CifInviteCodeEntityMapper cifInviteCodeEntityMapper;
    @Resource
    AusVerificateCodeEntityMapper ausVerificateCodeEntityMapper;
    @Resource
    SpringContextUtil springContextUtil;
    public String getSysConfigValue(String paramId, String paramType){
        SysParamsConfigEntityKey sysParamsConfigKey=new SysParamsConfigEntityKey();
        sysParamsConfigKey.setDistributorId(DISTRIBUTORID);
        sysParamsConfigKey.setExchangeId(EXCHANGEID);
        sysParamsConfigKey.setParamId(paramId);
        sysParamsConfigKey.setParamType(paramType);
        SysParamsConfigEntity sysParamsConfig=sysParamsConfigMapper.selectByPrimaryKey(sysParamsConfigKey);
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
    public Map<Integer,Object> getInviteCode1(final String customerno, String certificateno, final String isMember,
                                                     Response rs) {
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        if (!NO.equals(isMember) && !YES.equals(isMember)) {
            rs.fail("isMember传入值不合法");
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

        List<CifInviteCodeEntity> cs = cifInviteCodeEntityMapper
                .selectByEntity(c);
        if (CheckUtil.isNull(cs)) {
            String invitecode1 = CommonUtil.getVerifyCode(6);
            map.put(0, invitecode1);
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
        if (!CheckUtil.isNull(cc)) {
            String invitecode2 = cc.getInvitecode();
            map.put(1, invitecode2);
            return map;
        }
        String invitecode = CommonUtil.getVerifyCode(6);
        map.put(0, invitecode);
        return map;
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
     * @param rs
     * @return
     */
    public boolean checkMsgCode(String msgcode, String mobile, String verificate_type, Boolean delcode,
                                       Response rs) {

        String open = getSysConfigValue("sms_open_params_config", "sms_open_params_config");

        if (CommonUtil.YES.equals(open)) {
            if (CheckUtil.checkNull(msgcode, "短信验证码", rs)) {
                return false;
            }
            if (CheckUtil.checkNull(mobile, "手机号码", rs)) {
                return false;
            }
            if (CheckUtil.checkNull(verificate_type, "认证类型", rs)) {
                return false;
            }
            AusVerificateCodeEntityKey ausVerificateCodeEntityKey = new AusVerificateCodeEntityKey();
            ausVerificateCodeEntityKey.setMobile(mobile);
            ausVerificateCodeEntityKey.setVerificatetype(verificate_type);
            AusVerificateCodeEntity ausVerificateCodeEntity = ausVerificateCodeEntityMapper.selectByPrimaryKey(ausVerificateCodeEntityKey);
            if (CheckUtil.isNull(ausVerificateCodeEntity)) {
                rs.fail("请先获取验证码");
                return false;
            }
            if (!msgcode.equals(ausVerificateCodeEntity.getVerificatecode())) {
                rs.fail("验证码有误");
                return false;
            }
            if (!NO.equals(ausVerificateCodeEntity.getStatus())) {//0-未校验 1-已校验
                rs.fail("验证码已校验，请勿重复使用");
                return false;
            }
            if (DateUtil.getUserDate(DATE_FMT_DATETIME).compareTo(ausVerificateCodeEntity.getFailuretime()) > 0) {
                rs.fail("验证码已失效，请重新获取");
                return false;
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
        return true;
    }
    /**
     * 检查短信验证码是否正确
     *
     * @param msgcode         验证码
     * @param mobile          手机号码
     * @param verificate_type 认证类型 3- 手机号码换绑
     * @param delcode         是否删除认证记录
     * @param rs
     * @return
     */
    public boolean checkMsgCodeMoblieChange(String customerno,String msgcode, String mobile, String verificate_type, Boolean delcode,
                                                   Response rs) {
        String open =getSysConfigValue("sms_open_params_config", "sms_open_params_config");

        if (CommonUtil.YES.equals(open)) {
            if (CheckUtil.checkNull(msgcode, "短信验证码", rs)) {
                return false;
            }
            if (CheckUtil.checkNull(mobile, "手机号码", rs)) {
                return false;
            }
            if (CheckUtil.checkNull(verificate_type, "认证类型", rs)) {
                return false;
            }
            AusVerificateCodeEntityKey ausVerificateCodeEntityKey=new AusVerificateCodeEntityKey();
            ausVerificateCodeEntityKey.setVerificatetype(verificate_type);
            ausVerificateCodeEntityKey.setMobile(mobile);
            AusVerificateCodeEntity paramAusVerificateCodeEntity=new AusVerificateCodeEntity();
            paramAusVerificateCodeEntity.setCustomerno(customerno);
            paramAusVerificateCodeEntity.setMobile(mobile);
            paramAusVerificateCodeEntity.setVerificatetype(verificate_type);
            List<AusVerificateCodeEntity> ausVerificateCodeEntityList = ausVerificateCodeEntityMapper.selectByEntity(paramAusVerificateCodeEntity);
            if (CheckUtil.isNull(ausVerificateCodeEntityList)) {
                rs.fail("请先获取验证码");
                return false;
            }
            AusVerificateCodeEntity ausVerificateCodeEntity=ausVerificateCodeEntityList.get(0);
            if (!msgcode.equals(ausVerificateCodeEntity.getVerificatecode())) {
                rs.fail("验证码有误");
                return false;
            }
            if (!NO.equals(ausVerificateCodeEntity.getStatus())) {//0-未校验 1-已校验
                rs.fail("验证码已校验，请勿重复使用");
                return false;
            }
            if (DateUtil.getUserDate(DATE_FMT_DATETIME).compareTo(ausVerificateCodeEntity.getFailuretime()) > 0) {
                rs.fail("验证码已失效，请重新获取");
                return false;
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
        return true;
    }
}
