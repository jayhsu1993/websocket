package com.huixin.framework.log;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;


/**
 *本类是用于常量定义
 */
public class ComConstants {

    /**
     * log 配置名
     */
    public static final String LOG_CONF_NAME = "caimai.log";

    /**
     * 在properties文件中找不到相应消息时，输出的默认消息
     */
    public static final String MSG_DEFAULT = "EventMessage is not found";

    /**
     * 系统运行日志消息（方法开始时调用）对应的properties文件中KEY值
     */
    public static final String MSG_TRACE_START = "msg.trace.start";

    /**
     * 系统运行日志消息（方法结束时调用）对应的properties文件中KEY值
     */
    public static final String MSG_TRACE_END = "msg.trace.end";

    /**
     * LOG信息中，类名/方法名格式符号（开始）
     */
    public static final String MSG_FORMAT_START = "[";

    /**
     * LOG信息中，类名/方法名格式符号（结束）
     */
    public static final String MSG_FORMAT_END = "]";

    /**
     * LOG信息中，类名，方法名，日志消息之间的分隔符
     */
    public static final String MSG_FORMAT_SPLIT = " ";

    /***************************************************************************
     * 常用常量-时间格式化
     **************************************************************************/
    public static final SimpleDateFormat SDF_DAY = new SimpleDateFormat("yyyy/MM/dd");
    public static final SimpleDateFormat SDF_TIME = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public static final SimpleDateFormat SDF_YYYY = new SimpleDateFormat("yyyy");
    public static final SimpleDateFormat SDF_Month = new SimpleDateFormat("MM");
    public static final SimpleDateFormat SDF_DD = new SimpleDateFormat("dd");
    public static final SimpleDateFormat SDF_HOUR24 = new SimpleDateFormat("HH");
    public static final SimpleDateFormat SDF_MIN = new SimpleDateFormat("mm");
    public static final SimpleDateFormat SDF_SEC = new SimpleDateFormat("ss");
    public static final SimpleDateFormat SDF_YYYYMM = new SimpleDateFormat("yyyyMM");
    public static final SimpleDateFormat SDF_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat SDF_YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat SDF_YYYYMMDD_CHN = new SimpleDateFormat( "yyyy年MM月dd日");
    public static final SimpleDateFormat SDF_YYYYMMDD_CROSS = new SimpleDateFormat( "yyyy-MM-dd");
    public static final SimpleDateFormat SDF_MMDD_CROSS = new SimpleDateFormat("MM-dd");

    /***************************************************************************
     * 常用常量-数字
     **************************************************************************/
    public static final BigDecimal NEGATIVE = new BigDecimal("-1");
    public static final BigDecimal ZERO = new BigDecimal("0");
    public static final BigDecimal ONE = new BigDecimal("1");
    public static final BigDecimal TWO = new BigDecimal("2");
    public static final BigDecimal TEN = new BigDecimal("10");

    public static final String FLAG_YES = "YES";
    public static final String FLAG_NO = "NO";

    public static final String FLAG_ALL = "ALL";

    /***************************************************************************
     * 星星类用class
     **************************************************************************/
    public static final String STAR_ONE = "one";
    public static final String STAR_TWO = "two";
    public static final String STAR_THREE = "three";
    public static final String STAR_FOUR = "four";
    public static final String STAR_FIVE = "five";
    
    /**
     * 转发标志
     */
    public static final String SUCCESS_FLG = "success";
    public static final String FILTER_FLG = "filter";
    
    /**
     * AJAX判断的标志
     */
    public static final String AJAX_SUCCESS = "success"; // 成功
    public static final String AJAX_ERROR = "error"; // 异常
    public static final String AJAX_UNIQUE = "unique"; // 因不能重复插入导致失败
    
    /**
     * 用户登录标识
     */
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";
    public static final String CITY = "city";
    public static final String User = "UserDto";
    
    /**
     * sessionid
     */
    public static final String SESSION_ID = "sessionId";

    /**
     * 保持登录的cookie的id
     */
    public static final String KEEP_LOGIN = "KEEP_LOGIN";

    /***
     * 消息提示
     */
    public static final String SAVE_SUCCESS = "保存成功"; // 成功
    public static final String SAVE_FAILED = "保存失败"; // 异常
    public static final String SAVE_HEAD = "不能互相关联";
    public static final String DELETE_SUCCESS = "删除成功"; // 异常
    public static final String DELETE_FAILED = "删除失败"; // 异常

}
