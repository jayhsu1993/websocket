package com.huixin.framework.wx.beans;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.huixin.framework.wx.util.Base64Util;
import com.huixin.framework.wx.util.DESUtil;


/**
 *
 * @author Sean
 *
 */
public class DBEncrypt extends PropertyPlaceholderConfigurer {
	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		String userName = props.getProperty("db.userName");
		String password = props.getProperty("db.passWord");
		String DES_KEY = props.getProperty("DES_KEY");
		if (StringUtils.isNotBlank(userName)
				&& StringUtils.isNotBlank(password)) {
			try {
				props.setProperty(
						"db.userName",
						new String(DESUtil.decryptDES(
								Base64Util.decryptBASE64(userName), DES_KEY)));
				props.setProperty(
						"db.passWord",
						new String(DESUtil.decryptDES(
								Base64Util.decryptBASE64(password), DES_KEY)));
			} catch (Exception e) {

				e.printStackTrace();
				throw new BeanInitializationException(e.getMessage());

			}
		}

		super.processProperties(beanFactoryToProcess, props);
	}
}
