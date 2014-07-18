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

	private PensionerBioQueryAdapter queryAdapter;

	@Override
	public void setUp() {
		queryAdapter = new PensionerBioQueryAdapter("http://www.google.com",
				"", "");
	}

	/** Just test if the Proxy is working */
	public void testClientWorking() throws Exception {
		CreatePensionerBioProxy createPensionerBioProxy = ProxyFactory.create(
				CreatePensionerBioProxy.class, queryAdapter.getFundMasterUrl());
		ClientResponse response = createPensionerBioProxy
				.getPensionerBio("7883737383");
		assertEquals(404, response.getStatus());
	}

}
