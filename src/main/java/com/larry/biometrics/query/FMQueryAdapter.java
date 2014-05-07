/**
 * 
 */
package com.larry.biometrics.query;

import java.io.IOException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;

import com.google.common.io.Resources;
import com.larry.biometrics.model.Pensioner;

/**
 * @author Larry
 * 
 *         Query Adapter to FundMaster
 * 
 */
public class FMQueryAdapter {

	private String fundMasterUrl;
	private String userName;
	private String password;

	public FMQueryAdapter(String fundMasterUrl, String userName, String password) {
		this.fundMasterUrl = fundMasterUrl;
		this.userName = userName;
		this.password = password;

	}

	public boolean savePensionerInfo(Pensioner pensioner) throws Exception {
		ClientRequest add_request = request();

		MultipartFormDataOutput upload = new MultipartFormDataOutput();
		upload.addFormData("data", pensioner, MediaType.APPLICATION_XML_TYPE);
		upload.addFormData("file",
				Resources.toByteArray(Resources.getResource("thermo.wav")),
				MediaType.APPLICATION_OCTET_STREAM_TYPE);

		add_request.body(MediaType.MULTIPART_FORM_DATA_TYPE, upload);

		ClientResponse<?> recording_response = add_request.post();
		return true;
	}

	private ClientRequest request() {
		return null;
	}

	public Pensioner getPensionerInfo(String pensionerNumber) {
		return null;
	}

	public String getFundMasterUrl() {
		return fundMasterUrl;
	}

	public void setFundMasterUrl(String fundMasterUrl) {
		this.fundMasterUrl = fundMasterUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
