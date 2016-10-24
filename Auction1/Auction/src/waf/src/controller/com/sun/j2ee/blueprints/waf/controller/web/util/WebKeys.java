/* Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 
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
 $Id: WebKeys.java,v 1.2 2002/11/19 20:37:41 gmurray Exp $ */

package com.sun.j2ee.blueprints.waf.controller.web.util;

/**
 * This interface contains all the keys that are used to 
 * store data in the different scopes of web-tier. These 
 * values are the same as those used in the JSP 
 * pages (useBean tags).
 */
public class WebKeys {
    
    protected WebKeys() {} // prevent instanciation

    public static final String COMPONENT_MANAGER = "com.sun.j2ee.blueprints.waf.COMPONENT_MANAGER";
    public static final String SCREEN_FLOW_MANAGER = "com.sun.j2ee.blueprints.waf.SCREEN_FLOW_MANAGER";
    public static final String REQUEST_PROCESSOR = "com.sun.j2ee.blueprints.waf.REQUEST_PROCESSOR";
    public static final String CURRENT_SCREEN = "com.sun.j2ee.blueprints.waf.CURRENT_SCREEN";
    public static final String PREVIOUS_SCREEN = "com.sun.j2ee.blueprints.waf.PREVIOUS_SCREEN";
    public static final String CURRENT_URL = "com.sun.j2ee.blueprints.waf.CURRENT_URL";
    public static final String PREVIOUS_URL = "com.sun.j2ee.blueprints.waf.PREVIOUS_URL";
    public static final String PREVIOUS_REQUEST_PARAMETERS = "com.sun.j2ee.blueprints.waf.PREVIOUS_REQUEST_PARAMETERS";
    public static final String PREVIOUS_REQUEST_ATTRIBUTES = "com.sun.j2ee.blueprints.waf.PREVIOUS_REQUEST_ATTRIBUTES";
    public static final String URL_MAPPINGS = "com.sun.j2ee.blueprints.waf.URL_MAPPINGS";
    public static final String EVENT_MAPPINGS = "com.sun.j2ee.blueprints.waf.EVENT_MAPPINGS";
    public static final String MISSING_FORM_DATA = "com.sun.j2ee.blueprints.waf.MISSING_FORM_DATA";
    public static final String SERVER_TYPE = "com.sun.j2ee.blueprints.waf.SERVER_TYPE";
    public static final String LOCALE = "com.sun.j2ee.blueprints.waf.LOCALE";
    public static final String WEB_CONTROLLER = "com.sun.j2ee.blueprints.waf.WEB_CLIENT_CONTROLLER";
    public static final String EJB_CONTROLLER = "com.sun.j2ee.blueprints.waf.EJB_CLIENT_CONTROLLER ";
}

