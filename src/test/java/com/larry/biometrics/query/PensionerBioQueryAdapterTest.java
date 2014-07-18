/**
 * 
 */
package com.larry.biometrics.query;

import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ProxyFactory;
import org.jmock.MockObjectTestCase;

import com.larry.biometrics.model.Pensioner;

/**
 * @author Otieno Lawrence
 * 
 */
public class PensionerBioQueryAdapterTest extends MockObjectTestCase {

	private PensionerBioQueryAdapter queryAdapter;

	@Override
	public void setUp() {
		queryAdapter = new PensionerBioQueryAdapter("http://localhost:8888/Xi",
				"", "");
	}

	/** Just test if the Proxy is working */
	public void testClientWorking() throws Exception {
		CreatePensionerBioProxy createPensionerBioProxy = ProxyFactory.create(
				CreatePensionerBioProxy.class, queryAdapter.getFundMasterUrl());
		ClientResponse response = createPensionerBioProxy.getPensionerBio(
				"7883737383", "fingerprint_data");
		assertEquals(404, response.getStatus());
	}

	public void testGetPensionerInfo() throws Exception {
		queryAdapter.getPensionerInfo("282580");
		assertEquals(200, queryAdapter.getStatus());
	}

	public void testSavePensionerInfo() throws Exception {
		Pensioner pensioner = new Pensioner();
		pensioner.setPensionerNumber("282580");
		pensioner.setFpImage(new byte [400]);
		pensioner.setFpMinutiae(new byte [400]);
		queryAdapter.savePensionerInfo(pensioner);
		assertEquals(200, queryAdapter.getStatus());
	}

}
