package com.msjf.finance.mcs.modules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by csw on 2019/1/11.
 */
public class Message {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

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

}
