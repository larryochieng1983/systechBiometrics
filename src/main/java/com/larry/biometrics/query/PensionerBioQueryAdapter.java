/**
 * 
 */
package com.larry.biometrics.query;

import java.io.InputStream;

import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ProxyFactory;

import com.larry.biometrics.model.Pensioner;
import com.larry.biometrics.util.FundMasterConfiguration;

/**
 * @author Otieno Lawrence
 * 
 *         Query Adapter to FundMaster
 * 
 */
public class PensionerBioQueryAdapter {


	private FundMasterConfiguration configuration;
	/** For testing if the client works */
	private int status;

	public PensionerBioQueryAdapter(FundMasterConfiguration configuration) {
		this.configuration = configuration;

	}

	public boolean savePensionerInfo(Pensioner pensioner) throws Exception {
		byte[] fpImage = pensioner.getFpImage();
		byte[] fpMinutiae = pensioner.getFpMinutiae();
		CreatePensionerBioProxy createPensionerBioProxy = ProxyFactory.create(
				CreatePensionerBioProxy.class, configuration.getUrl());
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
				CreatePensionerBioProxy.class, configuration.getUrl());
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
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
