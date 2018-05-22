package com.huixin.framework.log;

import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;

/**
 * Created by sankun
 * 14-10-31 下午2:55
 * Logger Util
 * 可以需要在此进行一些配置初使化
 * 以及对一些logger的设置处理
 * 如 websphere不支持进接的dailyRollerAppender 需要在getlog之前进行一些处理 再返回出去
 */
public class LogUtil {
    static boolean isInit = false;
    static final String LINUX_LOG_PATH = "/app/middapp/haidong_log/";

    public static void init() {
        //调整root的路径
        if (!isWindowsOS()) {
            Logger root = Logger.getRootLogger();
            Enumeration<Appender> e = root.getAllAppenders();
            if (e != null) {
                while (e.hasMoreElements()) {
                    Appender a = e.nextElement();
                    if (a instanceof FileAppender) {
                        FileAppender fa = (FileAppender) a;
                        String path = fa.getFile();
                        String name = getLogFileName(path);
                        String newPath = LINUX_LOG_PATH + name;
                        fa.setFile(newPath);
                        fa.activateOptions();
                        root.info("已将原日志路径:" + path + "转换为linux下路径:" + newPath);
                    }
                }
            }
        }
        isInit = true;
    }


    public static Logger getLog(Class clz) {
        if (!isInit) {
            init();
        }
        Logger log = Logger.getLogger(clz);
        return log;
    }

    private static String getLogFileName(String path) {
        if (path == null || "".equals(path)) {
            return "";
        }

        path = path.replaceAll("\\\\", "/");
        int lastIdx = path.lastIndexOf("/");
        if (lastIdx > 0) {
            return path.substring(lastIdx + 1);
        } else {
            return path;
        }

    }

    public static void main(String[] args) {
        System.out.println(getLogFileName("d:/aa/bb/cc.log"));

    }

    /*
* @return true---是Windows操作系统
*/
    public static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().indexOf("windows") > -1) {
            isWindowsOS = true;
        }
        return isWindowsOS;
    }
}
