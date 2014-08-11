/**
 * 
 */
package com.larry.biometrics.query;

import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ProxyFactory;
import org.jmock.MockObjectTestCase;

import com.larry.biometrics.util.FundMasterConfiguration;

/**
 * @author Otieno Lawrence
 * 
 */
public class PensionerBioQueryAdapterTest extends MockObjectTestCase {

	private FundMasterConfiguration configuration;

	@Override
	public void setUp() {
		configuration = new FundMasterConfiguration();
	}

	/** Just test if the Proxy is working */
	public void testClientWorking() throws Exception {
		CreatePensionerBioProxy createPensionerBioProxy = ProxyFactory.create(
				CreatePensionerBioProxy.class, configuration.getUrl());
		ClientResponse response = createPensionerBioProxy.getPensionerBio("","",
				"7883737383", "fingerprint_data");
		assertEquals(404, response.getStatus());
	}

}
