/**
 * 
 */
package com.larry.biometrics.model;

import java.io.Serializable;

/**
 * @author Otieno Lawrence
 * 
 */
public class Pensioner implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String pensionerNumber;
	// the image of the fingerprint
	private byte[] fpImage;
	// the fingerprint minutiae
	private byte[] fpMinutiae;

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
		Pensioner other = (Pensioner) obj;
		if (pensionerNumber == null) {
			if (other.pensionerNumber != null)
				return false;
		} else if (!pensionerNumber.equals(other.pensionerNumber))
			return false;
		return true;
	}

}
