/**
 * 
 */
package com.larry.biometrics.query;

import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

/**
 * @author Otieno Lawrence
 * 
 */
public class PensionerServiceInputBean {

	@FormParam("member_id")
	@PartType("text/plain")
	private String memberId;

	@FormParam("fpImage")
	@PartType("image/bmp")
	private byte[] fpImage;

	@FormParam("fpMinutiae")
	@PartType("application/octet-stream")
	private byte[] fpMinutiae;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the fpImage
	 */
	public byte[] getFpImage() {
		return fpImage;
	}

	/**
	 * @param fpImage
	 *            the fpImage to set
	 */
	public void setFpImage(byte[] fpImage) {
		this.fpImage = fpImage;
	}

	/**
	 * @return the fpMinutiae
	 */
	public byte[] getFpMinutiae() {
		return fpMinutiae;
	}

	/**
	 * @param fpMinutiae
	 *            the fpMinutiae to set
	 */
	public void setFpMinutiae(byte[] fpMinutiae) {
		this.fpMinutiae = fpMinutiae;
	}

}
