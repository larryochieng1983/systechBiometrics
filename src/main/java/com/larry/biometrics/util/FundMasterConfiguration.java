/**
 * 
 */
package com.larry.biometrics.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

	public FundMasterConfiguration() {
		baseDir = System.getProperty("user.home");
		loadConfiguration();
	}

	private void loadConfiguration() {
		if (getBaseDir() != null) {
			try {
				props.load(new FileInputStream(new File(getBaseDir() + "/"
						+ "fundmaster.properties")));
				this.baseDir = props.getProperty("baseDir");
				this.url = props.getProperty("url");
				this.userName = props.getProperty("userName");
				this.password = props.getProperty("password");
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	public void reloadConfiguration() {
		loadConfiguration();
	}

	public void saveConfiguration(String directory, String fundmasterUrl,
			String username, String pass) {
		File configFile = null;
		OutputStream outputStream = null;
		configFile = new File(directory + "/" + "fundmaster.properties");
		configFile.mkdir();
		props.put("baseDir", directory);
		props.put("url", fundmasterUrl);
		props.put("userName", username);
		props.put("password", pass);
		try {
			outputStream = new FileOutputStream(configFile);
			props.store(outputStream, "File Modified" + new Date());
		} catch (Exception e) {
			logger.error(e);
		} finally {
			try {
				outputStream.close();
			} catch (Exception e) {
				logger.error(e);
			}
		}
		reloadConfiguration();
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
