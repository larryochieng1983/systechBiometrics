
FDx SDK Pro for Windows
  Copyright (C) 1998-2014 SecuGen Corporation

Version : 3.7
Date : November, 2013

----------------------
Change from v3.54
----------------------
- Support SecuGen U20 device.
- Support WBF driver (Windows 7 or later).
- Remove samples for Visual Stdio 6.0
- Bug-fix#1: Memory leak of CreateFPObject()/DestroyFPMObject().
- Bug-fix#2: Naming Issues of exported functions.

Date : July, 2009

----------------------
Changes from v3.53
----------------------
- SDK License updated
- EnableCheckOfFingerLiveness() and its sample(DeviceTest.exe) added
- S/N for installation not required

Version : 3.53
Date : January, 2009

----------------------
Changes from v2.5
----------------------
- Support x64 bit platform including .NET 2.0
  (Runtime dlls, Library file, Sample program)
- Support ActiveX control
- Add new samples for activeX control: Visual Basic and HTML sample
- Support ISO 19794-2 template
- sgfplib.dll updated

-----------------------
System Requirement
-----------------------
SecuGen USB port devices based on FDU02, FDU03/SDU03, FDU04/SDU04, or U20:
- IBM-compatible PC 486 or later
- CD ROM Drive
- 1 USB 2.0 port 
- 64MB RAM
- 80MB available hard disk space
- Microsoft Windows 98 SE, Windows ME, Windows 2000/XP/2003, Windows Vista,
  Windows 7,8,8.1


---------------------------
Files & Direcotry Structure
---------------------------
Windows System directory
- sgfplib.dll (FDx SDK Pro main module)
- sgfpamx.dll (FDx SDK Pro algorithm module, MINEX Certified)

Bin\i386 directory
- sgfplib.dll
- sgfpamx.dll
- sgfplibx.ocx

Bin\x64 directory
- sgfplib.dll
- sgfpamx.dll
- sgfplibx.ocx

Inc directory
- sgfplib.h

Lib\i386 directory
- sgfplib.lib

Lib\x64 directory
- sgfplib.lib

Samples directory
- Image capture sample written in Visual Studio 2005
- Matching sample written in Visual Studio 2005
- Matching sample for ANSI-378 template written in  Visual Studio 2005
- Matching sample for ISO 19794-2 template in Visual Studio 2005

DotNet\Bin\i386 Directory
- SecuGen.FdxSdkPro.Windows.dll (FDx SDK Pro .NET x86 library)

DotNet\Bin\x64 Directory
- SecuGen.FdxSdkPro.Windows.dll (FDx SDK Pro .NET x64 library)

DotNet\Samples Directory
- Matching sample written in C# (Visual Studio 2005)
- Matching sample showing ANSI 378 template usage written in C# (Visual Studio 2005)
- Matching sample written in Visual basic .NET (Visual Studio 2005)






