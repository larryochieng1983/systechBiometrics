/*
 * JSGD.java
 *
 * Created on May 11, 2005, 10:51 AM
 */

package com.larry.biometrics.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import SecuGen.FDxSDKPro.jni.SGDeviceInfoParam;
import SecuGen.FDxSDKPro.jni.SGFDxDeviceName;
import SecuGen.FDxSDKPro.jni.SGFDxErrorCode;
import SecuGen.FDxSDKPro.jni.SGFingerInfo;
import SecuGen.FDxSDKPro.jni.SGFingerPosition;
import SecuGen.FDxSDKPro.jni.SGImpressionType;
import SecuGen.FDxSDKPro.jni.SGPPPortAddr;

import com.larry.biometrics.model.PensionerDto;
import com.larry.biometrics.query.PensionerBioQueryAdapter;
import com.larry.biometrics.util.ApplicationInfo;
import com.larry.biometrics.util.ApplicationInfoImpl;
import com.larry.biometrics.util.BiometricsUtil;
import com.larry.biometrics.util.BiometricsUtilImpl;
import com.larry.biometrics.util.FundMasterConfiguration;

/**
 * This code was edited or generated using CloudGarden's Jigloo
 * SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a corporation,
 * company or business for any purpose whatever) then you
 * should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details.
 * Use of Jigloo implies acceptance of these licensing terms.
 * A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
 * THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
 * LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
/**
 * 
 * @author Otieno lawrence
 */
public class MainWindow extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(MainWindow.class);
	// Private instance variables
	private long deviceName;
	private long devicePort;
	private long ret;
	private boolean bLEDOn;
	private byte[] regMin1 = new byte[400];
	private byte[] regMin2 = new byte[400];
	private byte[] vrfMin = new byte[400];
	private SGDeviceInfoParam deviceInfo = new SGDeviceInfoParam();
	private BufferedImage imgRegistration1;
	private BufferedImage imgRegistration2;
	private BufferedImage imgVerification;
	private boolean r1Captured = false;
	private boolean r2Captured = false;
	private boolean v1Captured = false;
	private ApplicationInfo applicationInfo;
	private BiometricsUtilImpl biometricsUtil;
	private JButton resetBtn;
	private JButton memberSearchBtn;
	private JTextField memberSearchText;
	private JLabel memberPictureLabel;
	private JPanel searchPanel;
	private JPanel currentMemberPanel;
	private JTextField baseDirText;
	private JLabel baseDirLabel;
	private JButton saveBtn;
	private JButton clearBtn;
	private JTextField userPasswordText;
	private JLabel userPasswordLabel;
	private JTextField userNameText;
	private JLabel userNameLabel;
	private JTextField fundMasterUrlText;
	private JLabel fundMasterUrlLabel;
	private JLabel systemConfigLabel;
	private JPanel systemConfigPanel;
	private FundMasterConfiguration config;
	private PensionerBioQueryAdapter queryAdapter;

	/** The currently selected pensioner/member */
	private PensionerDto currentPensioner;

	/** Creates new form */
	public MainWindow() {
		biometricsUtil = new BiometricsUtilImpl();
		applicationInfo = new ApplicationInfoImpl();
		config = new FundMasterConfiguration();
		queryAdapter = new PensionerBioQueryAdapter(config);
		bLEDOn = false;
		initComponents();
		disableControls();
		this.jComboBoxRegisterSecurityLevel.setSelectedIndex(4);
		this.jComboBoxVerifySecurityLevel.setSelectedIndex(4);
		loadSystemConfig();
	}

	private void disableControls() {
		this.jButtonToggleLED.setEnabled(false);
		this.jButtonCapture.setEnabled(false);
		this.jButtonCaptureR1.setEnabled(false);
		this.jButtonCaptureR2.setEnabled(false);
		this.jButtonCaptureV1.setEnabled(false);
		this.jButtonRegister.setEnabled(false);
		this.jButtonVerify.setEnabled(false);
		this.jButtonGetDeviceInfo.setEnabled(false);
		this.jButtonConfig.setEnabled(false);
		getResetBtn().setEnabled(false);
		getMemberSearchBtn().setEnabled(false);
	}

	private void enableControls() {
		this.jButtonToggleLED.setEnabled(true);
		this.jButtonCapture.setEnabled(true);
		this.jButtonCaptureR1.setEnabled(true);
		this.jButtonCaptureR2.setEnabled(true);
		this.jButtonCaptureV1.setEnabled(true);
		this.jButtonGetDeviceInfo.setEnabled(true);
		this.jButtonConfig.setEnabled(true);
		getResetBtn().setEnabled(true);
		getMemberSearchBtn().setEnabled(true);
	}

	private void enableRegisterControls() {
		if (r1Captured && r2Captured)
			this.jButtonRegister.setEnabled(true);
	}

	private void enableVerifyControls() {
		if (v1Captured)
			this.jButtonVerify.setEnabled(true);
	}

	private void loadSystemConfig() {
		if (config.getBaseDir() != null) {
			baseDirText.setText(config.getBaseDir());
		}
		if (config.getUrl() != null) {
			fundMasterUrlText.setText(config.getUrl());
		}
		if (config.getUserName() != null) {
			userNameText.setText(config.getUserName());
		}
		if (config.getPassword() != null) {
			userPasswordText.setText(config.getPassword());
		}
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
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jLabelStatus = new javax.swing.JLabel();
		jTabbedPane1 = new javax.swing.JTabbedPane();
		{
			jPanelImage = new javax.swing.JPanel();
			jTabbedPane1.addTab("Device Test/Configuration", null, jPanelImage,
					null);
			jPanelImage
					.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
			jPanelImage.setSize(500, 392);
			jPanelImage.setFont(new java.awt.Font("Arial", 0, 12));
			{
				jButtonInit = new javax.swing.JButton();
				jPanelImage.add(jButtonInit,
						new org.netbeans.lib.awtextra.AbsoluteConstraints(10,
								10, 100, 30));
				jButtonInit.setText("Initialize");
				jButtonInit.setMaximumSize(new java.awt.Dimension(100, 30));
				jButtonInit.setMinimumSize(new java.awt.Dimension(100, 30));
				jButtonInit.setName("jButtonInit"); // NOI18N
				jButtonInit.setPreferredSize(new java.awt.Dimension(100, 30));
				jButtonInit.setFont(new java.awt.Font("Arial", 0, 12));
				jButtonInit
						.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(
									java.awt.event.ActionEvent evt) {
								jButtonInitActionPerformed(evt);
							}
						});
			}
			{
				jLabelImage = new javax.swing.JLabel();
				jPanelImage.add(jLabelImage,
						new org.netbeans.lib.awtextra.AbsoluteConstraints(10,
								60, -1, -1));
				jLabelImage
						.setBorder(javax.swing.BorderFactory
								.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
				jLabelImage.setMinimumSize(new java.awt.Dimension(260, 300));
				jLabelImage.setPreferredSize(new java.awt.Dimension(260, 300));
				jLabelImage.setFont(new java.awt.Font("Arial", 0, 12));
			}
			{
				jComboBoxUSBPort = new javax.swing.JComboBox();
				jPanelImage.add(jComboBoxUSBPort,
						new org.netbeans.lib.awtextra.AbsoluteConstraints(280,
								90, 220, 27));
				jComboBoxUSBPort.setModel(new javax.swing.DefaultComboBoxModel(
						new String[] { "AUTO_DETECT", "0", "1", "2", "3", "4",
								"5", "6", "7", "8", "9" }));
				jComboBoxUSBPort
						.setMaximumSize(new java.awt.Dimension(170, 27));
				jComboBoxUSBPort
						.setMinimumSize(new java.awt.Dimension(170, 27));
				jComboBoxUSBPort.setBounds(280, 90, 210, 27);
				jComboBoxUSBPort.setSize(220, 27);
				jComboBoxUSBPort.setFont(new java.awt.Font("Arial", 0, 12));
			}
			{
				jButtonToggleLED = new javax.swing.JButton();
				jPanelImage.add(jButtonToggleLED,
						new org.netbeans.lib.awtextra.AbsoluteConstraints(120,
								10, 100, 30));
				jButtonToggleLED.setText("Toggle LED");
				jButtonToggleLED
						.setMaximumSize(new java.awt.Dimension(100, 30));
				jButtonToggleLED
						.setMinimumSize(new java.awt.Dimension(100, 30));
				jButtonToggleLED.setPreferredSize(new java.awt.Dimension(100,
						30));
				jButtonToggleLED.setFont(new java.awt.Font("Arial", 0, 12));
				jButtonToggleLED
						.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(
									java.awt.event.ActionEvent evt) {
								jButtonToggleLEDActionPerformed(evt);
							}
						});
			}
			{
				jButtonCapture = new javax.swing.JButton();
				jPanelImage.add(jButtonCapture,
						new org.netbeans.lib.awtextra.AbsoluteConstraints(230,
								10, 100, 30));
				jButtonCapture.setText("Capture");
				jButtonCapture.setMaximumSize(new java.awt.Dimension(100, 30));
				jButtonCapture.setMinimumSize(new java.awt.Dimension(100, 30));
				jButtonCapture
						.setPreferredSize(new java.awt.Dimension(100, 30));
				jButtonCapture.setFont(new java.awt.Font("Arial", 0, 12));
				jButtonCapture
						.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(
									java.awt.event.ActionEvent evt) {
								jButtonCaptureActionPerformed(evt);
							}
						});
			}
			{
				jButtonConfig = new javax.swing.JButton();
				jPanelImage.add(jButtonConfig,
						new org.netbeans.lib.awtextra.AbsoluteConstraints(340,
								10, -1, 30));
				jButtonConfig.setText("Config");
				jButtonConfig.setMaximumSize(new java.awt.Dimension(100, 30));
				jButtonConfig.setMinimumSize(new java.awt.Dimension(100, 30));
				jButtonConfig.setPreferredSize(new java.awt.Dimension(100, 30));
				jButtonConfig.setFont(new java.awt.Font("Arial", 0, 12));
				jButtonConfig
						.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(
									java.awt.event.ActionEvent evt) {
								jButtonConfigActionPerformed(evt);
							}
						});
			}
			{
				jLabel1 = new javax.swing.JLabel();
				jPanelImage.add(jLabel1,
						new org.netbeans.lib.awtextra.AbsoluteConstraints(280,
								70, -1, -1));
				jLabel1.setText("USB Device");
				jLabel1.setFont(new java.awt.Font("Arial", 0, 12));
			}
			{
				jSliderQuality = new javax.swing.JSlider();
				jPanelImage.add(jSliderQuality,
						new org.netbeans.lib.awtextra.AbsoluteConstraints(270,
								170, 220, -1));
				jSliderQuality.setMajorTickSpacing(10);
				jSliderQuality.setMinorTickSpacing(5);
				jSliderQuality.setPaintLabels(true);
				jSliderQuality.setPaintTicks(true);
				jSliderQuality.setName(""); // NOI18N
				jSliderQuality.setOpaque(false);
				jSliderQuality.setFont(new java.awt.Font("Arial", 0, 12));
			}
			{
				jLabel2 = new javax.swing.JLabel();
				jPanelImage.add(jLabel2,
						new org.netbeans.lib.awtextra.AbsoluteConstraints(280,
								150, -1, -1));
				jLabel2.setText("Image Quality");
				jLabel2.setFont(new java.awt.Font("Arial", 0, 12));
			}
			{
				jLabel3 = new javax.swing.JLabel();
				jPanelImage.add(jLabel3,
						new org.netbeans.lib.awtextra.AbsoluteConstraints(290,
								230, -1, -1));
				jLabel3.setText("Timeout (seconds)");
				jLabel3.setFont(new java.awt.Font("Arial", 0, 12));
			}
			{
				jSliderSeconds = new javax.swing.JSlider();
				jPanelImage.add(jSliderSeconds,
						new org.netbeans.lib.awtextra.AbsoluteConstraints(270,
								250, 220, -1));
				jSliderSeconds.setMajorTickSpacing(1);
				jSliderSeconds.setMaximum(10);
				jSliderSeconds.setMinimum(1);
				jSliderSeconds.setPaintLabels(true);
				jSliderSeconds.setPaintTicks(true);
				jSliderSeconds.setValue(5);
				jSliderSeconds.setFont(new java.awt.Font("Arial", 0, 12));
			}
		}
		jPanelRegisterVerify = new javax.swing.JPanel();
		jLabelSecurityLevel = new javax.swing.JLabel();
		jLabelRegistration = new javax.swing.JLabel();
		jLabelVerification = new javax.swing.JLabel();
		jComboBoxRegisterSecurityLevel = new javax.swing.JComboBox();
		jComboBoxVerifySecurityLevel = new javax.swing.JComboBox();
		jLabelRegistrationBox = new javax.swing.JLabel();
		jLabelRegisterImage1 = new javax.swing.JLabel();
		jLabelRegisterImage2 = new javax.swing.JLabel();
		jLabelVerificationBox = new javax.swing.JLabel();
		jLabelVerifyImage = new javax.swing.JLabel();
		jButtonCaptureR1 = new javax.swing.JButton();
		jButtonCaptureV1 = new javax.swing.JButton();
		jButtonRegister = new javax.swing.JButton();
		jButtonVerify = new javax.swing.JButton();
		jButtonCaptureR2 = new javax.swing.JButton();
		jProgressBarR1 = new javax.swing.JProgressBar();
		jProgressBarR2 = new javax.swing.JProgressBar();
		jProgressBarV1 = new javax.swing.JProgressBar();
		jPanelDeviceInfo = new javax.swing.JPanel();
		jLabelDeviceInfoGroup = new javax.swing.JLabel();
		jLabelDeviceID = new javax.swing.JLabel();
		jTextFieldDeviceID = new javax.swing.JTextField();
		jLabelFWVersion = new javax.swing.JLabel();
		jTextFieldFWVersion = new javax.swing.JTextField();
		jLabelSerialNumber = new javax.swing.JLabel();
		jTextFieldSerialNumber = new javax.swing.JTextField();
		jLabelImageWidth = new javax.swing.JLabel();
		jTextFieldImageWidth = new javax.swing.JTextField();
		jLabelImageHeight = new javax.swing.JLabel();
		jTextFieldImageHeight = new javax.swing.JTextField();
		jLabelImageDPI = new javax.swing.JLabel();
		jTextFieldImageDPI = new javax.swing.JTextField();
		jLabelBrightness = new javax.swing.JLabel();
		jTextFieldBrightness = new javax.swing.JTextField();
		jLabelContrast = new javax.swing.JLabel();
		jTextFieldContrast = new javax.swing.JTextField();
		jLabelGain = new javax.swing.JLabel();
		jTextFieldGain = new javax.swing.JTextField();
		jButtonGetDeviceInfo = new javax.swing.JButton();
		jComboBoxDeviceName = new javax.swing.JComboBox();
		jLabelDeviceName = new javax.swing.JLabel();
		jLabelSpacer1 = new javax.swing.JLabel();
		jLabelSpacer2 = new javax.swing.JLabel();

		setTitle(applicationInfo.getApplicationName() + "-"
				+ applicationInfo.getApplicationVendor());
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				exitForm(evt);
			}
		});
		getContentPane().setLayout(
				new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabelStatus.setText("Click Initialize Button ...");
		jLabelStatus.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		getContentPane().add(
				jLabelStatus,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 675,
						30));
		jLabelStatus.setBounds(10, 470, 664, 30);
		jLabelStatus.setSize(675, 30);
		jLabelStatus.setFont(new java.awt.Font("Arial", 0, 12));

		jPanelRegisterVerify
				.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabelSecurityLevel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Security Level"));
		jPanelRegisterVerify.add(jLabelSecurityLevel,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 460,
						60));
		jLabelSecurityLevel.setFont(new java.awt.Font("Arial", 0, 12));

		jLabelRegistration.setText("Registration");
		jPanelRegisterVerify.add(jLabelRegistration,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 34, -1,
						-1));
		jLabelRegistration.setFont(new java.awt.Font("Arial", 0, 12));

		jLabelVerification.setText("Verification");
		jPanelRegisterVerify.add(jLabelVerification,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 34, -1,
						-1));
		jLabelVerification.setFont(new java.awt.Font("Arial", 0, 12));

		jComboBoxRegisterSecurityLevel
				.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
						"LOWEST", "LOWER", "LOW", "BELOW_NORMAL", "NORMAL",
						"ABOVE_NORMAL", "HIGH", "HIGHER", "HIGHEST" }));
		jPanelRegisterVerify.add(jComboBoxRegisterSecurityLevel,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 130,
						-1));
		jComboBoxRegisterSecurityLevel
				.setFont(new java.awt.Font("Arial", 0, 12));

		jComboBoxVerifySecurityLevel
				.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
						"LOWEST", "LOWER", "LOW", "BELOW_NORMAL", "NORMAL",
						"ABOVE_NORMAL", "HIGH", "HIGHER", "HIGHEST" }));
		jPanelRegisterVerify.add(jComboBoxVerifySecurityLevel,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 30, 130,
						-1));
		jComboBoxVerifySecurityLevel.setFont(new java.awt.Font("Arial", 0, 12));

		jLabelRegistrationBox.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Registration"));
		jPanelRegisterVerify.add(jLabelRegistrationBox,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 290,
						240));
		jLabelRegistrationBox.setFont(new java.awt.Font("Arial", 0, 12));

		jLabelRegisterImage1.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		jLabelRegisterImage1.setMinimumSize(new java.awt.Dimension(130, 150));
		jLabelRegisterImage1.setPreferredSize(new java.awt.Dimension(130, 150));
		jPanelRegisterVerify.add(jLabelRegisterImage1,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1,
						-1));

		jLabelRegisterImage2.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		jLabelRegisterImage2.setMinimumSize(new java.awt.Dimension(130, 150));
		jLabelRegisterImage2.setPreferredSize(new java.awt.Dimension(130, 150));
		jPanelRegisterVerify.add(jLabelRegisterImage2,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, -1,
						-1));

		jLabelVerificationBox.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Verification"));
		jPanelRegisterVerify.add(jLabelVerificationBox,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 80, 150,
						240));
		jLabelVerificationBox.setFont(new java.awt.Font("Arial", 0, 12));

		jLabelVerifyImage.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		jLabelVerifyImage.setMinimumSize(new java.awt.Dimension(130, 150));
		jLabelVerifyImage.setPreferredSize(new java.awt.Dimension(130, 150));
		jPanelRegisterVerify.add(jLabelVerifyImage,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 100, -1,
						-1));

		jButtonCaptureR1.setText("Capture R1");
		jButtonCaptureR1.setActionCommand("jButton1");
		jButtonCaptureR1.setMaximumSize(new java.awt.Dimension(130, 30));
		jButtonCaptureR1.setMinimumSize(new java.awt.Dimension(130, 30));
		jButtonCaptureR1.setPreferredSize(new java.awt.Dimension(130, 30));
		jButtonCaptureR1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonCaptureR1ActionPerformed(evt);
			}
		});
		jPanelRegisterVerify.add(jButtonCaptureR1,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 130,
						30));
		jButtonCaptureR1.setFont(new java.awt.Font("Arial", 0, 12));

		jButtonCaptureV1.setText("Capture V1");
		jButtonCaptureV1.setActionCommand("jButton1");
		jButtonCaptureV1.setMaximumSize(new java.awt.Dimension(130, 30));
		jButtonCaptureV1.setMinimumSize(new java.awt.Dimension(130, 30));
		jButtonCaptureV1.setPreferredSize(new java.awt.Dimension(130, 30));
		jButtonCaptureV1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonCaptureV1ActionPerformed(evt);
			}
		});
		jPanelRegisterVerify.add(jButtonCaptureV1,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 280,
						130, 30));
		jButtonCaptureV1.setFont(new java.awt.Font("Arial", 0, 12));

		jButtonRegister.setText("Register");
		jButtonRegister.setActionCommand("jButton1");
		jButtonRegister.setMaximumSize(new java.awt.Dimension(270, 30));
		jButtonRegister.setMinimumSize(new java.awt.Dimension(270, 30));
		jButtonRegister.setPreferredSize(new java.awt.Dimension(270, 30));
		jButtonRegister.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonRegisterActionPerformed(evt);
			}
		});
		jPanelRegisterVerify.add(jButtonRegister,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 270,
						30));
		jButtonRegister.setFont(new java.awt.Font("Arial", 0, 12));

		jButtonVerify.setText("Verify");
		jButtonVerify.setActionCommand("jButton1");
		jButtonVerify.setMaximumSize(new java.awt.Dimension(130, 30));
		jButtonVerify.setMinimumSize(new java.awt.Dimension(130, 30));
		jButtonVerify.setPreferredSize(new java.awt.Dimension(130, 30));
		jButtonVerify.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonVerifyActionPerformed(evt);
			}
		});
		jPanelRegisterVerify.add(jButtonVerify,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 340,
						130, 30));
		jButtonVerify.setFont(new java.awt.Font("Arial", 0, 12));

		jButtonCaptureR2.setText("Capture R2");
		jButtonCaptureR2.setActionCommand("jButton1");
		jButtonCaptureR2.setMaximumSize(new java.awt.Dimension(130, 30));
		jButtonCaptureR2.setMinimumSize(new java.awt.Dimension(130, 30));
		jButtonCaptureR2.setPreferredSize(new java.awt.Dimension(130, 30));
		jButtonCaptureR2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonCaptureR2ActionPerformed(evt);
			}
		});
		jPanelRegisterVerify.add(jButtonCaptureR2,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 280,
						130, 30));
		jButtonCaptureR2.setFont(new java.awt.Font("Arial", 0, 12));

		jProgressBarR1.setForeground(new java.awt.Color(0, 51, 153));
		jPanelRegisterVerify.add(jProgressBarR1,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 130,
						-1));

		jProgressBarR2.setForeground(new java.awt.Color(0, 51, 153));
		jPanelRegisterVerify.add(jProgressBarR2,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250,
						130, -1));

		jProgressBarV1.setForeground(new java.awt.Color(0, 51, 153));
		jPanelRegisterVerify.add(jProgressBarV1,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 250,
						130, -1));

		/** current member panel */
		jPanelRegisterVerify.add(getCurrentMemberPanel(),
				new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 80, -1,
						-1));
		jPanelRegisterVerify.add(getResetBtn(),
				new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 340, -1,
						-1));

		jTabbedPane1.addTab("Register/Verify", jPanelRegisterVerify);
		jPanelRegisterVerify.setFont(new java.awt.Font("Arial", 0, 12));

		jPanelDeviceInfo
				.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabelDeviceInfoGroup.setBorder(javax.swing.BorderFactory
				.createTitledBorder("DeviceInfo"));
		jPanelDeviceInfo.add(jLabelDeviceInfoGroup,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 290,
						290));
		jLabelDeviceInfoGroup.setFont(new java.awt.Font("Arial", 0, 12));

		jLabelDeviceID.setText("Device ID");
		jPanelDeviceInfo.add(jLabelDeviceID,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1,
						-1));
		jLabelDeviceID.setFont(new java.awt.Font("Arial", 0, 12));

		jTextFieldDeviceID.setEditable(false);
		jTextFieldDeviceID.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		jPanelDeviceInfo.add(jTextFieldDeviceID,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 160,
						-1));
		jTextFieldDeviceID.setFont(new java.awt.Font("Arial", 0, 12));

		jLabelFWVersion.setText("F/W Version");
		jPanelDeviceInfo.add(jLabelFWVersion,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1,
						-1));
		jLabelFWVersion.setFont(new java.awt.Font("Arial", 0, 12));

		jTextFieldFWVersion.setEditable(false);
		jTextFieldFWVersion.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		jPanelDeviceInfo.add(jTextFieldFWVersion,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 160,
						-1));
		jTextFieldFWVersion.setFont(new java.awt.Font("Arial", 0, 12));

		jLabelSerialNumber.setText("Serial #");
		jPanelDeviceInfo.add(jLabelSerialNumber,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1,
						-1));
		jLabelSerialNumber.setFont(new java.awt.Font("Arial", 0, 12));

		jTextFieldSerialNumber.setEditable(false);
		jTextFieldSerialNumber.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		jPanelDeviceInfo.add(jTextFieldSerialNumber,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 160,
						-1));
		jTextFieldSerialNumber.setFont(new java.awt.Font("Arial", 0, 12));

		jLabelImageWidth.setText("Image Width");
		jPanelDeviceInfo.add(jLabelImageWidth,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1,
						-1));
		jLabelImageWidth.setFont(new java.awt.Font("Arial", 0, 12));

		jTextFieldImageWidth.setEditable(false);
		jTextFieldImageWidth.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		jPanelDeviceInfo.add(jTextFieldImageWidth,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120,
						160, -1));
		jTextFieldImageWidth.setFont(new java.awt.Font("Arial", 0, 12));

		jLabelImageHeight.setText("Image Height");
		jPanelDeviceInfo.add(jLabelImageHeight,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1,
						-1));
		jLabelImageHeight.setFont(new java.awt.Font("Arial", 0, 12));

		jTextFieldImageHeight.setEditable(false);
		jTextFieldImageHeight.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		jPanelDeviceInfo.add(jTextFieldImageHeight,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150,
						160, -1));
		jTextFieldImageHeight.setFont(new java.awt.Font("Arial", 0, 12));

		jLabelImageDPI.setText("Image DPI");
		jPanelDeviceInfo.add(jLabelImageDPI,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1,
						-1));
		jLabelImageDPI.setFont(new java.awt.Font("Arial", 0, 12));

		jTextFieldImageDPI.setEditable(false);
		jTextFieldImageDPI.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		jPanelDeviceInfo.add(jTextFieldImageDPI,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180,
						160, -1));
		jTextFieldImageDPI.setFont(new java.awt.Font("Arial", 0, 12));

		jLabelBrightness.setText("Brightness");
		jPanelDeviceInfo.add(jLabelBrightness,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, -1,
						-1));
		jLabelBrightness.setFont(new java.awt.Font("Arial", 0, 12));

		jTextFieldBrightness.setEditable(false);
		jTextFieldBrightness.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		jPanelDeviceInfo.add(jTextFieldBrightness,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210,
						160, -1));
		jTextFieldBrightness.setFont(new java.awt.Font("Arial", 0, 12));

		jLabelContrast.setText("Contrast");
		jPanelDeviceInfo.add(jLabelContrast,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, -1,
						-1));
		jLabelContrast.setFont(new java.awt.Font("Arial", 0, 12));

		jTextFieldContrast.setEditable(false);
		jTextFieldContrast.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		jPanelDeviceInfo.add(jTextFieldContrast,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240,
						160, -1));
		jTextFieldContrast.setFont(new java.awt.Font("Arial", 0, 12));

		jLabelGain.setText("Gain");
		jPanelDeviceInfo.add(jLabelGain,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1,
						-1));
		jLabelGain.setFont(new java.awt.Font("Arial", 0, 12));

		jTextFieldGain.setEditable(false);
		jTextFieldGain.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		jPanelDeviceInfo.add(jTextFieldGain,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270,
						160, -1));
		jTextFieldGain.setFont(new java.awt.Font("Arial", 0, 12));

		jButtonGetDeviceInfo.setText("Get Device Info");
		jButtonGetDeviceInfo.setMaximumSize(new java.awt.Dimension(150, 30));
		jButtonGetDeviceInfo.setMinimumSize(new java.awt.Dimension(150, 30));
		jButtonGetDeviceInfo.setPreferredSize(new java.awt.Dimension(150, 30));
		jButtonGetDeviceInfo
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						jButtonGetDeviceInfoActionPerformed(evt);
					}
				});
		jPanelDeviceInfo.add(jButtonGetDeviceInfo,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, 150,
						30));
		jButtonGetDeviceInfo.setFont(new java.awt.Font("Arial", 0, 12));

		jTabbedPane1.addTab("Device Info", jPanelDeviceInfo);
		jPanelDeviceInfo.setFont(new java.awt.Font("Arial", 0, 12));
		{
			systemConfigPanel = new JPanel();
			jTabbedPane1.addTab("System Configuration", null,
					systemConfigPanel, null);
			systemConfigPanel.setLayout(null);
			systemConfigPanel.setFont(new java.awt.Font("Arial", 0, 12));
			{
				systemConfigLabel = new JLabel();
				systemConfigPanel.add(systemConfigLabel);
				systemConfigLabel.setBounds(12, 23, 402, 236);
				systemConfigLabel.setBorder(BorderFactory
						.createTitledBorder("Configuration"));
			}
			{
				fundMasterUrlLabel = new JLabel();
				systemConfigPanel.add(fundMasterUrlLabel);
				fundMasterUrlLabel.setText("Fund Master Url");
				fundMasterUrlLabel.setBounds(26, 89, 93, 21);
				fundMasterUrlLabel.setFont(new java.awt.Font("Arial", 0, 12));
			}
			{
				fundMasterUrlText = new JTextField();
				systemConfigPanel.add(fundMasterUrlText);
				fundMasterUrlText.setBounds(134, 90, 208, 22);
				fundMasterUrlText.setFont(new java.awt.Font("Arial", 0, 12));
			}
			{
				userNameLabel = new JLabel();
				systemConfigPanel.add(userNameLabel);
				userNameLabel.setText("User Name");
				userNameLabel.setBounds(26, 119, 88, 16);
				userNameLabel.setFont(new java.awt.Font("Arial", 0, 12));
			}
			{
				userNameText = new JTextField();
				systemConfigPanel.add(userNameText);
				userNameText.setBounds(134, 119, 208, 23);
				userNameText.setFont(new java.awt.Font("Arial", 0, 12));
			}
			{
				userPasswordLabel = new JLabel();
				systemConfigPanel.add(userPasswordLabel);
				userPasswordLabel.setText("Password");
				userPasswordLabel.setBounds(26, 148, 89, 16);
				userPasswordLabel.setFont(new java.awt.Font("Arial", 0, 12));
			}
			{
				userPasswordText = new JTextField();
				systemConfigPanel.add(userPasswordText);
				userPasswordText.setBounds(134, 151, 208, 23);
				userPasswordText.setFont(new java.awt.Font("Arial", 0, 12));
			}
			{
				clearBtn = new JButton();
				systemConfigPanel.add(clearBtn);
				clearBtn.setText("Clear");
				clearBtn.setBounds(191, 183, 71, 23);
				clearBtn.setFont(new java.awt.Font("Arial", 0, 12));
				clearBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						clearBtnActionPerformed(evt);
					}
				});
			}
			{
				saveBtn = new JButton();
				systemConfigPanel.add(saveBtn);
				saveBtn.setText("Save");
				saveBtn.setBounds(273, 183, 66, 23);
				saveBtn.setFont(new java.awt.Font("Arial", 0, 12));
				saveBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						saveBtnActionPerformed(evt);
					}
				});
			}
			{
				baseDirLabel = new JLabel();
				systemConfigPanel.add(baseDirLabel);
				baseDirLabel.setText("Config. Directory");
				baseDirLabel.setBounds(25, 60, 98, 16);
				baseDirLabel.setFont(new java.awt.Font("Arial", 0, 12));
			}
			{
				baseDirText = new JTextField();
				systemConfigPanel.add(baseDirText);
				baseDirText.setBounds(135, 62, 207, 23);
				baseDirText.setFont(new java.awt.Font("Arial", 0, 12));
				baseDirText.setEditable(false);
			}
		}

		getContentPane().add(
				jTabbedPane1,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 35, 680,
						420));
		jTabbedPane1.setBounds(10, 35, 662, 420);
		jTabbedPane1.setSize(680, 420);

		jComboBoxDeviceName.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "AUTO", "FDU04", "FDU03", "FDU02" }));
		jComboBoxDeviceName.setMinimumSize(new java.awt.Dimension(350, 10));
		jComboBoxDeviceName.setVerifyInputWhenFocusTarget(false);
		getContentPane().add(
				jComboBoxDeviceName,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 350,
						-1));
		jComboBoxDeviceName.setSize(350, 23);
		jComboBoxDeviceName.setFont(new java.awt.Font("Arial", 0, 12));

		jLabelDeviceName.setText("Device Name");
		getContentPane().add(
				jLabelDeviceName,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 110,
						-1));
		jLabelDeviceName.setFont(new java.awt.Font("Arial", 0, 12));

		jLabelSpacer1.setText(" ");
		getContentPane().add(
				jLabelSpacer1,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 490, 10,
						-1));

		jLabelSpacer2.setText(" ");
		getContentPane().add(
				jLabelSpacer2,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 10,
						-1));

		pack();
		this.setSize(700, 544);
	}// </editor-fold>//GEN-END:initComponents

	private void jButtonGetDeviceInfoActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonGetDeviceInfoActionPerformed
		long iError;

		iError = biometricsUtil.getDeviceInfo(deviceInfo);
		if (ret == SGFDxErrorCode.SGFDX_ERROR_NONE) {
			this.jLabelStatus.setText("Getting Device Info Successful!");
			this.jTextFieldSerialNumber.setText(new String(deviceInfo
					.deviceSN()));
			this.jTextFieldBrightness.setText(new String(Integer
					.toString(deviceInfo.brightness)));
			this.jTextFieldContrast.setText(new String(Integer
					.toString((int) deviceInfo.contrast)));
			this.jTextFieldDeviceID.setText(new String(Integer
					.toString(deviceInfo.deviceID)));
			this.jTextFieldFWVersion.setText(new String(Integer
					.toHexString(deviceInfo.FWVersion)));
			this.jTextFieldGain.setText(new String(Integer
					.toString(deviceInfo.gain)));
			this.jTextFieldImageDPI.setText(new String(Integer
					.toString(deviceInfo.imageDPI)));
			this.jTextFieldImageHeight.setText(new String(Integer
					.toString(deviceInfo.imageHeight)));
			this.jTextFieldImageWidth.setText(new String(Integer
					.toString(deviceInfo.imageWidth)));
		} else
			this.jLabelStatus.setText("Getting Device Info Error! : " + iError);

	}// GEN-LAST:event_jButtonGetDeviceInfoActionPerformed

	private void jButtonConfigActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonConfigActionPerformed
		long iError;

		iError = biometricsUtil.configure();
		if (iError == SGFDxErrorCode.SGFDX_ERROR_NONE) {
			this.jLabelStatus.setText("Configuration Successful");
			this.jButtonGetDeviceInfo.doClick();
		} else if (iError == SGFDxErrorCode.SGFDX_ERROR_NOT_USED)
			this.jLabelStatus
					.setText("Configure() not supported on this platform");
		else
			this.jLabelStatus.setText("Configuration Error : " + iError);

	}// GEN-LAST:event_jButtonConfigActionPerformed

	private void jButtonVerifyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonVerifyActionPerformed
		long iError;
		long secuLevel = (long) (this.jComboBoxVerifySecurityLevel
				.getSelectedIndex() + 1);
		boolean[] matched = new boolean[1];
		matched[0] = false;

		iError = biometricsUtil.verify(vrfMin, secuLevel);
		if (iError == SGFDxErrorCode.SGFDX_ERROR_NONE) {
			if (matched[0])
				this.jLabelStatus
						.setText("Verification Success (1st template)");
			else {
				iError = biometricsUtil.getMatchingScore(regMin2, vrfMin);
				if (iError == SGFDxErrorCode.SGFDX_ERROR_NONE)
					if (matched[0])
						this.jLabelStatus
								.setText("Verification Success (2nd template)");
					else
						this.jLabelStatus.setText("Verification Fail");
				else
					this.jLabelStatus
							.setText("Verification Attempt 2 Fail - MatchTemplate() Error : "
									+ iError);

			}
		} else
			this.jLabelStatus
					.setText("Verification Attempt 1 Fail - MatchTemplate() Error : "
							+ iError);
	}// GEN-LAST:event_jButtonVerifyActionPerformed

	private byte[] convertBufferedImageToByteArray(BufferedImage image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", baos);
			baos.flush();
		} catch (IOException e) {
			LOG.error(e);
		}
		return baos.toByteArray();
	}

	private void jButtonRegisterActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonRegisterActionPerformed
		long iError;
		long secuLevel = (long) (this.jComboBoxRegisterSecurityLevel
				.getSelectedIndex() + 1);
		int matchScore = 0;
		matchScore = biometricsUtil.getMatchingScore(regMin1, regMin2);
		iError = biometricsUtil.register(regMin1, regMin2,
				convertBufferedImageToByteArray(imgRegistration1), secuLevel);
		if (iError == SGFDxErrorCode.SGFDX_ERROR_NONE) {
			this.jLabelStatus.setText("Registration Success, Matching Score: "
					+ matchScore);
		} else if (iError == SGFDxErrorCode.SGFDX_ERROR_MATCH_FAIL) {
			this.jLabelStatus
					.setText("Registration Fail, Templates Mismatch!, Matching Score: "
							+ matchScore);
		} else {
			this.jLabelStatus
					.setText("Registration Fail, Getting Matching Score Error : "
							+ iError);
		}

	}// GEN-LAST:event_jButtonRegisterActionPerformed

	private void jButtonCaptureV1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonCaptureV1ActionPerformed
		byte[] imageBuffer1 = ((java.awt.image.DataBufferByte) imgVerification
				.getRaster().getDataBuffer()).getData();
		long iError = SGFDxErrorCode.SGFDX_ERROR_NONE;

		iError = biometricsUtil.getImageEx(imageBuffer1,
				jSliderSeconds.getValue() * 1000, 0, jSliderQuality.getValue());
		int quality = biometricsUtil.getImageQuality(deviceInfo.imageWidth,
				deviceInfo.imageHeight, imageBuffer1);
		this.jProgressBarV1.setValue(quality);
		SGFingerInfo fingerInfo = new SGFingerInfo();
		fingerInfo.FingerNumber = SGFingerPosition.SG_FINGPOS_LI;
		fingerInfo.ImageQuality = quality;
		fingerInfo.ImpressionType = SGImpressionType.SG_IMPTYPE_LP;
		fingerInfo.ViewNumber = 1;

		if (iError == SGFDxErrorCode.SGFDX_ERROR_NONE) {
			this.jLabelVerifyImage.setIcon(new ImageIcon(imgVerification
					.getScaledInstance(130, 150, Image.SCALE_DEFAULT)));
			if (quality == 0)
				this.jLabelStatus.setText("Get Image Success [" + ret
						+ "] but image quality is [" + quality
						+ "]. Please try again");
			else {
				this.jLabelStatus.setText("Get Image Success [" + ret + "]");

				iError = biometricsUtil.createTemplate(fingerInfo,
						imageBuffer1, vrfMin);
				if (iError == SGFDxErrorCode.SGFDX_ERROR_NONE) {
					this.jLabelStatus
							.setText("Verification image was captured");
					v1Captured = true;
					this.enableVerifyControls();
				} else
					this.jLabelStatus.setText("Creating Template Error : "
							+ iError);
			}
		} else
			this.jLabelStatus.setText("Get Image Error : " + iError);

	}// GEN-LAST:event_jButtonCaptureV1ActionPerformed

	private void jButtonCaptureR2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonCaptureR2ActionPerformed
		byte[] imageBuffer1 = ((java.awt.image.DataBufferByte) imgRegistration2
				.getRaster().getDataBuffer()).getData();
		long iError = SGFDxErrorCode.SGFDX_ERROR_NONE;

		iError = biometricsUtil.getImageEx(imageBuffer1,
				jSliderSeconds.getValue() * 1000, 0, jSliderQuality.getValue());
		int quality = biometricsUtil.getImageQuality(deviceInfo.imageWidth,
				deviceInfo.imageHeight, imageBuffer1);
		this.jProgressBarR2.setValue(quality);
		SGFingerInfo fingerInfo = new SGFingerInfo();
		fingerInfo.FingerNumber = SGFingerPosition.SG_FINGPOS_LI;
		fingerInfo.ImageQuality = quality;
		fingerInfo.ImpressionType = SGImpressionType.SG_IMPTYPE_LP;
		fingerInfo.ViewNumber = 1;

		if (iError == SGFDxErrorCode.SGFDX_ERROR_NONE) {
			this.jLabelRegisterImage2.setIcon(new ImageIcon(imgRegistration2
					.getScaledInstance(130, 150, Image.SCALE_DEFAULT)));
			if (quality == 0)
				this.jLabelStatus.setText("Get Image Success [" + ret
						+ "] but image quality is [" + quality
						+ "]. Please try again");
			else {
				this.jLabelStatus.setText("Get Image Success [" + ret + "]");

				iError = biometricsUtil.createTemplate(fingerInfo,
						imageBuffer1, regMin2);
				if (iError == SGFDxErrorCode.SGFDX_ERROR_NONE) {
					this.jLabelStatus
							.setText("Second registration image was captured");
					r2Captured = true;
					this.enableRegisterControls();
				} else
					this.jLabelStatus.setText("Creating Template Error : "
							+ iError);
			}
		} else
			this.jLabelStatus.setText("Get Image Error : " + iError);

	}// GEN-LAST:event_jButtonCaptureR2ActionPerformed

	private void jButtonCaptureR1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonCaptureR1ActionPerformed
		byte[] imageBuffer1 = ((java.awt.image.DataBufferByte) imgRegistration1
				.getRaster().getDataBuffer()).getData();
		long iError = SGFDxErrorCode.SGFDX_ERROR_NONE;

		iError = biometricsUtil.getImageEx(imageBuffer1,
				jSliderSeconds.getValue() * 1000, 0, jSliderQuality.getValue());
		int quality = biometricsUtil.getImageQuality(deviceInfo.imageWidth,
				deviceInfo.imageHeight, imageBuffer1);
		this.jProgressBarR1.setValue(quality);
		SGFingerInfo fingerInfo = new SGFingerInfo();
		fingerInfo.FingerNumber = SGFingerPosition.SG_FINGPOS_LI;
		fingerInfo.ImageQuality = quality;
		fingerInfo.ImpressionType = SGImpressionType.SG_IMPTYPE_LP;
		fingerInfo.ViewNumber = 1;

		if (iError == SGFDxErrorCode.SGFDX_ERROR_NONE) {
			this.jLabelRegisterImage1.setIcon(new ImageIcon(imgRegistration1
					.getScaledInstance(130, 150, Image.SCALE_DEFAULT)));
			if (quality == 0)
				this.jLabelStatus.setText("Get Image Success [" + ret
						+ "] but image quality is [" + quality
						+ "]. Please try again");
			else {

				this.jLabelStatus.setText("Get Image Success [" + ret + "]");

				iError = biometricsUtil.createTemplate(fingerInfo,
						imageBuffer1, regMin1);
				if (iError == SGFDxErrorCode.SGFDX_ERROR_NONE) {
					this.jLabelStatus
							.setText("First registration image was captured");
					r1Captured = true;
					this.enableRegisterControls();
				} else
					this.jLabelStatus.setText("Creating Template Error : "
							+ iError);
			}
		} else
			this.jLabelStatus.setText("Get Image Error : " + iError);

	}// GEN-LAST:event_jButtonCaptureR1ActionPerformed

	private void jButtonCaptureActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonCaptureActionPerformed
		BufferedImage img1gray = new BufferedImage(deviceInfo.imageWidth,
				deviceInfo.imageHeight, BufferedImage.TYPE_BYTE_GRAY);
		byte[] imageBuffer1 = ((java.awt.image.DataBufferByte) img1gray
				.getRaster().getDataBuffer()).getData();
		if (biometricsUtil != null) {
			ret = biometricsUtil.getImageEx(imageBuffer1,
					jSliderSeconds.getValue() * 1000, 0,
					jSliderQuality.getValue());
			if (ret == SGFDxErrorCode.SGFDX_ERROR_NONE) {
				this.jLabelImage.setIcon(new ImageIcon(img1gray));
				int quality = biometricsUtil.getImageQuality(
						deviceInfo.imageWidth, deviceInfo.imageHeight,
						imageBuffer1);
				this.jLabelStatus.setText("Get Image Success [" + ret + "]"
						+ " Image Quality [" + quality + "]");
			} else {
				this.jLabelStatus.setText("Get Image Error [" + ret + "]");
			}
		} else {
			this.jLabelStatus.setText("JSGFPLib is not Initialized");
		}

	}// GEN-LAST:event_jButtonCaptureActionPerformed

	private void jButtonToggleLEDActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonToggleLEDActionPerformed
		if (biometricsUtil != null) {
			bLEDOn = !bLEDOn;
			ret = biometricsUtil.setLedOn(bLEDOn);
			if (ret == SGFDxErrorCode.SGFDX_ERROR_NONE) {
				this.jLabelStatus.setText("Set Led On(" + bLEDOn
						+ ") Success [" + ret + "]");
			} else {
				this.jLabelStatus.setText("Set Led On(" + bLEDOn + ") Error ["
						+ ret + "]");
			}
		} else {
			this.jLabelStatus.setText("JSGFPLib is not Initialized");
		}
	}// GEN-LAST:event_jButtonToggleLEDActionPerformed

	private void jButtonInitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonInitActionPerformed
		int selectedDevice = jComboBoxDeviceName.getSelectedIndex();
		switch (selectedDevice) {
		case 0: // USB
		default:
			this.deviceName = SGFDxDeviceName.SG_DEV_AUTO;
			break;
		case 1: // FDU04
			this.deviceName = SGFDxDeviceName.SG_DEV_FDU04;
			break;
		case 2: // CN_FDU03
			this.deviceName = SGFDxDeviceName.SG_DEV_FDU03;
			break;
		case 3: // CN_FDU02
			this.deviceName = SGFDxDeviceName.SG_DEV_FDU02;
			break;
		}
		ret = biometricsUtil.initDevice(this.deviceName);
		if (ret == SGFDxErrorCode.SGFDX_ERROR_NONE) {
			this.jLabelStatus.setText("Initialization Success");
			this.devicePort = SGPPPortAddr.AUTO_DETECT;
			switch (this.jComboBoxUSBPort.getSelectedIndex()) {
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
				this.devicePort = this.jComboBoxUSBPort.getSelectedIndex() - 1;
				break;
			}
			ret = biometricsUtil.openDevice(this.devicePort);
			if (ret == SGFDxErrorCode.SGFDX_ERROR_NONE) {
				this.jLabelStatus.setText("Open Device Success [" + ret + "]");
				ret = biometricsUtil.getDeviceInfo(deviceInfo);
				if (ret == SGFDxErrorCode.SGFDX_ERROR_NONE) {
					this.jTextFieldSerialNumber.setText(new String(deviceInfo
							.deviceSN()));
					this.jTextFieldBrightness.setText(new String(Integer
							.toString(deviceInfo.brightness)));
					this.jTextFieldContrast.setText(new String(Integer
							.toString((int) deviceInfo.contrast)));
					this.jTextFieldDeviceID.setText(new String(Integer
							.toString(deviceInfo.deviceID)));
					this.jTextFieldFWVersion.setText(new String(Integer
							.toHexString(deviceInfo.FWVersion)));
					this.jTextFieldGain.setText(new String(Integer
							.toString(deviceInfo.gain)));
					this.jTextFieldImageDPI.setText(new String(Integer
							.toString(deviceInfo.imageDPI)));
					this.jTextFieldImageHeight.setText(new String(Integer
							.toString(deviceInfo.imageHeight)));
					this.jTextFieldImageWidth.setText(new String(Integer
							.toString(deviceInfo.imageWidth)));
					imgRegistration1 = new BufferedImage(deviceInfo.imageWidth,
							deviceInfo.imageHeight,
							BufferedImage.TYPE_BYTE_GRAY);
					imgRegistration2 = new BufferedImage(deviceInfo.imageWidth,
							deviceInfo.imageHeight,
							BufferedImage.TYPE_BYTE_GRAY);
					imgVerification = new BufferedImage(deviceInfo.imageWidth,
							deviceInfo.imageHeight,
							BufferedImage.TYPE_BYTE_GRAY);
					this.enableControls();
				} else
					this.jLabelStatus.setText("Getting Device Info Error ["
							+ ret + "]");
			} else
				this.jLabelStatus.setText("Open Device Error [" + ret + "]");
		} else
			this.jLabelStatus.setText("Initialization Failed");

	}// GEN-LAST:event_jButtonInitActionPerformed

	/** Exit the Application */
	private void exitForm(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_exitForm
		this.setVisible(false);
	}// GEN-LAST:event_exitForm

	/**
	 * For testing during development
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		new MainWindow().setVisible(true);
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButtonCapture;
	private javax.swing.JButton jButtonCaptureR1;
	private javax.swing.JButton jButtonCaptureR2;
	private javax.swing.JButton jButtonCaptureV1;
	private javax.swing.JButton jButtonConfig;
	private javax.swing.JButton jButtonGetDeviceInfo;
	private javax.swing.JButton jButtonInit;
	private javax.swing.JButton jButtonRegister;
	private javax.swing.JButton jButtonToggleLED;
	private javax.swing.JButton jButtonVerify;
	private javax.swing.JComboBox jComboBoxDeviceName;
	private javax.swing.JComboBox jComboBoxRegisterSecurityLevel;
	private javax.swing.JComboBox jComboBoxUSBPort;
	private javax.swing.JComboBox jComboBoxVerifySecurityLevel;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabelBrightness;
	private javax.swing.JLabel jLabelContrast;
	private javax.swing.JLabel jLabelDeviceID;
	private javax.swing.JLabel jLabelDeviceInfoGroup;
	private javax.swing.JLabel jLabelDeviceName;
	private javax.swing.JLabel jLabelFWVersion;
	private javax.swing.JLabel jLabelGain;
	private javax.swing.JLabel jLabelImage;
	private javax.swing.JLabel jLabelImageDPI;
	private javax.swing.JLabel jLabelImageHeight;
	private javax.swing.JLabel jLabelImageWidth;
	private javax.swing.JLabel jLabelRegisterImage1;
	private javax.swing.JLabel jLabelRegisterImage2;
	private javax.swing.JLabel jLabelRegistration;
	private javax.swing.JLabel jLabelRegistrationBox;
	private javax.swing.JLabel jLabelSecurityLevel;
	private javax.swing.JLabel jLabelSerialNumber;
	private javax.swing.JLabel jLabelSpacer1;
	private javax.swing.JLabel jLabelSpacer2;
	private javax.swing.JLabel jLabelStatus;
	private javax.swing.JLabel jLabelVerification;
	private javax.swing.JLabel jLabelVerificationBox;
	private javax.swing.JLabel jLabelVerifyImage;
	private javax.swing.JPanel jPanelDeviceInfo;
	private javax.swing.JPanel jPanelImage;
	private javax.swing.JPanel jPanelRegisterVerify;
	private javax.swing.JProgressBar jProgressBarR1;
	private javax.swing.JProgressBar jProgressBarR2;
	private javax.swing.JProgressBar jProgressBarV1;
	private javax.swing.JSlider jSliderQuality;
	private javax.swing.JSlider jSliderSeconds;
	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JTextField jTextFieldBrightness;
	private javax.swing.JTextField jTextFieldContrast;
	private javax.swing.JTextField jTextFieldDeviceID;
	private javax.swing.JTextField jTextFieldFWVersion;
	private javax.swing.JTextField jTextFieldGain;
	private javax.swing.JTextField jTextFieldImageDPI;
	private javax.swing.JTextField jTextFieldImageHeight;
	private javax.swing.JTextField jTextFieldImageWidth;
	private javax.swing.JTextField jTextFieldSerialNumber;

	// End of variables declaration//GEN-END:variables

	public void exitOperation() {
		biometricsUtil.close();
		System.exit(0);

	}

	private void saveBtnActionPerformed(ActionEvent evt) {
		String baseDir = baseDirText.getText();
		String url = fundMasterUrlText.getText();
		String userName = userNameText.getText();
		String password = userPasswordText.getText();
		if (!baseDir.equals("") && !url.equals("") && !userName.equals("")
				&& !password.equals("")) {
			config.saveConfiguration(baseDir, url, userName, password);
			JOptionPane.showMessageDialog(new JFrame(), "OK");
		}

	}

	/**
	 * @return the applicationInfo
	 */
	public ApplicationInfo getApplicationInfo() {
		return applicationInfo;
	}

	/**
	 * @return the biometricsUtil
	 */
	public BiometricsUtil getBiometricsUtil() {
		return biometricsUtil;
	}

	/**
	 * @return the config
	 */
	public FundMasterConfiguration getConfig() {
		return config;
	}

	private void clearBtnActionPerformed(ActionEvent evt) {
		fundMasterUrlText.setText("");
		userNameText.setText("");
		userPasswordText.setText("");
	}

	private JPanel getCurrentMemberPanel() {
		if (currentMemberPanel == null) {
			currentMemberPanel = new JPanel();
			BorderLayout currentMemberPanelLayout = new BorderLayout();
			currentMemberPanel.setLayout(currentMemberPanelLayout);
			currentMemberPanel.setBounds(500, 85, 135, 236);
			currentMemberPanel.setBorder(BorderFactory.createTitledBorder(null,
					"Current Member", TitledBorder.LEADING,
					TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial",
							0, 12)));
			currentMemberPanel.setFont(new java.awt.Font("Arial", 0, 12));
			currentMemberPanel.add(getSearchPanel(), BorderLayout.NORTH);
		}
		return currentMemberPanel;
	}

	private JPanel getSearchPanel() {
		if (searchPanel == null) {
			searchPanel = new JPanel();
			FlowLayout searchPanelLayout = new FlowLayout();
			searchPanel.setLayout(searchPanelLayout);
			searchPanel.setPreferredSize(new java.awt.Dimension(149, 219));
			searchPanel.add(getMemberSearchText());
			searchPanel.add(getMemberSearchBtn());
			searchPanel.add(getMemberPictureLabel());
		}
		return searchPanel;
	}

	private JLabel getMemberPictureLabel() {
		if (memberPictureLabel == null) {
			memberPictureLabel = new JLabel();
			memberPictureLabel.setBorder(BorderFactory
					.createBevelBorder(BevelBorder.LOWERED));
			memberPictureLabel
					.setPreferredSize(new java.awt.Dimension(136, 152));
			memberPictureLabel.setFont(new java.awt.Font("Arial", 0, 12));
		}
		return memberPictureLabel;
	}

	private JTextField getMemberSearchText() {
		if (memberSearchText == null) {
			memberSearchText = new JTextField();
			memberSearchText.setPreferredSize(new java.awt.Dimension(130, 23));
			memberSearchText.setFont(new java.awt.Font("Arial", 0, 12));
		}
		return memberSearchText;
	}

	private JButton getMemberSearchBtn() {
		if (memberSearchBtn == null) {
			memberSearchBtn = new JButton();
			memberSearchBtn.setText("Search");
			memberSearchBtn.setPreferredSize(new java.awt.Dimension(83, 23));
			memberSearchBtn.setFont(new java.awt.Font("Arial", 0, 12));
			memberSearchBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					memberSearchBtnActionPerformed(evt);
				}
			});
		}
		return memberSearchBtn;
	}

	// Check that FundMaster Config exists
	private void checkFundMasterConfiguration() {
		if (config.getBaseDir() == null || config.getUrl() == null) {
			jLabelStatus
					.setText("Warning: Please Check the FundMaster Configuration!");
		}
	}

	private void memberSearchBtnActionPerformed(ActionEvent evt) {
		String searchId = getMemberSearchText().getText();
		if (!searchId.equals("")) {
			currentPensioner = queryAdapter.searchPensioner(searchId);

			if (currentPensioner != null) {
				ImageIcon icon = null;
				biometricsUtil.setCurrentPensioner(currentPensioner);
				if (currentPensioner.getPhotoUrl() != null) {
					URL imageUrl = null;
					try {
						imageUrl = new URL(currentPensioner.getPhotoUrl());
					} catch (MalformedURLException e) {
						LOG.error(e);
					}
					icon = new ImageIcon(imageUrl);
				} else {
					getMemberPictureLabel().setText("No Photo");
				}
				jLabelStatus.setText("Member Search Successful: "
						+ currentPensioner.getMemberName());
				getMemberPictureLabel().setIcon(icon);
			} else {
				JOptionPane
						.showMessageDialog(new JFrame(), "Member Not Found!");
			}
		}
	}

	private JButton getResetBtn() {
		if (resetBtn == null) {
			resetBtn = new JButton();
			resetBtn.setText("Reset");
			resetBtn.setMaximumSize(new java.awt.Dimension(160, 30));
			resetBtn.setMinimumSize(new java.awt.Dimension(160, 30));
			resetBtn.setPreferredSize(new java.awt.Dimension(160, 30));
			resetBtn.setFont(new java.awt.Font("Arial", 0, 12));
			resetBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					resetBtnActionPerformed(evt);
				}
			});
		}
		return resetBtn;
	}

	private void resetBtnActionPerformed(ActionEvent evt) {
		reset();
	}

	/***/
	private void reset() {
		currentPensioner = null;
		jLabelRegisterImage1.setText("");
		jLabelRegisterImage2.setText("");
		jLabelVerifyImage.setText("");
		getMemberSearchText().setText("");
		getMemberPictureLabel().setText("");
		jLabelStatus.setText("");
	}

}
