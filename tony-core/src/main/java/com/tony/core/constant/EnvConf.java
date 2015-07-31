package com.tony.core.constant;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 应用环境配置
 * @author jahe.lai
 *
 */
public final class EnvConf {
	
	private static final Logger LOG = LoggerFactory.getLogger(EnvConf.class);
	private static ResourceBundle envBundle;
	private static String env;

	static {
		try {
			envBundle = new PropertyResourceBundle(new BufferedInputStream(
					EnvConf.class.getResourceAsStream("/env.properties")));
			env = envBundle.getString("SERVER.ENV");
		} catch (IOException e) {
			LOG.error("load env.properties file error.", e);
		}
	}
	
	public static String getConfig(String key) {
		return envBundle.getString(key);
	}
	
	/**
	 * 是开发环境还是生产环境
	 * @return true: 生产环境
	 */
	public static boolean isProd() {
		return "prod".equals(env) ? true : false;
	}
}
