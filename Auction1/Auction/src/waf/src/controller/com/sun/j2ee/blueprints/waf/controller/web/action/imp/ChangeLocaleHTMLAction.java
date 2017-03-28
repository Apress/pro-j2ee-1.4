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
 $Id: ChangeLocaleHTMLAction.java,v 1.2 2002/11/19 20:37:40 gmurray Exp $ */

package com.sun.j2ee.blueprints.waf.controller.web.action.impl;


import java.io.IOException;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

// waf imports
import com.sun.j2ee.blueprints.waf.util.I18nUtil;
import com.sun.j2ee.blueprints.waf.event.Event;
import com.sun.j2ee.blueprints.waf.event.impl.ChangeLocaleEvent;
import com.sun.j2ee.blueprints.waf.controller.web.action.HTMLActionSupport;
import com.sun.j2ee.blueprints.waf.controller.web.action.HTMLActionException;


/**
 * Implementation of HTMLAction that processes a
 * user change in language.
 *
 */

public final class ChangeLocaleHTMLAction extends HTMLActionSupport {


    public static final String LOCALE = "com.sun.j2ee.blueprints.waf.LOCALE";
    
    public Event perform(HttpServletRequest request)
  throws HTMLActionException {
  // Extract attributes we will need
        String localeString = request.getParameter("locale");
  Locale locale = I18nUtil.getLocaleFromString(localeString);
  //MessageResources messages = getResources();
  HttpSession session = request.getSession();
        
  if (locale != null) {
            session.setAttribute(LOCALE,locale);
            return new ChangeLocaleEvent(locale);
        } else {
            throw new HTMLActionException("ChangeLocaleAction: Unable to change language to " + localeString);
        }
    }
}

