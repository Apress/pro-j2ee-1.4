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
 $Id: MainServlet.java,v 1.2 2002/11/19 20:37:39 gmurray Exp $ */

package com.sun.j2ee.blueprints.waf.controller.web;

import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;

// J2EE Imports
import javax.naming.NamingException;

// WAF imports
import com.sun.j2ee.blueprints.waf.util.I18nUtil;
import com.sun.j2ee.blueprints.waf.exceptions.GeneralFailureException;
import com.sun.j2ee.blueprints.util.tracer.Debug;
import com.sun.j2ee.blueprints.waf.controller.web.URLMappingsXmlDAO;
import com.sun.j2ee.blueprints.waf.controller.web.RequestProcessor;
import com.sun.j2ee.blueprints.waf.controller.web.util.WebKeys;
import com.sun.j2ee.blueprints.waf.util.JNDINames;
import com.sun.j2ee.blueprints.waf.controller.web.flow.FlowHandlerException;
import com.sun.j2ee.blueprints.waf.controller.web.flow.ScreenFlowManager;
import com.sun.j2ee.blueprints.waf.controller.web.WebController;

public class MainServlet extends HttpServlet {
    
    private ServletContext context;
    private HashMap urlMappings;
    private HashMap eventMappings;
    private Locale defaultLocale = null;

    private RequestProcessor requestProcessor;
    
    
    public void init(ServletConfig config) throws ServletException {
        String defaultLocaleString = config.getInitParameter("default_locale");
        defaultLocale = I18nUtil.getLocaleFromString(defaultLocaleString);
        this.context = config.getServletContext();
        String requestMappingsURL = null;
        try {
            requestMappingsURL = context.getResource("/WEB-INF/mappings.xml").toString();
        } catch (java.net.MalformedURLException ex) {
            System.err.println("MainServlet: initializing ScreenFlowManager malformed URL exception: " + ex);
        }
       urlMappings = URLMappingsXmlDAO.loadRequestMappings(requestMappingsURL);
       context.setAttribute(WebKeys.URL_MAPPINGS, urlMappings);
       eventMappings = URLMappingsXmlDAO.loadEventMappings(requestMappingsURL);
       context.setAttribute(WebKeys.EVENT_MAPPINGS, eventMappings);
       getScreenFlowManager();
       getRequestProcessor();
    }
   
     public  void doGet(HttpServletRequest request, HttpServletResponse  response)
        throws IOException, ServletException {
        doProcess(request, response);
    }

    public  void doPost(HttpServletRequest request, HttpServletResponse  response)
        throws IOException, ServletException {
        doProcess(request, response);
        
    }
    
    private void doProcess(HttpServletRequest request, HttpServletResponse response)
                   throws IOException, ServletException {
        // set the locale of the user to default if not set
        if (request.getSession().getAttribute(WebKeys.LOCALE) == null) {
            request.getSession().setAttribute(WebKeys.LOCALE, defaultLocale);
        }
        try {
                 getRequestProcessor().processRequest(request);
                 getScreenFlowManager().forwardToNextScreen(request,response);
        } catch (Throwable ex) {
            String className = ex.getClass().getName();
            String nextScreen = getScreenFlowManager().getExceptionScreen(ex);
            // put the exception in the request
            request.setAttribute("javax.servlet.jsp.jspException", ex);
            if (nextScreen == null) {
                // send to general error screen
                //ex.printStackTrace();
                throw new ServletException("MainServlet: unknown exception: " + className);
            }
            context.getRequestDispatcher(nextScreen).forward(request, response);
      }

      

    }

    private RequestProcessor getRequestProcessor() {
         RequestProcessor rp = (RequestProcessor)context.getAttribute(WebKeys.REQUEST_PROCESSOR);
         if ( rp == null ) {
             rp = new RequestProcessor();
             rp.init(context); 
             context.setAttribute(WebKeys.REQUEST_PROCESSOR, rp);
        }
       return rp;
    }

    private ScreenFlowManager getScreenFlowManager() {
            ScreenFlowManager screenManager = (ScreenFlowManager)context.getAttribute(WebKeys.SCREEN_FLOW_MANAGER);
            if (screenManager == null ) {
                screenManager = new ScreenFlowManager();
                screenManager.init(context);
                context.setAttribute(WebKeys.SCREEN_FLOW_MANAGER, screenManager);
             }
        return screenManager;
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
}

