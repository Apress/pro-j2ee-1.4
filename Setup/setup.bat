@echo off

REM
REM Copyright 2002 Sun Microsystems, Inc. All rights reserved.
REM 
REM Redistribution and use in source and binary forms, with or without
REM modification, are permitted provided that the following conditions
REM are met:
REM 
REM - Redistributions of source code must retain the above copyright
REM   notice, this list of conditions and the following disclaimer.
REM 
REM - Redistribution in binary form must reproduce the above copyright
REM   notice, this list of conditions and the following disclaimer in
REM   the documentation and/or other materials provided with the
REM   distribution.
REM 
REM Neither the name of Sun Microsystems, Inc. or the names of
REM contributors may be used to endorse or promote products derived
REM from this software without specific prior written permission.
REM 
REM This software is provided "AS IS," without a warranty of any
REM kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
REM WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
REM FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
REM EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES
REM SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
REM DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN
REM OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR
REM FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
REM PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF
REM LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE,
REM EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
REM 
REM You acknowledge that Software is not designed, licensed or intended
REM for use in the design, construction, operation or maintenance of
REM any nuclear facility.
REM

%J2EE_HOME%\bin\asant -Dj2ee.home=%J2EE_HOME% -buildfile setup.xml %1 %2 %3 %4
