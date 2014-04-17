/**
 * 
 */
package com.larry.biometrics.util;

/**
 * @author Tot Bby
 * 
 */
public interface BiometricsUtil {
	boolean verify(String pensionerNumber, byte[] verifyMin,
			byte[] registeredMin, long securityLevel, boolean[] matched);

	boolean register(String pensionerNumber, byte[] registeredMin1,
			byte[] registeredMin2, long securityLevel, boolean[] matched);
}
