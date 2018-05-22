package com.huixin.framework.redis;

import org.apache.log4j.Logger;

import com.huixin.framework.log.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sankun
 * 14-11-24 上午10:05
 */
public class PortalProps {
    private static Logger logger = LogUtil.getLog(PortalProps.class);
    private PortalProps(){}
    private static Properties prop = null;
    private static void init(){
        if(prop ==null){
            InputStream in = null;
            try{
                prop = new Properties();
                in = PortalProps.class.getResourceAsStream("/redis.properties");
                if(in == null){
                    in = ClassLoader.getSystemResourceAsStream("redis.properties");
                }
                prop.load(in);
            }catch (Exception e){
                logger.error("初使化redis.properties流失败",e);
            }finally {
                if(in != null){
                    try{
                        in.close();
                    }catch (IOException e2){
                        logger.error("关闭redis.properties流失败",e2);
                    }
                }
            }
        }
    }

    public static String getProp(String key){
        if(prop == null){
            init();
        }

        return prop.getProperty(key);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getProp("redis.server.url"));
    }
}
