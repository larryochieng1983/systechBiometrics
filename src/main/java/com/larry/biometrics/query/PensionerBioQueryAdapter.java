/**
 * 
 */
package com.larry.biometrics.query;

import java.io.InputStream;

import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ProxyFactory;

import com.larry.biometrics.model.Pensioner;

/**
 * @author Otieno Lawrence
 * 
 *         Query Adapter to FundMaster
 * 
 */
public class PensionerBioQueryAdapter {

	private String fundMasterUrl;
	private String userName;
	private String password;
	/** For testing if the client works */
	private int status;

	public PensionerBioQueryAdapter(String fundMasterUrl, String userName,
			String password) {
		this.fundMasterUrl = fundMasterUrl;
		this.userName = userName;
		this.password = password;

	}

	public boolean savePensionerInfo(Pensioner pensioner) throws Exception {
		byte[] fpImage = pensioner.getFpImage();
		byte[] fpMinutiae = pensioner.getFpMinutiae();
		CreatePensionerBioProxy createPensionerBioProxy = ProxyFactory.create(
				CreatePensionerBioProxy.class, getFundMasterUrl());
		PensionerServiceInputBean pensionerBioServiceInputBean = new PensionerServiceInputBean();
		pensionerBioServiceInputBean.setMemberId(pensioner.getPensionerNumber());
		pensionerBioServiceInputBean.setFpImage(fpImage);
		pensionerBioServiceInputBean.setFpMinutiae(fpMinutiae);
		ClientResponse response = createPensionerBioProxy
				.createPensionerBio(pensionerBioServiceInputBean);		
		setStatus(response.getStatus());
		return getStatus() == 200;

	}

	public Pensioner getPensionerInfo(String pensionerNumber) {
		Pensioner pensioner = null;
		CreatePensionerBioProxy createPensionerBioProxy = ProxyFactory.create(
				CreatePensionerBioProxy.class, getFundMasterUrl());
		ClientResponse response = createPensionerBioProxy
				.getPensionerBio(pensionerNumber,"fingerprint_data");
		if (response.getStatus() == 200) {
			setStatus(200);
			pensioner = new Pensioner();
			pensioner.setPensionerNumber(pensionerNumber);
			pensioner.setFpMinutiae(extractByte(response));
		}
		return pensioner;
	}

	private byte[] extractByte(ClientResponse response) {
		byte[] payload = new byte[400];
			try {
				InputStream inputStream = (InputStream)response.getEntity(InputStream.class);
				inputStream.read(payload);
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		return payload;
	}

	/**
	 * @return the fundMasterUrl
	 */
	public String getFundMasterUrl() {
		return fundMasterUrl;
	}

	/**
	 * @param fundMasterUrl
	 *            the fundMasterUrl to set
	 */
	public void setFundMasterUrl(String fundMasterUrl) {
		this.fundMasterUrl = fundMasterUrl;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
