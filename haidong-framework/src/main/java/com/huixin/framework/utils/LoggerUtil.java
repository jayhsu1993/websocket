package com.huixin.framework.utils;

import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;  
import java.util.Date;

import org.apache.log4j.FileAppender;  
import org.apache.log4j.Level;  
import org.apache.log4j.Logger;  
import org.apache.log4j.PatternLayout;  
import org.apache.log4j.RollingFileAppender; 

public class LoggerUtil {
	
	
	public static Logger getLog(Class<?> clazz, String liveId) {  
        
        Logger logger = Logger.getLogger(clazz);  // 生成新的Logger  
        logger.removeAllAppenders(); // 清空Appender，特別是不想使用現存實例時一定要初期化  
        logger.setLevel(Level.DEBUG); // 设定Logger級別。  
        logger.setAdditivity(false); // 设定是否继承父Logger。默认为true，继承root输出；设定false后将不输出root。  
          
        FileAppender appender = new RollingFileAppender(); // 生成新的Appender  
        PatternLayout layout = new PatternLayout();  
        layout.setConversionPattern("[%d{yyyy-MM-dd HH:mm:ss}] %p %l : %m%n"); // log的输出形式  
        appender.setLayout(layout);  
      
        appender.setFile(liveId + "_" + getTime("yyyy-MM-dd") + ".log");  // log输出路径  
        appender.setEncoding("UTF-8"); // log的字符编码  
        appender.setAppend(true);  //日志合并方式： true:在已存在log文件后面追加 false:新log覆盖以前的log  
        appender.activateOptions();  // 适用当前配置  
        appender.setImmediateFlush(true);  
        logger.addAppender(appender); // 将新的Appender加到Logger中  
        return logger;  
    }  
      
    private static String getTime(String format) {  
        SimpleDateFormat sdf = new SimpleDateFormat(format);  
        return sdf.format(new Date());  
    }
    
    public static void getLogByLiveId(Logger logger, String liveId) {  
        
		FileAppender appender = (FileAppender) logger.getRootLogger().getAppender("A");
		appender.setFile(System.getProperty("catalina.home")+ "/logs/live_log/" +liveId + "_" + getTime("yyyy-MM-dd") + ".log");
		appender.activateOptions();
	
    }  
    
    public static void getchatConnectLog(Logger logger) {  
        
		FileAppender appender = (FileAppender) logger.getRootLogger().getAppender("A");
		appender.setThreshold(org.apache.log4j.Priority.INFO);
		appender.setFile(System.getProperty("catalina.home")+ "/logs/chat_log/chatConnect_" + getTime("yyyy-MM-dd") + ".log");
		appender.activateOptions();
	
    }  
    
    
}
