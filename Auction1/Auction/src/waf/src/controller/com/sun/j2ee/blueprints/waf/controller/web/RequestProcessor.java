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
 $Id: RequestProcessor.java,v 1.2 2002/11/19 20:37:39 gmurray Exp $ */

package com.sun.j2ee.blueprints.waf.controller.web;

import java.util.Collection;
import java.util.HashMap;

// J2ee Imports
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import com.sun.j2ee.blueprints.waf.util.JNDINames;

// BluePrints/WAF imports
import com.sun.j2ee.blueprints.util.tracer.Debug;
import com.sun.j2ee.blueprints.waf.controller.web.util.WebKeys;
import com.sun.j2ee.blueprints.waf.event.Event;
import com.sun.j2ee.blueprints.waf.event.EventException;
import com.sun.j2ee.blueprints.waf.event.EventResponse;
import com.sun.j2ee.blueprints.waf.controller.web.action.HTMLAction;
import com.sun.j2ee.blueprints.waf.controller.web.action.HTMLActionException;
import com.sun.j2ee.blueprints.waf.controller.web.ComponentManager;

/** 
 * This is the web tier controller for the sample application.
 *
 * This class is responsible for processing all requests received from
 * the Main.jsp and generating necessary events to modify data which
 * are sent to the WebController. 
 * 
 */
public class RequestProcessor implements java.io.Serializable {

    private HashMap urlMappings;
    private HashMap eventMappings;

    public RequestProcessor() {}


    public void init(ServletContext context) {
        urlMappings = (HashMap)context.getAttribute(WebKeys.URL_MAPPINGS);
        eventMappings = (HashMap)context.getAttribute(WebKeys.EVENT_MAPPINGS);
    }
    
    /**
     * The UrlMapping object contains information that will match
     * a url to a mapping object that contains information about
     * the current screen, the HTMLAction that is needed to
     * process a request, and the HTMLAction that is needed
     * to insure that the propper screen is displayed.
    */

    private URLMapping getURLMapping(String urlPattern) {
        if ((urlMappings != null) && urlMappings.containsKey(urlPattern)) {
            return (URLMapping)urlMappings.get(urlPattern);
        } else {
            return null;
        }
    }

    /**
     * The EventMapping object contains information that will match
     * a event class name to an EJBActionClass.
     *
    */

    private EventMapping getEventMapping(Event eventClass) {
        // get the fully qualified name of the event class
        String eventClassName = eventClass.getClass().getName();
        if ((eventMappings != null) && eventMappings.containsKey(eventClassName)) {
            return (EventMapping)eventMappings.get(eventClassName);
        } else {
            return null;
        }
    }

    /**
    * This method is the core of the RequestProcessor. It receives all requests
    *  and generates the necessary events.
    */
    public void processRequest(HttpServletRequest request) throws HTMLActionException, EventException, ServletException {
        Event ev = null;
        String fullURL = request.getRequestURI();
        // get the screen name
        String selectedURL = null;            
        int lastPathSeparator = fullURL.lastIndexOf("/") + 1;
        if (lastPathSeparator != -1) {
            selectedURL = fullURL.substring(lastPathSeparator, fullURL.length());
        }
        URLMapping urlMapping = getURLMapping(selectedURL);
        HTMLAction action = getAction(urlMapping);
        if (action != null) {
            action.setServletContext(request.getSession().getServletContext());
            action.doStart(request);
            ev = action.perform(request);
            EventResponse eventResponse = null;
            if (ev != null) {
               // set the ejb action class name on the event
                EventMapping eventMapping = getEventMapping(ev);
                if (eventMapping != null) {
                        ev.setEJBActionClassName(eventMapping.getEJBActionClassName());
                }
               ComponentManager sl = (ComponentManager)request.getSession().getAttribute(WebKeys.COMPONENT_MANAGER);
               WebController wcc =  sl.getWebController(request.getSession());
         eventResponse  = wcc.handleEvent(ev, request.getSession());
           }
           action.doEnd(request, eventResponse);
        }
    }
    
    /**
     * This method load the necessary HTMLAction class necessary to process a the
     * request for the specified URL.
     */

    private HTMLAction getAction(URLMapping urlMapping) {
        HTMLAction handler = null;
        String actionClassString = null;
        if (urlMapping != null) {
            actionClassString = urlMapping.getWebAction();
            if (urlMapping.isAction()) {
                try {
                    handler = (HTMLAction)getClass().getClassLoader().loadClass(actionClassString).newInstance();
                } catch (Exception ex) {
                    System.err.println("RequestProcessor caught loading action: " + ex);          
                }
            }
        }
        return handler;
    }
}

