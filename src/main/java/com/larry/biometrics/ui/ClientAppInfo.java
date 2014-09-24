/*
 * Copyright 2005-2009 TraceTracker AS, Norway.
 */

package com.larry.biometrics.ui;


/**
 * Information about this application.
 * 
 * <br>
 * 
 * @author Otieno Lawrence
 * 
 */
public class ClientAppInfo {

	private String vendorName;
	private String vendorUrl;
	private String name;
	private String version;
	private String description;
	private String releaseYear;

	/**
	 * 
	 */
	public ClientAppInfo() {
		
	}

	/**
	 * 
	 * @param vendorName
	 * @param vendorUrl
	 * @param name
	 * @param version
	 * @param description
	 * @param releaseYear
	 */
	public ClientAppInfo(String vendorName, String vendorUrl, String name,
			String version, String description, String releaseYear) {
		this.vendorName = vendorName;
		this.vendorUrl = vendorUrl;
		this.name = name;
		this.version = version;
		this.description = description;
		this.releaseYear = releaseYear;
	}

	/**
	 * @return the vendorName
	 */
	public String getVendorName() {
		return vendorName;
	}

	/**
	 * @param vendorName the vendorName to set
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	/**
	 * @return the vendorUrl
	 */
	public String getVendorUrl() {
		return vendorUrl;
	}

	/**
	 * @param vendorUrl the vendorUrl to set
	 */
	public void setVendorUrl(String vendorUrl) {
		this.vendorUrl = vendorUrl;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the releaseYear
	 */
	public String getReleaseYear() {
		return releaseYear;
	}

	/**
	 * @param releaseYear the releaseYear to set
	 */
	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
	}

	
}
