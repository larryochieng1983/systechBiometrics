/**
 * 
 */
package com.larry.biometrics.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Otieno Lawrence
 * 
 */
public class FundMasterConfiguration {
	static final Logger logger = LogManager
			.getLogger(FundMasterConfiguration.class.getName());

	private String url;
	private String userName;
	private String password;

	private String baseDir;
	private Properties props = new Properties();
	private File configFile;

	public FundMasterConfiguration() {
		String separator = System.getProperty("file.separator");
		if (this.baseDir == null || this.baseDir.equals("")) {
			this.baseDir = System.getProperty("user.home");
		}
		try {
			configFile = new File(baseDir + separator + "fundmaster.properties");
			if (!configFile.exists()) {
				configFile.createNewFile();
				props.put("url", "");
				props.put("userName", "");
				props.put("password", "");
			}

			OutputStream outputStream = new FileOutputStream(configFile);
			props.store(outputStream, "File Modified" + new Date());
		} catch (Exception e) {
			logger.error(e);
		}

	}

	public void saveConfiguration(String baseDir, String url, String userName,
			String password) {
		this.baseDir = baseDir;
		this.url = url;
		this.userName = userName;
		this.password = password;

		if (!configFile.exists()) {
			configFile.mkdir();
			props.put("baseDir", this.baseDir);
			props.put("url", this.url);
			props.put("userName", this.userName);
			props.put("password", this.password);
		}
		try {
			OutputStream outputStream = new FileOutputStream(configFile);
			props.store(outputStream, "File Modified" + new Date());
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the baseDir
	 */
	public String getBaseDir() {
		return baseDir;
	}

}
