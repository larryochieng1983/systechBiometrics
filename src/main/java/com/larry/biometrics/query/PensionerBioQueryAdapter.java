/**
 * 
 */
package com.larry.biometrics.query;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

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
	/**For testing if the client works */
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
		pensionerBioServiceInputBean.setFpImage(fpImage);
		pensionerBioServiceInputBean.setFpMinutiae(fpMinutiae);
		ClientResponse response = createPensionerBioProxy
				.createPensionerBio(pensionerBioServiceInputBean);
		return response.getStatus() == 200;

	}

	public Pensioner getPensionerInfo(String pensionerNumber) {
		Pensioner pensioner = null;
		CreatePensionerBioProxy createPensionerBioProxy = ProxyFactory.create(
				CreatePensionerBioProxy.class, getFundMasterUrl());
		ClientResponse response = createPensionerBioProxy
				.getPensionerBio(pensionerNumber);
		MultipartFormDataInput input = (MultipartFormDataInput) response
				.getEntity(MultipartFormDataInput.class);
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		if (response.getStatus() == 200) {
			pensioner = new Pensioner();
			pensioner.setPensionerNumber(pensionerNumber);
			pensioner.setFpImage(extractByte(uploadForm, "fpImage"));
			pensioner.setFpMinutiae(extractByte(uploadForm, "fpMinutiae"));
		}
		return pensioner;
	}

	private byte[] extractByte(Map<String, List<InputPart>> uploadForm,
			String attributeName) {
		byte[] payload = new byte[400];
		List<InputPart> inputParts = uploadForm.get(attributeName);
		for (InputPart inputPart : inputParts) {
			try {
				InputStream inputStream = inputPart.getBody(InputStream.class,
						null);
				inputStream.read(payload);
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
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

}
