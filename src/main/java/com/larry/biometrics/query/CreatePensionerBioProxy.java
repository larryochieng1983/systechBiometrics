/**
 * 
 */
package com.larry.biometrics.query;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.client.ClientResponse;

/**
 * @author Otieno Lawrence
 * 
 */
@Path("/api/pensionerBio")
public interface CreatePensionerBioProxy {

	@POST
	@Consumes("multipart/form-data")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public ClientResponse<PensionerServiceInputBean> createPensionerBio(
			@MultipartForm PensionerServiceInputBean inputBean);

	@GET
	@PathParam("pensionerId")
	@Consumes("text/plain")
	@Produces(value = { MediaType.MULTIPART_FORM_DATA, MediaType.TEXT_PLAIN })
	public ClientResponse<PensionerServiceInputBean> getPensionerBio(
			@PathParam("pensionerId") String id);
}
