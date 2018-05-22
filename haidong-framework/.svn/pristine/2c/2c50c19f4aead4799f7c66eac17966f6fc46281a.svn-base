/**
 * 
 */
package com.huixin.framework.utils.smack;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

/**
 * @author Administrator
 *
 */
public class SmackUtils {
	private static Connection connection;
	private static ConnectionConfiguration config;
	/** openfire服务器address */
	private final static String server = "121.40.194.154";

	private static final void fail(Object o) {
		if (o != null) {
			System.out.println(o);
		}
	}
	
     static{
    	System.out.println("==========================init==========================");
        try {
            //connection = new XMPPConnection(server);
            //connection.connect();
            /** 5222是openfire服务器默认的通信端口，你可以登录http://192.168.8.32:9090/到管理员控制台查看客户端到服务器端口 */
            config = new ConnectionConfiguration(server, 5222);
            				  
            /** 是否启用压缩 */ 
            config.setCompressionEnabled(true);
            /** 是否启用安全验证 */
            config.setSASLAuthenticationEnabled(true);
            /** 是否启用调试 */
            config.setDebuggerEnabled(false);
            //config.setReconnectionAllowed(true);
            //config.setRosterLoadedAtLogin(true);
            
            /** 创建connection链接 */
            connection = new XMPPConnection(config);
            /** 建立连接 */
            connection.connect();
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }
	
	  private final void fail(Object o, Object... args) {
	        if (o != null && args != null && args.length > 0) {
	            String s = o.toString();
	            for (int i = 0; i < args.length; i++) {
	                String item = args[i] == null ? "" : args[i].toString();
	                if (s.contains("{" + i + "}")) {
	                    s = s.replace("{" + i + "}", item);
	                } else {
	                    s += " " + item;
	                }
	            }
	            System.out.println(s);
	        }
	    }
	
	/**
     * <b>function:</b> 创建用户
     * @author hoojo
     * @createDate 2012-6-25 下午04:22:31
     */
    public static void testAccountManager(String name) {
        AccountManager accountManager = connection.getAccountManager();
        try {
            accountManager.createAccount(name, "HaiDongPwd");
            System.out.println("end");
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
    	testAccountManager("aa");
	}
}
