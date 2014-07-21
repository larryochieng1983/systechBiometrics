/**
 * 
 */
package com.larry.biometrics.query;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientResponse;

/**
 * @author Otieno Lawrence
 * 
 */
@Path(" /api/membersearch")
public interface PensionerSearchProxy {

	@GET
	@Consumes("text/xml")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public ClientResponse<PensionerServiceInputBean> searchMember(
			@HeaderParam("userName") String userName,
			@HeaderParam("password") String password,
			@PathParam("q") String member_id);
}
