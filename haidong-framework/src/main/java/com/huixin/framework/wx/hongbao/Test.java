/**
 * 
 */
package com.huixin.framework.wx.hongbao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Administrator
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		int j=1;
		while(j<2)
		{
		int number=5;
		float total=10;
		float money;
		double min=0.01;
		double max;
		int i=1;

		List math=new ArrayList();
		while(i<number)
		{

		max = total- min*(number- i);
		int k = (int)((number-i)/2);
		if (number -i <= 2)
		{k = number -i;}
		max = max/k;
		money=(int)(min*100+Math.random()*(max*100-min*100+1));
		money=(float)money/100;
		total=total-money;
		math.add(money);
		System.out.println("第"+i+"个人拿到"+money+"剩下"+total);
		i++;
		if(i==number)
		{
		math.add(total);
		System.out.println("第"+i+"个人拿到"+total+"剩下0");
		}
		}

		System.out.println("本轮发红包中第"+(math.indexOf(Collections.max(math))+1)+"个人手气最佳");
		j++;
		}
		
	}

}
