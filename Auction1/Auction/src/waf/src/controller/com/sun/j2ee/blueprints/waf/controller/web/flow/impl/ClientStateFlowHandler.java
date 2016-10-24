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
 $Id: ClientStateFlowHandler.java,v 1.2 2002/11/19 20:37:41 gmurray Exp $ */

package com.sun.j2ee.blueprints.waf.controller.web.flow.impl;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Map;
import java.util.Iterator;
import java.util.HashMap;

// Apache Commons- Tag-Lib Imports
import org.apache.commons.codec.base64.Base64;

// J2EE imports 
import javax.servlet.http.HttpServletRequest;

// WAF imports
import com.sun.j2ee.blueprints.waf.controller.web.flow.FlowHandler;
import com.sun.j2ee.blueprints.waf.controller.web.flow.FlowHandlerException;

/**
 * This class de-serializes Base64 encoded parameters encoded into a web page using the
 * ClientCacheLinkTag.
 *
*/
public class ClientStateFlowHandler implements FlowHandler {

    public void doStart(HttpServletRequest request){
    }
    
    public String processFlow(HttpServletRequest request) 
        throws FlowHandlerException {
            
   
        String forwardScreen = request.getParameter("referring_screen");
        // de-serialize the request attributes.
        Map params = (Map)request.getParameterMap();
        HashMap newParams = new HashMap();
        String cacheId= request.getParameter("cacheId");
        if (!params.isEmpty()) {
            Iterator it = params.keySet().iterator();
            // put the request attributes stored in the session in the request
            while (it.hasNext()) {
                String key = (String)it.next();
                if (key.startsWith(cacheId + "_attribute_")) {
                    String[] values = (String[])params.get(key);
                    String valueString = values[0];
                    byte[] bytes  = Base64.decode(valueString.getBytes());
                    try {
                        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                        Object requestObject = requestObject = ois.readObject();
                        ois.close();
                        // put the de-serialized object back into the request
                        String requestObjectKey = key.substring((cacheId + "_attribute_").length(), key.length());
                        request.setAttribute(requestObjectKey, requestObject);
                    } catch (java.io.OptionalDataException ode) {
                        System.err.println("ClientCacheLinkFlowHandler caught: " + ode);
                    } catch (java.lang.ClassNotFoundException cnfe) {
                        System.err.println("ClientCacheLinkFlowHandler caught: " + cnfe);                  
                    } catch (java.io.IOException iox) {
                        System.err.println("ClientCacheLinkFlowHandler caught: " + iox);                  
                    }
                }
            }
        }
        return forwardScreen;
    }
    
    
    public void doEnd(HttpServletRequest request) {
    }

}

