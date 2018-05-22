package com.huixin.framework.utils;

import org.apache.log4j.Logger;

import com.huixin.framework.log.LogUtil;

/**
 * User: X.Y.CHEN
 * Date: 14-12-9
 * Time: 上午9:56
 */
public class IntUtils {

    private static Logger logger = LogUtil.getLog(IntUtils.class);

    public static int getTime(String refTime) {
        int time = valueOf(refTime);
        return time / 60;
    }

    public static int valueOf(String num) {
        try {
            return Integer.valueOf(num);
        } catch (NumberFormatException e) {
            logger.error("Integer.valueOf(" + num + ") is error", e);
            return 0;
        }
    }
}
