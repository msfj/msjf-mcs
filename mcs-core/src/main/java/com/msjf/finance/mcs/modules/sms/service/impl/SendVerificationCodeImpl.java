package com.msjf.finance.mcs.modules.sms.service.impl;

import com.msjf.finance.mcs.common.response.Response;
import com.msjf.finance.mcs.facade.sms.domain.VerificationCodeDomain;
import com.msjf.finance.mcs.modules.sms.dao.AusVerificateCodeEntityMapper;
import com.msjf.finance.mcs.modules.sms.entity.AusVerificateCodeEntity;
import com.msjf.finance.mcs.modules.sms.entity.AusVerificateCodeEntityKey;
import com.msjf.finance.mcs.modules.sms.service.SendVerificationCodeService;
import com.msjf.finance.mcs.modules.utils.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sendVerificationCodeImpl")
@Scope("prototype")
public class SendVerificationCodeImpl implements SendVerificationCodeService {
    @Resource
    AusVerificateCodeEntityMapper ausVerificateCodeEntityMapper;

    @Resource
    SpringContextUtil springContextUtil;
    /**
     * <pre>
     *     发送短信验证码服务
     *     入参必须有手机号和模板ID
     * </pre>
     *
     * @param mapParam
     */
    public Response<VerificationCodeDomain> SendRegisterVerificationCode(HashMap<String, Object> mapParam ) throws Exception{
        Response<VerificationCodeDomain> rs=new Response();
        rs.fail();
        String mobile = CheckUtil.isNull(mapParam.get("mobile")) ? "" : String.valueOf(mapParam.get("mobile"));

        /** 登录账号 */
        String loginname = CheckUtil.isNull(mapParam.get("loginname")) ? "" : String.valueOf(mapParam.get("loginname"));
        String customerno = CheckUtil.isNull(mapParam.get("customerno")) ? "" : String.valueOf(mapParam.get
                ("customerno"));
        String templateId = CheckUtil.isNull(mapParam.get("templateId")) ? "" : String.valueOf(mapParam.get
                ("templateId"));
        String verificatetype = CheckUtil.isNull(mapParam.get("verificatetype")) ? "" : String.valueOf(mapParam.get
                ("verificatetype"));
        String oldMsgcode=StringUtil.valueOf(mapParam.get("oldmsgcode"));
        String oldMobile=StringUtil.valueOf(mapParam.get("oldmobile"));
//        if ( CheckUtil.isNull(customerno)) {
//            rs.fail("客户代码不能为空");
//            return rs;
//        }
        if (CheckUtil.isNull(templateId)) {
            rs.fail("模板id不能为空");
            return rs;
        }
        if (CheckUtil.isNull(mobile) ) {
            rs.fail("手机号码不能为空");
            return rs;
        }
        //认证类型 0-服务平台注册 1-管理平台登录 2-修改密码 3-手机换绑
        if (CheckUtil.isNull(verificatetype)) {
            rs.fail("认证类型不能为空");
            return rs;
        }
        /**
         * 换绑第一次发送邀请码传customerno和mobile
         * 换绑输入新手机发送验证码customerno和mobile和oldmsgcode
         **/
        if(CommonUtil.SMS_CHANGE_MOBILE_TYPE.equals(verificatetype)){
            if(!CheckUtil.isNull(oldMsgcode)){
                if(CheckUtil.isNull(oldMobile)){
                    rs.fail("原手机不能为空");
                    return rs;
                }
                if(CheckUtil.isNull(customerno)){
                    rs.fail("客户代码不能为空");
                    return rs;
                }
                AusVerificateCodeEntity ausVerificateCodeEntity=new AusVerificateCodeEntity();
                ausVerificateCodeEntity.setMobile(oldMobile);
                ausVerificateCodeEntity.setCustomerno(customerno);
                ausVerificateCodeEntity.setStatus(CommonUtil.YES);
                ausVerificateCodeEntity.setVerificatetype(CommonUtil.SMS_CHANGE_MOBILE_TYPE);
                List<AusVerificateCodeEntity> ausVerificateCodeEntityList=ausVerificateCodeEntityMapper.selectByEntity(ausVerificateCodeEntity);
                if(CheckUtil.isNull(ausVerificateCodeEntityList)){
                    rs.fail("原手机号码校验不通过");
                    return rs;
                }
//                CifCustEntity cifCustEntity=cifCustPersistence.queryEntity(customerno);
                if(mobile.equals(oldMobile)){
                    rs.fail("换绑手机不能与原手机相同");
                    return rs;
                }
            }else{

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
        CommonUtil commonUtil=springContextUtil.getBean("commonUtil");
        //获取验证码有效时间
        String msgCodeFailureTime =commonUtil.getSysConfigValue("msgcode_failure_time", "code_failure_time");
        if (CheckUtil.isNull(msgCodeFailureTime)) {
//            rs.failed("系统参数异常");
            return rs;
        }
        if (!CheckUtil.isInteger(msgCodeFailureTime)) {
//            rs.failed("系统参数异常");
            return rs;
        }
//
//
//        //您的验证码是{verifyCode}在{activeSeconds}内有效{symbol}
//        //您的验证码是{xxxxxxxxxxxxxxx}在{xxxxxxxx}内有效{x}
//        //您的验证码是3212，在30分钟内有效。
            String code = UtilTools.getRandomCode(new BigDecimal(0), 4);
            mapParam.put("verifyCode", code + ",");
            mapParam.put("activeSeconds", Integer.parseInt(msgCodeFailureTime) / 60 + "分钟");
            mapParam.put("symbol", "。");
            mapParam.put("templateId", templateId);
            mapParam.put("mobile", mobile);
            mapParam.put("loginName", loginname);

          SmsService api = springContextUtil.getBean("SmsServiceApi");
            Response<List<Map>> irs = new Response();
            api.doService(mapParam, irs);
            if (!irs.checkIfSuccess()) {
                rs.fail(irs);
                return rs;
            }

        //将短信验证码写入kpfsp.aus_verificate_code 表
        AusVerificateCodeEntity ausVerificateCodeEntity = new AusVerificateCodeEntity();
        ausVerificateCodeEntity.setMobile(mobile);
        ausVerificateCodeEntity.setVerificatetype(verificatetype);
        ausVerificateCodeEntity.setStatus(CommonUtil.NO);
        ausVerificateCodeEntity.setVerificatecode(code);
        ausVerificateCodeEntity.setCustomerno(customerno); //手机换绑时（客户为登陆状态）写入客户代码

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, Integer.parseInt(msgCodeFailureTime));
        SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
        ausVerificateCodeEntity.setFailuretime(time.format(calendar.getTime()));

        //查询数据并检查
        AusVerificateCodeEntityKey ausVerificateCodeEntityKey=new AusVerificateCodeEntityKey();
        ausVerificateCodeEntityKey.setVerificatetype(CommonUtil.SMS_CHANGE_MOBILE_TYPE);
        ausVerificateCodeEntityKey.setMobile(mobile);
        AusVerificateCodeEntity verificateCodeEntity = ausVerificateCodeEntityMapper.selectByPrimaryKey(ausVerificateCodeEntityKey);
        if (CheckUtil.isNull(verificateCodeEntity)) {
            ausVerificateCodeEntityMapper.insert(ausVerificateCodeEntity);
        } else {
            ausVerificateCodeEntityMapper.updateByPrimaryKeySelective(ausVerificateCodeEntity);
        }

        //返回数据
        List<Map> irsResult =irs.getData();
        VerificationCodeDomain verificationCodeDomain=new VerificationCodeDomain();
        verificationCodeDomain.setActiveSeconds(msgCodeFailureTime);
        verificationCodeDomain.setSeqNum(String.valueOf(irsResult.get(0).get("seqNum")));
        rs.setData(verificationCodeDomain);
//        Map map = Maps.newHashMap();
//        map.put("seqNum", String.valueOf(irsResult.get(0).get("seqNum")));
//        map.put("activeSeconds", msgCodeFailureTime);
//        ResultUtil.makerSusResults(irs.getErrorMessage(), map, rs);
        return rs;
    }
}
