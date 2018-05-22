/**
 * 
 */
package com.huixin.framework.order;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class OrderUtils {
	
    /** 
     * 生成订单编号 
     * @return 
     */  
    public static synchronized String getOrderNo(String ip) {  
//        String str = new SimpleDateFormat("yyMMddHHmmss").format(new Date());  
        return String.valueOf(System.currentTimeMillis()) + getRandomCode(2, 9);  
    }  
    
    private static String getRandomCode(int beg, int end) {
		String[] beforeShuffle = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		List list = Arrays.asList(beforeShuffle);
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		String afterShuffle = sb.toString();
		return afterShuffle.substring(beg, end);
	}
}
