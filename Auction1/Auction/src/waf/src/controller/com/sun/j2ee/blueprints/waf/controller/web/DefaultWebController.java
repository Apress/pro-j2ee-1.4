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
 $Id: DefaultWebController.java,v 1.2 2002/11/19 20:37:39 gmurray Exp $ */

package com.sun.j2ee.blueprints.waf.controller.web;

import java.util.Locale;
import java.util.Collection;

// J2EE imports
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import javax.ejb.RemoveException;

// WAF imports
import com.sun.j2ee.blueprints.util.tracer.Debug;
import com.sun.j2ee.blueprints.waf.util.JNDINames;
import com.sun.j2ee.blueprints.waf.controller.web.util.WebKeys;
import com.sun.j2ee.blueprints.waf.event.Event;
import com.sun.j2ee.blueprints.waf.event.EventResponse;
import com.sun.j2ee.blueprints.waf.controller.ejb.EJBControllerLocal;

import com.sun.j2ee.blueprints.waf.exceptions.GeneralFailureException;
import com.sun.j2ee.blueprints.waf.event.EventException;
import com.sun.j2ee.blueprints.waf.exceptions.AppException;

/**
 * This class is essentially just a proxy object that calls methods on the 
 * EJB tier using the com.sun.j2ee.blueprints.waf.controller.ejb.ShoppingClientControllerEJB 
 * object. All the methods that access the EJB are synchronized so
 * that concurrent requests do not happen to the stateful session bean.
 *
 */
public class DefaultWebController implements WebController {

    public DefaultWebController() {
    }

    /**
     * constructor for an HTTP client.
     * @param the ServletContext  object of the application
     */
    public void init(ServletContext context) {
           context.setAttribute(WebKeys.WEB_CONTROLLER, this);
    }
    
    /**
     * feeds the specified event to the state machine of the business logic. 
     *
     * @param ese is the current event 
     * @return a com.sun.j2ee.blueprints.waf.event.EventResponse object which can be extend to carry any payload. 
     * @exception com.sun.j2ee.blueprints.waf.event.EventException <description>
     * @exception com.sun.j2ee.blueprints.waf.exceptions.GeneralFailureException
     */
    public synchronized EventResponse handleEvent(Event ev, HttpSession session) 
  throws EventException {
            DefaultComponentManager cm = (DefaultComponentManager)session.getAttribute(WebKeys.COMPONENT_MANAGER);
            EJBControllerLocal controllerEJB =cm.getEJBController(session);
      return controllerEJB.processEvent(ev);
    }

     /** 
     * frees up all the resources associated with this controller and
     * destroys itself.
     */
    public synchronized void destroy(HttpSession session) {
  // call ejb remove on EJBClientController
        DefaultComponentManager cm = (DefaultComponentManager)session.getAttribute(WebKeys.COMPONENT_MANAGER);
        EJBControllerLocal controllerEJB =cm.getEJBController(session);
        try {
      controllerEJB.remove();
  } catch(RemoveException re){
      // ignore, after all its only a remove() call!
      Debug.print(re);
  }
    }
}

