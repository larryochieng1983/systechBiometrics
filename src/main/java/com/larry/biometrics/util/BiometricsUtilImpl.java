/**
 * 
 */
package com.larry.biometrics.util;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import SecuGen.FDxSDKPro.jni.JSGFPLib;
import SecuGen.FDxSDKPro.jni.SGDeviceInfoParam;
import SecuGen.FDxSDKPro.jni.SGFDxErrorCode;
import SecuGen.FDxSDKPro.jni.SGFingerInfo;

import com.larry.biometrics.model.PensionerDto;
import com.larry.biometrics.query.PensionerBioQueryAdapter;

/**
 * @author Otieno Lawrence
 * 
 */
public class BiometricsUtilImpl implements BiometricsUtil {

	public PensionerDto currentPensioner;
	private PensionerBioQueryAdapter adapter;
	private FundMasterConfiguration configuration;

	private JSGFPLib fplib = null;

	// static {
	// logger.info("Loading Secugen DLL");
	// try {
	// loadLib();
	// } catch (Exception e) {
	// JOptionPane.showMessageDialog(new JFrame(), e.getMessage(),
	// "System Error", JOptionPane.ERROR_MESSAGE);
	// System.exit(0);
	// }
	// }

	public BiometricsUtilImpl() {
		fplib = new JSGFPLib();
		configuration = new FundMasterConfiguration();
		adapter = new PensionerBioQueryAdapter(configuration);
	}

	public long initDevice(long deviceName) {
		assert (fplib != null);
		return fplib.Init(deviceName);
	}

	public long openDevice(long deviceName) {
		return fplib.OpenDevice(deviceName);
	}

	public long getDeviceInfo(SGDeviceInfoParam deviceInfo) {
		return fplib.GetDeviceInfo(deviceInfo);
	}

	public long verify(byte[] verifyMin, long securityLevel) throws Exception {
		PensionerDto pensionerDto = null;
		if (currentPensioner == null) {
			throw new Exception(
					"Member Search not Successful, please search first!");
		}
		try {
			pensionerDto = adapter.getPensionerBiometricInfo(currentPensioner
					.getPensionerNumber());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		byte[] registeredMin = pensionerDto.getFpMinutiae();
		if (registeredMin == null) {
			throw new Exception(
					"Could not fetch registered biometric data for member!");
		}
		boolean[] matched = new boolean[1];
		matched[0] = false;
		long iError = fplib.MatchTemplate(registeredMin, verifyMin,
				securityLevel, matched);
		if (!matched[0]) {
			iError = SGFDxErrorCode.SGFDX_ERROR_MATCH_FAIL;
		}
		return iError;
	}

	public long register(byte[] registeredMin1, byte[] registeredMin2,
			byte[] fpImage, long securityLevel) throws Exception {
		if (currentPensioner == null) {
			throw new Exception(
					"Member Search not Successful, please search first!");
		}
		currentPensioner.setFpMinutiae(registeredMin2);
		boolean[] matched = new boolean[1];
		long err = fplib.MatchTemplate(registeredMin1, registeredMin2,
				securityLevel, matched);
		if (err == SGFDxErrorCode.SGFDX_ERROR_NONE) {
			if (matched[0]) {
				currentPensioner.setFpMinutiae(registeredMin2);
				currentPensioner.setFpImage(fpImage);
				try {
					adapter.savePensionerInfo(currentPensioner);
				} catch (Exception e) {
					throw new Exception(e.getMessage());
				}
			} else {
				err = SGFDxErrorCode.SGFDX_ERROR_MATCH_FAIL;
			}
		}
		return err;

	}

	public int getMatchingScore(byte[] regMin1, byte[] regMin2) {
		int matchingScore = 0;
		int[] matchScore = new int[1];
		long iError = fplib.GetMatchingScore(regMin1, regMin2, matchScore);
		if (iError == SGFDxErrorCode.SGFDX_ERROR_NONE) {
			matchingScore = matchScore[0];
		}
		return matchingScore;
	}

	public long configure() {
		return fplib.Configure(0);
	}

	public long setLedOn(boolean bLedOn) {

		return fplib.SetLedOn(bLedOn);
	}

	public long getImageEx(byte[] buffer, long imgWidth, long imgHeight,
			long quality) {
		return fplib.GetImageEx(buffer, imgWidth, imgHeight, quality);
	}

	public int getImageQuality(long imageWidth, long imageHeight,
			byte[] imageBuffer1) {
		int imageQuality = 0;
		int[] quality = new int[1];
		long iError = fplib.GetImageQuality(imageWidth, imageHeight,
				imageBuffer1, quality);
		if (iError == SGFDxErrorCode.SGFDX_ERROR_NONE) {
			imageQuality = quality[0];
		}
		return imageQuality;
	}

	public long createTemplate(SGFingerInfo fingerInfo, byte[] imageBuffer,
			byte[] regMin) {
		return fplib.CreateTemplate(fingerInfo, imageBuffer, regMin);
	}

	public long close() {
		return fplib.Close();
	}

	/**
	 * @return the currentPensioner
	 */
	public PensionerDto getCurrentPensioner() {
		return currentPensioner;
	}

	/**
	 * @param currentPensioner
	 *            the currentPensioner to set
	 */
	public void setCurrentPensioner(PensionerDto currentPensioner) {
		this.currentPensioner = currentPensioner;
	}

	/**
	 * Puts library to temp dir and loads to memory
	 */
	private static void loadLib() throws Exception {
		String separator = System.getProperty("file.separator");
		String userName = System.getProperty("user.name");
		InputStream src = BiometricsUtilImpl.class
				.getResourceAsStream("/jnlp/secugen/win32/jnisgfplib.dll");
		Path target = null;
		try {
			target = Paths.get("C:/Windows/System32/");
			String osArch = System.getProperty("os.arch");
			if (osArch.equalsIgnoreCase("amd64")) {
				src = BiometricsUtilImpl.class
						.getResourceAsStream("/jnlp/secugen/x64/jnisgfplib.dll");
				target = Paths.get("C:/Windows/SysWOW64/");
			}
			AclFileAttributeView view = Files.getFileAttributeView(target,
					AclFileAttributeView.class);
			List<AclEntry> aclEntryList = view.getAcl();
			UserPrincipalLookupService lookupService = FileSystems.getDefault()
					.getUserPrincipalLookupService();
			UserPrincipal userPrincipal = lookupService
					.lookupPrincipalByName(userName);
			AclEntry.Builder builder = AclEntry.newBuilder();
			builder.setType(AclEntryType.ALLOW);
			builder.setPrincipal(userPrincipal);
			builder.setPermissions(AclEntryPermission.WRITE_ACL,
					AclEntryPermission.DELETE);
			AclEntry entry = builder.build();
			aclEntryList.add(0, entry);
			if (target.toFile().canWrite()) {
				view.setAcl(aclEntryList);
			}
			target = Paths.get(target + separator + "jnisgfplib.dll");
			Files.copy(src, target, REPLACE_EXISTING);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
