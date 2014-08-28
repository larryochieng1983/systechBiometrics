										
										systechBiometrics
=================

Fingerprint biometrics for Systech's FundMaster

Setting up Secugen SDK

Installation
============
1. Install FDx SDK Pro for Windows v3.54 
2. Install JDK v1.6.0_45 or later
3. Install the FDx SDK Pro for Java runtime files
   Windows 7 32bit: Copy jnifplib\win32\jnisgfplib.dll to C:\windows\system32
   Windows 7 64bit:	Copy jnifplib\win32\jnisgfplib.dll to C:\Windows\SysWOW64
                  	Copy jnifplib\x64\jnisgfplib.dll to C:\Windows\system32

To setup the dev environment you need to install the secugen java implementation:
After checking out the repository run
	mvn install:install-file -Dfile=jnlp/secugen/FDxSDKPro.jar -DgroupId=com.secugen -DartifactId=fdxsdkpro -Dpackaging=jar -Dversion=1.0.0



Some maven commands

To deploy locally:
			1. Run mvn clean install -Pwebstart,local
			
			In the "installer" directory there is a zip file containing the application, extract and look for
			"launch.jnlp". Run it using JavaWebstart. The app launches on your Windows System Tray.
