/**
 * 
 */
package com.larry.biometrics.util;

import com.larry.biometrics.model.Pensioner;

/**
 * @author Otieno Lawrence
 * 
 */
public interface PensionerDetailDTO {

	Pensioner getPensioner(String pensionerNumber);

	boolean savePensioner(Pensioner pensioner);

	boolean updatePensioner(Pensioner pensioner);
}
