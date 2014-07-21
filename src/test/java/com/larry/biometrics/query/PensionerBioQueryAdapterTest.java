/**
 * 
 */
package com.larry.biometrics.query;

import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ProxyFactory;
import org.jmock.MockObjectTestCase;

import com.larry.biometrics.model.PensionerDto;
import com.larry.biometrics.util.FundMasterConfiguration;

/**
 * @author Otieno Lawrence
 * 
 */
public class PensionerBioQueryAdapterTest extends MockObjectTestCase {

	private PensionerBioQueryAdapter queryAdapter;
	private FundMasterConfiguration configuration;

	@Override
	public void setUp() {
		configuration = new FundMasterConfiguration();
		queryAdapter = new PensionerBioQueryAdapter(configuration);
	}

	/** Just test if the Proxy is working */
	public void testClientWorking() throws Exception {
		CreatePensionerBioProxy createPensionerBioProxy = ProxyFactory.create(
				CreatePensionerBioProxy.class, configuration.getUrl());
		ClientResponse response = createPensionerBioProxy.getPensionerBio(
				"7883737383", "fingerprint_data");
		assertEquals(404, response.getStatus());
	}

	public void testGetPensionerInfo() throws Exception {
		queryAdapter.getPensionerBiometricInfo("282580");
		assertEquals(200, queryAdapter.getStatus());
	}

	public void testSavePensionerInfo() throws Exception {
		PensionerDto pensionerDto = new PensionerDto();
		pensionerDto.setPensionerNumber("282580");
		pensionerDto.setFpImage(new byte[400]);
		pensionerDto.setFpMinutiae(new byte[400]);
		queryAdapter.savePensionerInfo(pensionerDto);
		assertEquals(200, queryAdapter.getStatus());
	}

}
