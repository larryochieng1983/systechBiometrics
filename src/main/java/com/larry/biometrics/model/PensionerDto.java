/**
 * 
 */
package com.larry.biometrics.model;

import java.io.Serializable;

/**
 * DTO for PensionerDto info
 * 
 * @author Otieno Lawrence
 * 
 */
public class PensionerDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pensionerNumber;
	// the image of the fingerprint
	private byte[] fpImage;
	// the fingerprint minutiae
	private byte[] fpMinutiae;
	private String photoUrl;
	private String memberName;

	public String getPensionerNumber() {
		return pensionerNumber;
	}

	public void setPensionerNumber(String pensionerNumber) {
		this.pensionerNumber = pensionerNumber;
	}

	public byte[] getFpImage() {
		return fpImage;
	}

	public void setFpImage(byte[] fpImage) {
		this.fpImage = fpImage;
	}

	public byte[] getFpMinutiae() {
		return fpMinutiae;
	}

	public void setFpMinutiae(byte[] fpMinutiae) {
		this.fpMinutiae = fpMinutiae;
	}

	/**
	 * @return the photoUrl
	 */
	public String getPhotoUrl() {
		return photoUrl;
	}

	/**
	 * @param photoUrl
	 *            the photoUrl to set
	 */
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName
	 *            the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((pensionerNumber == null) ? 0 : pensionerNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PensionerDto other = (PensionerDto) obj;
		if (pensionerNumber == null) {
			if (other.pensionerNumber != null)
				return false;
		} else if (!pensionerNumber.equals(other.pensionerNumber))
			return false;
		return true;
	}

}
