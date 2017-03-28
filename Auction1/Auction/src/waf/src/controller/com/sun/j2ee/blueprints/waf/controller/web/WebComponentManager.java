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
 $Id: WebComponentManager.java,v 1.2 2002/11/19 20:37:39 gmurray Exp $ */

package com.sun.j2ee.blueprints.waf.controller.web;

import java.beans.Beans;

// J2EE Imports
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import javax.ejb.CreateException;
import javax.naming.InitialContext;

// WAF Imports
import com.sun.j2ee.blueprints.waf.controller.web.util.WebKeys;
import com.sun.j2ee.blueprints.servicelocator.web.ServiceLocator;
import com.sun.j2ee.blueprints.servicelocator.ServiceLocatorException;
import com.sun.j2ee.blueprints.waf.util.JNDINames;
import com.sun.j2ee.blueprints.waf.exceptions.GeneralFailureException;
import com.sun.j2ee.blueprints.waf.exceptions.AppException;
import com.sun.j2ee.blueprints.waf.controller.web.WebController;
import com.sun.j2ee.blueprints.waf.controller.web.util.WebKeys;

import com.sun.j2ee.blueprints.util.tracer.Debug;


/** 
 * This implmentation class of the ComponentManager provides
 * access to services in the web tier and ejb tier. 
 *  
 */
public class WebComponentManager implements ComponentManager, java.io.Serializable {
    
    protected ServiceLocator sl = null;
    
    public WebComponentManager() {
    }

    /**
     * Web Only Applications will do there model updates in the 
     * individual actions themselves thuse we do not need 
     * another set of classes for command processing.
     */
    public WebController getWebController(HttpSession session) {
         return null;
    }

    /**
     *
     * Initialize
     */
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();  
        session.setAttribute(WebKeys.COMPONENT_MANAGER, this);
    }

    /**
     *
     * Destroy whatever needs destroying
     *
     */
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
    }
}


