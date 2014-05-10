/**
 * 
 */
package com.larry.biometrics.util;

import SecuGen.FDxSDKPro.jni.SGDeviceInfoParam;
import SecuGen.FDxSDKPro.jni.SGFingerInfo;

/**
 * @author Lawrence Otieno
 * 
 *         Biometrics functions
 */
public interface BiometricsUtil {

	public long initDevice(long deviceName);

	public long openDevice(long deviceName);

	public long getDeviceInfo(SGDeviceInfoParam deviceInfo);

	public int getMatchingScore(byte[] regMin1, byte[] regMin2);

	public long verify(byte[] verifyMin, byte[] registeredMin,
			long securityLevel);

	public long register(byte[] registeredMin1, byte[] registeredMin2,
			long securityLevel);

	public long getImageEx(byte[] buffer, long imgWidth, long imgHeight,
			long quality);

	public int getImageQuality(long imageWidth, long imageHeight,
			byte[] imageBuffer1);

	public long createTemplate(SGFingerInfo fingerInfo, byte[] imageBuffer,
			byte[] regMin);

	public long configure();

	public long setLedOn(boolean bLedOn);
	
	public long close();
}
