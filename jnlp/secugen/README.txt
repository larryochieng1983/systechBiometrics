

								INSTALLATION INTRUCTIONS
===============================================================================================================
1.Make sure your Windows PC XP/Vista/Windows7 has JRE version 7 or later installed

2. Unpack the application zip to your prefered destination folder.
3. Run setup.exe in FDx SDK Pro for Windows v3.7 folder to install the necessary Windows drivers for the Secugen
	Fingerprint Reader device.
4. Install the Secugen native files (dll's) as follows:
	 Windows 7 32bit: Copy jnifplib\win32\jnisgfplib.dll to C:\windows\system32
  	 Windows 7 64bit:	Copy jnifplib\win32\jnisgfplib.dll to C:\Windows\SysWOW64
                  	Copy jnifplib\x64\jnisgfplib.dll to C:\Windows\system32
5. Edit the "codebase" attribute of the launch.jnlp to reflect the directory location of the launch.jnlp file, e.g. if
launch.jnlp's path is "C:/systechBiometrics-0.0.1-SNAPSHOT/launch.jnlp" then the value of the codebase attribute would be
"file:///C:/systechBiometrics-0.0.1-SNAPSHOT"

6.Launch the application via Java Webstart, after that application has been successfully downloaded a system tray icon is 
available to show/hide the application main window.