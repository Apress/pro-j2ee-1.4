<%-- Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 
 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 
 - Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
 
 - Redistribution in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in
   the documentation and/or other materials provided with the
   distribution.
 
 Neither the name of Sun Microsystems, Inc. or the names of
 contributors may be used to endorse or promote products derived
 from this software without specific prior written permission.
 
 This software is provided "AS IS," without a warranty of any
 kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES
 SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN
 OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR
 FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
 PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF
 LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE,
 EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 
 You acknowledge that Software is not designed, licensed or intended
 for use in the design, construction, operation or maintenance of
 any nuclear facility.
 $Id: template.jsp,v 1.9 2002/11/22 20:34:11 vijaysr Exp $ --%>
<%@ taglib prefix="template" uri="/WEB-INF/template.tld" %>
<html>
 <head>
  <title><template:insert parameter="title" /></title>
 </head>

 <body bgcolor="#FFFFFF">
  <table width="100%" height="100%" border="0">
    <tr height="15%">
      <td colspan="2">
        <template:insert parameter="banner" />
      </td>
    </tr>
    <tr height="10%">
      <td width="20%" valign="top">
      </td>
      <td width="80%" align="center" valign="top">
        <template:insert parameter="error" />
      </td>
    </tr>
    <tr height="85%">
      <td width="20%" valign="top">
        <template:insert parameter="menu" />
      </td>
      <td width="80%" align="left" valign="top">
        <br/>
        <br/>
        <template:insert parameter="body" />
      </td>
    </tr>
  </table>
 </body>
</html>