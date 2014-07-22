/**
 * 
 */
package com.larry.biometrics.query;

import java.io.InputStream;

import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ProxyFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.larry.biometrics.model.PensionerDto;
import com.larry.biometrics.util.FundMasterConfiguration;

/**
 * @author Otieno Lawrence
 * 
 *         Query Adapter to FundMaster
 * 
 */
public class PensionerBioQueryAdapter {

	private static Logger LOG = Logger
			.getLogger(PensionerBioQueryAdapter.class);

	private FundMasterConfiguration configuration;
	/** For testing if the client works */
	private int status;

	public PensionerBioQueryAdapter(FundMasterConfiguration configuration) {
		this.configuration = configuration;

	}

	public boolean savePensionerInfo(PensionerDto pensionerDto)
			throws Exception {
		byte[] fpImage = pensionerDto.getFpImage();
		byte[] fpMinutiae = pensionerDto.getFpMinutiae();
		CreatePensionerBioProxy createPensionerBioProxy = ProxyFactory.create(
				CreatePensionerBioProxy.class, configuration.getUrl());
		PensionerServiceInputBean pensionerBioServiceInputBean = new PensionerServiceInputBean();
		pensionerBioServiceInputBean.setMemberId(pensionerDto
				.getPensionerNumber());
		pensionerBioServiceInputBean.setFpImage(fpImage);
		pensionerBioServiceInputBean.setFpMinutiae(fpMinutiae);
		ClientResponse response = createPensionerBioProxy.createPensionerBio(
				configuration.getUserName(), configuration.getPassword(),
				pensionerBioServiceInputBean);
		setStatus(response.getStatus());
		return getStatus() == 200;

	}

	public PensionerDto getPensionerBiometricInfo(String pensionerNumber) {
		PensionerDto pensionerDto = null;
		CreatePensionerBioProxy createPensionerBioProxy = ProxyFactory.create(
				CreatePensionerBioProxy.class, configuration.getUrl());
		ClientResponse response = createPensionerBioProxy.getPensionerBio(
				configuration.getUserName(), configuration.getPassword(),
				pensionerNumber, "fingerprint_data");
		if (response.getStatus() == 200) {
			setStatus(200);
			pensionerDto = new PensionerDto();
			pensionerDto.setPensionerNumber(pensionerNumber);
			pensionerDto.setFpMinutiae(extractByte(response));
		}
		return pensionerDto;
	}

	public PensionerDto searchPensioner(String memberId) {
		PensionerDto pensionerDto = null;
		PensionerSearchProxy pensionerSearchProxy = ProxyFactory.create(
				PensionerSearchProxy.class, configuration.getUrl());
		ClientResponse response = pensionerSearchProxy.searchMember(
				configuration.getUserName(), configuration.getPassword(),
				memberId);
		String result = (String) response.getEntity(String.class);
		System.out.println(result);
		JSONObject jsonResult = null;
		try {
			jsonResult = (JSONObject) new JSONParser().parse(result);
		} catch (ParseException e) {
			LOG.error(e);
		}
		// Fetch the other info if the member exists
		if (jsonResult != null || !jsonResult.isEmpty()) {
			pensionerDto = getPensionerBiometricInfo(memberId);
			pensionerDto.setMemberName(jsonResult.get("member.title")
					.toString()
					+ " "
					+ jsonResult.get("member.surname").toString()
					+ " "
					+ jsonResult.get("member.firstname").toString()
					+ " "
					+ jsonResult.get("member.othernames").toString());
			pensionerDto.setPhotoUrl(configuration.getUrl()+jsonResult.get("member.image_url").toString());

		}
		return pensionerDto;
	}

	private byte[] extractByte(ClientResponse response) {
		byte[] payload = new byte[400];
		try {
			InputStream inputStream = (InputStream) response
					.getEntity(InputStream.class);
			inputStream.read(payload);
			inputStream.close();
		} catch (Exception e) {
			LOG.error(e);
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
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

}
