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
@Path("/api/biometrics/")
public interface CreatePensionerBioProxy {

	@POST
	@Path("uploadfp")
	@Consumes("multipart/form-data")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public ClientResponse<PensionerServiceInputBean> createPensionerBio(
			@MultipartForm PensionerServiceInputBean inputBean);

	@GET
	@Path("fingerprint/{member_id}/{type}")
	@Consumes("text/xml")
	@Produces(value = {  MediaType.APPLICATION_OCTET_STREAM, "image/bmp" })
	public ClientResponse<PensionerServiceInputBean> getPensionerBio(
			@PathParam("member_id") String member_id,
			@PathParam("type") String type);	
}
