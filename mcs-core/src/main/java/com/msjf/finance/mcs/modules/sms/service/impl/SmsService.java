package com.msjf.finance.mcs.modules.sms.service.impl;


import com.google.common.collect.Maps;
import com.msjf.finance.mcs.common.response.Response;
import com.msjf.finance.mcs.modules.sms.dao.SpmMessageEntityMapper;
import com.msjf.finance.mcs.modules.sms.dao.SpmMsgTemplateEntityMapper;
import com.msjf.finance.mcs.modules.sms.dao.SysParamsConfigEntityMapper;
import com.msjf.finance.mcs.modules.sms.dao.SysSmsConfigEntityMapper;
import com.msjf.finance.mcs.modules.sms.entity.SpmMsgTemplateEntity;
import com.msjf.finance.mcs.modules.sms.entity.SysSmsConfigEntity;
import com.msjf.finance.mcs.modules.utils.CheckUtil;
import com.msjf.finance.mcs.modules.utils.CommonUtil;
import com.msjf.finance.mcs.modules.utils.SpringContextUtil;
import com.msjf.finance.mcs.modules.utils.TransactionManage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 *     短信发送API
 *
 */
@Scope("prototype")
@Service("SmsServiceApi")
public class SmsService {
    @Resource
    SysParamsConfigEntityMapper sysParamsConfigMapper;
    @Resource
    SpmMsgTemplateEntityMapper spmMsgTemplateEntityMapper;
    @Resource
    SysSmsConfigEntityMapper sysSmsConfigEntityMapper;
    /**
     * 模板ID
     */
    private String templateId;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 短信内容
     */
    private String templateContent;

    /**
     * 短信流水ID
     */
    private String seqNum;

    /**
     *
     */
    private Map map;

    /**
     * 客户发送方IP
     */
    private String smsIp;

    /**
     * 客户登入账号
     */
    private String loginName;


    /**
     * <pre>
     *     短信发送服务
     * <pre/>
     *
     * @param mapParam 入参
     *
     */
    public void doService(HashMap<String, Object> mapParam, Response rs) {
//
//        //1-获取入参
        getParam(mapParam);
//
//
        if (CheckUtil.isNull(templateId)) {
            //rs.failed("模板ID不能为空");
            return;
        }
//
        if (CheckUtil.isNull(mobile)) {
            //rs.failed("手机号不能为空");
            return;
        }
//
//        checkSmsLimit(rs);
//        if (!rs.isSuccessful()) {
//            return;
//        }
//
//
        //1-获取系统参数
        CommonUtil commonUtil=SpringContextUtil.getBean("commonUtil");
        String open =commonUtil.getSysConfigValue("sms_open_params_config", "sms_open_params_config");
        if (!("0".equals(open) || "1".equals(open))) {
//            rs.failed("系统参数异常");
            return;
        }
//
//        //2-获取短信内容
        if (getSendSmsContent(mapParam, rs)) {
            return;
        }

        //4-记录短信发送流水
//        TransactionManage transactionManage = new TransactionManage();
//        TransactionStatus status = null;
//        try {
//            status = transactionManage.newTransaction();
//            insertSpmMessage(rs);
//            transactionManage.commit(status);
//        } catch (Exception e) {
//            rs.fail("记录流水失败");
//            try {
//                transactionManage.rollback(status);
//            } catch (Exception e1) {
//                rs.fail("回滚失败");
//                throw new RuntimeException(e);
//            }
//            throw new RuntimeException(e);
//        }

        HashMap<String, String> outMap = Maps.newHashMap();
//
//        if (CommonUtil.NO.equals(open)) {
//            LogUtil.info("======根据控制参数设置不发送短信" + templateId + "======");
//            outMap.put("result", CommonUtil.NO);
//            outMap.put("description", "模拟发送成功");
//            rs.successful("发送成功");
//        }
        if (CommonUtil.YES.equals(open)) {
            //5-查询短信配置
            SysSmsConfigEntity sysSmsConfigEntity = checkSysSmsConfig(rs);
            if (sysSmsConfigEntity == null || CommonUtil.NO.equals(rs.getMsg())) {
                rs.fail(rs.getMsg());
                outMap.put("result", "9999");
                outMap.put("description", "查询短信配置异常:" + rs.getMsg());
//                updateSpmMessage(outMap, rs);
                return;
            }
            //6-发送短信
            outMap = sendSmsMessage(sysSmsConfigEntity,rs);
        }
//
//        //5-更新短信发送流水
//        updateSpmMessage(outMap, rs);
//
        if (CommonUtil.NO.equals(outMap.get("result"))) {
            Map map = Maps.newHashMap();
            map.put("seqNum", seqNum);
            rs.success(outMap.get("description"),map);
            //ResultUtil.makerSusResults(outMap.get("description"), map, rs);
        } else {
            rs.fail(outMap.get("description"));
        }
    }
//
//    /**
//     * <pre>
//     *  控制短信发送限制
//     *     同一个ip限制每天发送的短信次数
//     *     同一个账号限制每天发送的短信次数
//     *     同一个手机号码每天接收平台短信的次数
//     * </pre>
//     */
//    private void checkSmsLimit(IResult rs) {
//
//        //检查手机号码是否为空,不为空检查手机当天发送短信次数是否超限，并更新数据
//        SpmSmsControlTbPersistence ps = PersistenceUtil.getPersistence(SpmSmsControlTbPersistence.class);
//        SpmSmsControlTbKey spmSmsControlTbKey = new SpmSmsControlTbKey();
//
//        if (!CheckUtil.isNull(mobile)) {
//            String mobilePater = CommonUtil.getSysConfigValue("sms_mobile_params_config", "sms_mobile_params_config");
//            if (CheckUtil.isNull(mobilePater) || !CheckUtil.isInteger(mobilePater)) {
//                rs.failed("系统参数异常");
//                return;
//            }
//
//            spmSmsControlTbKey.setControlKey(mobile);
//            SpmSmsControlTbEntity controlTbEntityMobile = ps.queryEntityLock(spmSmsControlTbKey);
//            if (!CheckUtil.isNull(controlTbEntityMobile)) {
//                if (DateUtil.getUserDate("yyyyMMdd").equals(controlTbEntityMobile.getUpdateDate())) {
//                    if (CheckUtil.isNull(controlTbEntityMobile.getTodayTotal()) || !CheckUtil.isInteger(String
//                            .valueOf(controlTbEntityMobile.getTodayTotal()))) {
//                        controlTbEntityMobile.setTodayTotal(KPConstant.ZERO_INT);
//                    }
//                    if (controlTbEntityMobile.getTodayTotal() >= Integer.parseInt(mobilePater)) {
//                        rs.failed("手机号【" + mobile + "】当日短信发送次数已达上限");
//                        return;
//                    }
//                } else {
//                    controlTbEntityMobile.setTodayTotal(KPConstant.ZERO_INT);
//                }
//            }
//
//            if (!updateDate(controlTbEntityMobile, mobile)) {
//                rs.failed("更新数据失败");
//                return;
//            }
//
//        }
//        //检查ip是否为空,不为空检查手机当天发送短信次数是否超限，并更新数据
//        if (!CheckUtil.isNull(smsIp)) {
//            String smsIpPater = CommonUtil.getSysConfigValue("sms_smsIp_params_config", "sms_smsIp_params_config");
//            if (CheckUtil.isNull(smsIpPater) || !CheckUtil.isInteger(smsIpPater)) {
//                rs.failed("系统参数异常");
//                return;
//            }
//
//            spmSmsControlTbKey.setControlKey(smsIp);
//            SpmSmsControlTbEntity controlTbEntityMobile = ps.queryEntityLock(spmSmsControlTbKey);
//            if (!CheckUtil.isNull(controlTbEntityMobile)) {
//                if (DateUtil.getUserDate("yyyyMMdd").equals(controlTbEntityMobile.getUpdateDate())) {
//                    if (CheckUtil.isNull(controlTbEntityMobile.getTodayTotal()) || !CheckUtil.isInteger(String
//                            .valueOf(controlTbEntityMobile.getTodayTotal()))) {
//
//                        controlTbEntityMobile.setTodayTotal(KPConstant.ZERO_INT);
//                    }
//                    if (controlTbEntityMobile.getTodayTotal() >= Integer.parseInt(smsIpPater)) {
//                        rs.failed("IP【" + smsIp + "】当日短信发送次数已达上限");
//                        return;
//                    }
//                } else {
//                    controlTbEntityMobile.setTodayTotal(KPConstant.ZERO_INT);
//                }
//
//            }
//            if (!updateDate(controlTbEntityMobile, smsIp)) {
//                rs.failed("更新数据失败");
//                return;
//            }
//        }
//        //检查登入名是否为空,不为空检查手机当天发送短信次数是否超限，并更新数据
//        if (!CheckUtil.isNull(loginName)) {
//            String loginNamePater = CommonUtil.getSysConfigValue("sms_loginName_params_config",
//                    "sms_loginName_params_config");
//            if (CheckUtil.isNull(loginNamePater) || !CheckUtil.isInteger(loginNamePater)) {
//                rs.failed("系统参数异常");
//                return;
//            }
//
//            spmSmsControlTbKey.setControlKey(loginName);
//            SpmSmsControlTbEntity controlTbEntityMobile = ps.queryEntityLock(spmSmsControlTbKey);
//            if (!CheckUtil.isNull(controlTbEntityMobile)) {
//                if (DateUtil.getUserDate("yyyyMMdd").equals(controlTbEntityMobile.getUpdateDate())) {
//                    if (CheckUtil.isNull(controlTbEntityMobile.getTodayTotal()) || !CheckUtil.isInteger(String
//                            .valueOf(controlTbEntityMobile.getTodayTotal()))) {
//
//                        controlTbEntityMobile.setTodayTotal(KPConstant.ZERO_INT);
//                    }
//
//                    if (controlTbEntityMobile.getTodayTotal() >= Integer.parseInt(loginNamePater)) {
//                        rs.failed("登入名【" + loginName + "】当日短信发送次数已达上限");
//                        return;
//                    }
//                } else {
//                    controlTbEntityMobile.setTodayTotal(KPConstant.ZERO_INT);
//                }
//            }
//            if (!updateDate(controlTbEntityMobile, loginName)) {
//                rs.failed("更新数据失败");
//                return;
//            }
//        }
//        rs.successful("检查成功");
//    }
//
//    /**
//     * 更新数据
//     *
//     * @param controlTbEntityMobile 查询的实体数据
//     */
//    private boolean updateDate(SpmSmsControlTbEntity controlTbEntity, String controlKey) {
//        SpmSmsControlTbPersistence ps = PersistenceUtil.getPersistence(SpmSmsControlTbPersistence.class);
//        SpmSmsControlTbEntity smsControlTbEntity = new SpmSmsControlTbEntity();
//        smsControlTbEntity.setControlKey(controlKey);
//        smsControlTbEntity.setUpdateDate(DateUtil.getUserDate("yyyyMMdd"));
//        smsControlTbEntity.setUpdateTime(DateUtil.getUserDate("hhmmss"));
//        if (CheckUtil.isNull(controlTbEntity)) {
//            smsControlTbEntity.setCumulativeTotal(KPConstant.ONE_INT);
//            smsControlTbEntity.setTodayTotal(KPConstant.ONE_INT);
//            smsControlTbEntity.setInsertDate(DateUtil.getUserDate("yyyyMMdd"));
//            smsControlTbEntity.setInsertTime(DateUtil.getUserDate("hhmmss"));
//            ps.insert(smsControlTbEntity);
//        } else {
//            smsControlTbEntity.setCumulativeTotal((CheckUtil.isNull(controlTbEntity.getCumulativeTotal()) ? 0 :
//                    controlTbEntity.getCumulativeTotal()) + KPConstant.ONE_INT);
//            smsControlTbEntity.setTodayTotal((CheckUtil.isNull(controlTbEntity.getTodayTotal()) ? 0 : controlTbEntity
//                    .getTodayTotal()) + KPConstant.ONE_INT);
//            ps.update(smsControlTbEntity);
//        }
//        return true;
//    }
//
//    /**
//     * <pre>
//     *     更新短信流水表
//     * </pre>
//     *
//     * @param outMap 短信发生结果
//     */
//    private void updateSpmMessage(HashMap<String, String> outMap, IResult rs) {
//        SpmMessageEntity spmMessageEntity = new SpmMessageEntity();
//        spmMessageEntity.setSeqNum(seqNum);
//        if (CommonUtil.NO.equals(outMap.get("result"))) {
//            spmMessageEntity.setSendnotestatus(CommonUtil.YES);
//        }
//        spmMessageEntity.setSendnotestatus(CommonUtil.NO);
//        spmMessageEntity.setSendnotereply(JsonUtil.toJson(outMap));
//
//        TransactionManage transactionManage = new TransactionManage();
//        TransactionStatus status = null;
//
//        try {
//            status = transactionManage.newTransaction();
//            PersistenceUtil.getPersistence(SpmMessagePersistence.class).update(spmMessageEntity);
//            transactionManage.commit(status);
//        } catch (WsException e) {
//            try {
//                transactionManage.rollback(status);
//            } catch (WsException e1) {
//                LogUtil.info("流水更新失败,回滚失败");
//                throw new WsRuntimeException(e1);
//            }
//            rs.failed("");
//            LogUtil.info("流水更新失败");
//            throw new WsRuntimeException(e);
//        }
//        //rs.successful("更新成功");
//    }
//
    /**
     * <pre>
     *     短信发送
     * </pre>
     *
     * @param sysSmsConfigEntity 短信配置
     *
     * @return outMap 发送结果数据
     */
    private HashMap<String, String> sendSmsMessage(SysSmsConfigEntity sysSmsConfigEntity,Response rs) {
        HashMap<String, String> outMap = null;
        String password = sysSmsConfigEntity.getPassword();
        SmsStub.SmsResponse resp = null;
        SmsStub stub;
        try {
            stub = new SmsStub(sysSmsConfigEntity.getUrl());
        } catch (Exception e) {
//            rs.failed("短信发送失败,网络异常");
//            LogUtil.info("短信发送失败,网络异常" + e);
            outMap.put("result", "999");
            outMap.put("description", "短信发送失败,网络异常");
//            updateSpmMessage(outMap, rs);
            throw new RuntimeException(e);
        }
        //发送接口
        SmsStub.Sms sms0 = new SmsStub.Sms();
        sms0.setIn0(sysSmsConfigEntity.getUserId());//企业编号
        sms0.setIn1(sysSmsConfigEntity.getAccount());//登录名
        sms0.setIn2(password);//密码
//        sms0.setIn3(templateContent);//短信内容
        sms0.setIn3(templateContent);//短信内容
        sms0.setIn4(mobile);//手机号码
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        sms0.setIn5(seqNum = "000" + format.format(new Date()));
        sms0.setIn6("");
        sms0.setIn7("1");
        sms0.setIn8("");
        try {
            resp = stub.Sms(sms0);
            //result=0&description=发送短信成功&taskid=2031205309919&faillist=&task_id=2031205309919
            //result=28&description=发送内容与模板不符
//            LogUtil.info("短信发送成功===" + resp.getOut());
            outMap = URLRequest(resp.getOut());
            rs.success("短信发送成功");
        } catch (Exception e) {
//            rs.failed("短信发送失败");
//            LogUtil.info("短信发送失败" + e);
//            com.websuites.utils.LogUtil.debug("短信发送失败" + e);
            outMap.put("result", "999");
            outMap.put("description", "程序异常");
//            updateSpmMessage(outMap, rs);
            //throw new WsRuntimeException(e);发送失败不回滚业务
        }
        return outMap;
    }

    /**
     * <pre>
     *     查询和检查短信配置信息
     * </pre>
     *
     * @param rs 结果集
     * @return SysSmsConfigEntity 短信配置实体
     */
    private SysSmsConfigEntity checkSysSmsConfig(Response rs) {
        SysSmsConfigEntity smsConfigEntity = new SysSmsConfigEntity();
        smsConfigEntity.setSmsType("1");
        List<SysSmsConfigEntity> sysSmsConfigEntityList = sysSmsConfigEntityMapper.selectByEntity(smsConfigEntity);
        if (CheckUtil.isNull(sysSmsConfigEntityList)) {
//            LogUtil.info("未查询到短信配置信息===" + sysSmsConfigEntityList.toString());
            rs.fail("未查询到短信配置信息");
            return null;
        }
        SysSmsConfigEntity sysSmsConfigEntity=sysSmsConfigEntityList.get(0);
        if (0==sysSmsConfigEntity.getStatus()) {
//            LogUtil.info("短信配置未启用===" + sysSmsConfigEntity.getStatus());
            rs.fail("短信配置未启用");
            return null;
        }
        if (CheckUtil.isNull(sysSmsConfigEntity.getUserId())) {
//            LogUtil.info("企业编号编号不能为空");
            rs.fail("短信配置(企业编号编号不能为空)");
            return null;

        }
        if (CheckUtil.isNull(sysSmsConfigEntity.getAccount())) {
//            LogUtil.info("登录名不能为空");
            rs.fail("短信配置(登录名不能为空)");
            return null;
        }
        if (CheckUtil.isNull(sysSmsConfigEntity.getPassword())) {
//            LogUtil.info("密码不能为空");
            rs.fail("短信配置(密码不能为空)");
            return null;
        }
        if (CheckUtil.isNull(sysSmsConfigEntity.getUrl())) {
//            LogUtil.info("url不能为空");
            rs.fail("短信配置(url不能为空)");
            return null;
        }
        rs.success("查询成功");
        return sysSmsConfigEntity;
    }

//    /**
//     * 记录短信发送流水
//     *
//     * @return SpmMessageEntity 流水实体
//     */
//    private void insertSpmMessage(IResult rs) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//        seqNum = "000" + format.format(new Date());
//        map.put("seqNum", seqNum);
//        map.put("templateId", templateId);
//        map.put("mobile", mobile);
//
//        SpmMessageEntity spmMessageEntity = new SpmMessageEntity();
//        spmMessageEntity.setSeqNum(seqNum);
//        spmMessageEntity.setTitle("短信发送");
//        spmMessageEntity.setMobilephone(mobile);
//        spmMessageEntity.setCustomerno("");
//        spmMessageEntity.setMessagecontent(templateContent);
//        spmMessageEntity.setSendchanel("2");
//        spmMessageEntity.setSenddisplay("0");
//        spmMessageEntity.setMessageRemark("");
//        spmMessageEntity.setSenddate(DateUtil.getUserDate("yyyyMMddHHmmss"));
//        spmMessageEntity.setSendnotestatus("");
//        spmMessageEntity.setMessageparam(JsonUtil.toJson(map));
//
//        TransactionManage transactionManage = new TransactionManage();
//        TransactionStatus status = null;
//        try {
//            status = transactionManage.newTransaction();
//            PersistenceUtil.getPersistence(SpmMessagePersistence.class).insert(spmMessageEntity);
//            transactionManage.commit(status);
//        } catch (WsException e) {
//            try {
//                transactionManage.rollback(status);
//            } catch (WsException e1) {
//                rs.failed("记录流水失败,回滚流水失败");
//                throw new WsRuntimeException(e);
//            }
//            rs.failed("记录流水失败");
//            throw new WsRuntimeException(e);
//        }
//        rs.successful("保存成功");
//    }
//
    /**
     * <pre>
     *     获取短信模板中的内容,并替换关键字和检查关键字是否存在
     * </pre>
     *
     * @param mapParam
     * @param
     * @return
     */
    private boolean getSendSmsContent(HashMap<String, Object> mapParam, Response rs) {
        //2-获取短信模板
        SpmMsgTemplateEntity templateEntity = new SpmMsgTemplateEntity();
        templateEntity.setExchangeId(CommonUtil.YES);
        templateEntity.setDistributorId("100");
        templateEntity.setTemplateId(templateId);
        templateEntity.setStatus(CommonUtil.I_YES);
        List<SpmMsgTemplateEntity> spmMsgTemplateEntityList = spmMsgTemplateEntityMapper.selectByEntityLock(templateEntity);
        if (spmMsgTemplateEntityList.size() == CommonUtil.I_NO) {
//            LogUtil.info("============请检查短信模板" + templateId + "是否开启或配置============");
            rs.fail("请检查短信模板是否开启或配置");
            return true;
        }
        //3-替换短信模板关键字
        templateContent = spmMsgTemplateEntityList.get(0).getTemplateContent();
        if (CheckUtil.isNull(templateContent.trim())) {
            rs.fail("========短信模板为空==========");
            return true;
        }
        String templateKeys = spmMsgTemplateEntityList.get(0).getTemplateKeys();
        if (!CheckUtil.isNull(templateKeys.trim())) {

            String[] templateKeysString = templateKeys.split(",");
            List<String> arrayList = Arrays.asList(templateKeysString);
            List<String> templateKeysList = new ArrayList<String>(arrayList);

            for (String key : templateKeysList) {
                if (templateContent.indexOf(key) == -1) {
                    rs.fail("模板关键字" + key + "在模板中不存在");
                    return true;
                }
                if (CheckUtil.isNull(mapParam.get(key))) {
                    rs.fail("模板关键字" + key + "的值不能为空");
                    return true;
                }
                map.put(key, mapParam.get(key));
//                LogUtil.info("=========key:" + key + " value:" + mapParam.get(key));
                templateContent = templateContent.replaceAll(key, String.valueOf(mapParam.get(key)).trim());
            }
//            LogUtil.info("待处理短信内容===" + templateContent);
            templateContent = templateContent.replaceAll("[[{}]]", "");
        }

//        LogUtil.info("待发送短信内容===" + templateContent);

        return false;
    }

    private void getParam(HashMap<String, Object> mapParam) {
        templateId = CheckUtil.isNull(mapParam.get("templateId")) ? "" : String.valueOf(mapParam.get("templateId"));
        mobile = CheckUtil.isNull(mapParam.get("mobile")) ? "" : String.valueOf(mapParam.get("mobile"));
        smsIp = CheckUtil.isNull(mapParam.get("smsIp")) ? "" : String.valueOf(mapParam.get("smsIp"));
        loginName = CheckUtil.isNull(mapParam.get("loginName")) ? "" : String.valueOf(mapParam.get("loginName"));
        map = Maps.newHashMap();
    }


    /**
     * 解析出url参数中的键值对
     * 如 "Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     *
     * @return url请求参数部分
     */
    public static HashMap<String, String> URLRequest(String strUrlParam) {
        HashMap<String, String> mapRequest = new HashMap<String, String>();

        String[] arrSplit;

        //每个键值为一组 www.2cto.com
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");

            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (arrSplitEqual[0] != "") {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }
//
//
    public static void main(String[] args) {
        Response rs=new Response();
        SysSmsConfigEntity sysSmsConfigEntity=new SysSmsConfigEntity();
        sysSmsConfigEntity.setAccount("nb_msfw");
        sysSmsConfigEntity.setUserId("247058");
        sysSmsConfigEntity.setPassword("Nbsm741!+?");
        sysSmsConfigEntity.setUrl("http://ums.zj165.com:8888/sms/services/Sms?wsdl");
        HashMap map=new SmsService().sendSmsMessage(sysSmsConfigEntity,rs);
        System.out.println(map);
    }

}
