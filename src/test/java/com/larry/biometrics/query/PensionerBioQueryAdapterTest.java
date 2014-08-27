/**
 * 
 */
package com.larry.biometrics.query;

import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ProxyFactory;
import org.jmock.MockObjectTestCase;

/**
 * @author Otieno Lawrence
 * 
 */
public class PensionerBioQueryAdapterTest extends MockObjectTestCase {

	private String testUrl = "https://www.google.co.ke";

	@Override
	public void setUp() {
	}

	/** Just test if the Proxy is working */
	public void testClientWorking() throws Exception {
		CreatePensionerBioProxy createPensionerBioProxy = ProxyFactory.create(
				CreatePensionerBioProxy.class, testUrl);
		ClientResponse response = createPensionerBioProxy.getPensionerBio("",
				"", "7883737383", "fingerprint_data");
		assertEquals(404, response.getStatus());
	}

}
