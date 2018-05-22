/**
 * ComMessage.java
 *
 * 功  能：业务异常处理类
 * 类  名：ComMessage
 */
package com.huixin.framework.log;

import org.apache.log4j.Logger;

import java.text.MessageFormat;


/**
 * 本类用于properties文件读取。<BR>
 * 
 */
public class ComMessage {

    private static Logger logger = LogUtil.getLog(ComMessage.class);

    /**
     * <p>
     * ComMessage的构造方法。
     * </p>
     * 
     * @since [pro_000]searchUI PKG Ver 1.0
     */
    public ComMessage() {

       /* try {
            ResourceBundle resourceBundle = ResourceBundle
                    .getBundle(ComConstants.RES_PATH);
            strLogLocale = resourceBundle.getString(ComConstants.Log_LOCALE);
        } catch (Exception e) {
            logger.logError(e, "ComMessage");
        }*/

    }

    /**
     * <p>
     * 从properties文件中取得参数的消息。
     * </p>
     * 如果取不到消息的话返回消息key值。
     * 
     * @param strMsgId 消息key值
     * @return properties文件中对应的消息或消息key值
     * @since [pro_000]searchUI PKG Ver 1.0
     */
    /*
     * public String getParamMessage(String strMsgId) { String strErrorMsg =
     * ComConstants.MSG_DEFAULT; try { ResourceBundle resourceBundle =
     * ResourceBundle .getBundle(ComConstants.PROPERTIES_FILE_NAME); strErrorMsg =
     * resourceBundle.getString(strMsgId); } catch (Exception e) { strErrorMsg =
     * strMsgId; } return strErrorMsg; }
     */

    /**
     * <p>
     * 从properties文件中取得消息。
     * </p>
     * 
     * @param strMsgId 消息key值
     * @param strMsgParams 消息参数key值
     * @return properties文件中对应的消息
     * @since [pro_000]searchUI PKG Ver 1.0
     */
    /*
     * public String getMessage(String strMsgId, String[] strMsgParams) { String
     * strErrorMsg = this.getMessage(strMsgId); String strParam = ""; String
     * sParam = ""; try { if (strMsgParams != null) { for (int i = 0; i <
     * strMsgParams.length; i++) { strParam =
     * encodeForJAVA(getParamMessage(strMsgParams[i])); sParam = "\\{" +
     * String.valueOf(i+1) + "\\}"; strErrorMsg =
     * strErrorMsg.replaceAll(sParam.toString(), strParam); } } } catch
     * (Exception e) { logger.logError(e,"getMessage"); } return strErrorMsg; }
     */

    /**
     * <p>
     * 从properties文件中取得消息。(Log相关)
     * </p>
     * 
     * @param strMsgId 消息key值
     * @return properties文件中对应的消息(Log相关)
     * @since [pro_000]searchUI PKG Ver 1.0
     */
    public String getLogMessage(String strMsgId) {

        String strErrorMsg = ComConstants.MSG_DEFAULT;

        try {
//            ResourceBundle resourceBundle = ResourceBundle
//                    .getBundle(ComConstants.LOG_RES_PATH);
//            ;

            /*
             * if (ComConstants.LOCALE_JAPANESE.equals(strLogLocale)) {
             * resourceBundle = ResourceBundle
             * .getBundle(ComConstants.LOG_RES_PATH,new Locale("ja","JP"));
             * }else if (ComConstants.LOCALE_CHINESE.equals(strLogLocale)) {
             * resourceBundle = ResourceBundle
             * .getBundle(ComConstants.LOG_RES_PATH,new Locale("zh","CN"));
             * }else { resourceBundle = ResourceBundle
             * .getBundle(ComConstants.LOG_RES_PATH,new Locale("en","US")); }
             */
//            strErrorMsg = resourceBundle.getString(strMsgId);
        } catch (Exception e) {
            logger.error( "getLogMessage",e);
        }
        return strErrorMsg;
    }

    /**
     * <p>
     * 从properties文件中取得消息。(Log相关)
     * </p>
     * 
     * @param strMsgId 消息key值
     * @param strMsgParams 消息参数key值
     * @return properties文件中对应的消息(Log相关)
     * @since [pro_000]searchUI PKG Ver 1.0
     */
    public String getLogMessage(String strMsgId, String[] strMsgParams) {
        Object obj[] = strMsgParams;
        String strErrorMsg = this.getLogMessage(strMsgId);
        try {
            strErrorMsg = MessageFormat.format(strErrorMsg, obj);

        } catch (Exception e) {
            logger.error("getLogMessage",e);
        }
        return strErrorMsg;
    }

    /*
     * public String getLogParamMessage(String strMsgId) { String strErrorMsg =
     * ComConstants.MSG_DEFAULT; try { ResourceBundle resourceBundle; if
     * (ComConstants.LOCALE_JAPANESE.equals(strLogLocale)) { resourceBundle =
     * ResourceBundle.getBundle( ComConstants.LOG_RES_PATH, new Locale("ja",
     * "JP")); } else if (ComConstants.LOCALE_CHINESE.equals(strLogLocale)) {
     * resourceBundle = ResourceBundle.getBundle( ComConstants.LOG_RES_PATH, new
     * Locale("zh", "CN")); } else { resourceBundle = ResourceBundle.getBundle(
     * ComConstants.LOG_RES_PATH, new Locale("en", "US")); } strErrorMsg =
     * resourceBundle.getString(strMsgId); } catch (Exception e) { strErrorMsg =
     * strMsgId; } return strErrorMsg; }
     */

}
