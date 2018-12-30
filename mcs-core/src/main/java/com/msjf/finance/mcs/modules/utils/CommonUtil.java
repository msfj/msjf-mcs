package com.msjf.finance.mcs.modules.utils;

import com.msjf.finance.mcs.modules.sms.dao.SysParamsConfigEntityMapper;
import com.msjf.finance.mcs.modules.sms.entity.SysParamsConfigEntity;
import com.msjf.finance.mcs.modules.sms.entity.SysParamsConfigEntityKey;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CommonUtil {
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
    @Resource
    SysParamsConfigEntityMapper sysParamsConfigMapper;
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
}
