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
 $Id: ClientStateTag.java,v 1.2 2002/11/19 20:37:43 gmurray Exp $ */

package com.sun.j2ee.blueprints.waf.view.taglibs.smart;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.Map;
import java.util.Iterator;
import java.util.Enumeration;
import java.util.HashMap;

// Apache Commons- Tag-Lib Imports
import org.apache.commons.codec.base64.Base64;

// J2EE Imports
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.http.HttpServletRequest;

// WAF imports
import com.sun.j2ee.blueprints.waf.controller.web.util.WebKeys;

/**
 * This tag caches state in the page and provides a button or
 * image link within a form with the current page parameters
 * and the page request attributes by encoding them as hidden
 * form variables that are serialized using Base 64 Encoded Strings.
 *
 * This tag when used along with a front controller such as the
 * one provided in the WAF can utilize a flow handler to forward
 * a request page to a page with the same attributes it recieved
 * when the original request was made.
 */
public class ClientStateTag extends BodyTagSupport {

    private String altText = "";
    private String buttonText;
    private String imageURL;
    private String cacheId;
    private String targetURL;
    private boolean encodeRequestAttributes = true;
    private boolean encodeRequestParameters = true;
    private Class serializableClass = null;
    private HashMap parameters;

    public void setId(String cacheId) {
        this.cacheId = cacheId;
    }
    
    public void setAlt(String altText) {
        this.altText = altText;
    }
    
    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }
    
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    
    public void setTargetURL(String targetURL) {
        this.targetURL = targetURL;
    }

    public void setEncodeRequestAttributes(boolean encodeRequestAttributes) {
        this.encodeRequestAttributes = encodeRequestAttributes;
    }
    
    public void setEncodeRequestParameters(boolean encodeRequestParameters) {
        this.encodeRequestAttributes = encodeRequestParameters;
    }

    public int doStartTag() throws JspTagException {
        if (imageURL == null && buttonText == null) {
            throw new JspTagException("ClientStateTag error: either an " +
                                                        "imageURL or buttonText attribute must be specified.");
        }
        return EVAL_BODY_BUFFERED;
    }

    public int doEndTag() throws JspTagException {
        HttpServletRequest request = 
            ((HttpServletRequest) pageContext.getRequest());   
        StringBuffer buffer = new StringBuffer();
        buffer.append("<form method=\"POST\" action=\"" + targetURL + "\">");
        // insert any parameters that may have been added via sub tags
        if (parameters != null) {
            Iterator it = parameters.keySet().iterator();       
            // put the request attributes stored in the session in the request
            while (it.hasNext()) {  
                String key = (String)it.next();
                String value = (String)parameters.get(key);
                buffer.append(" <input type=\"hidden\" name=\"" +               
                                          key + "\" value=\"" + value + "\" />");
            }
        }
        String fullURL = request.getRequestURI();
        // find the url that sent us this page
        String targetURL = null;
        int lastPathSeparator = fullURL.lastIndexOf("/") + 1;
        if (lastPathSeparator != -1) {
           targetURL = fullURL.substring(lastPathSeparator, fullURL.length());
        }
        buffer.append(" <input type=\"hidden\" name=\"referring_URL\"" +
                               "value=\"" + targetURL +
                               "\">");
        String referringScreen =
                (String)request.getSession().getAttribute(WebKeys.PREVIOUS_SCREEN);
        buffer.append(" <input type=\"hidden\" name=\"referring_screen\"" +
                               "value=\"" + referringScreen +
                               "\">");
        buffer.append(" <input type=\"hidden\" name=\"cacheId\"" +
                               "value=\"" + cacheId +  "\">");
        // check the request for previous parameters
        Map params = (Map)request.getParameterMap();
        if (!params.isEmpty() && encodeRequestParameters) {
            Iterator it = params.keySet().iterator();       
            // copy in the request parameters stored
            while (it.hasNext()) {  
                String key = (String)it.next();
                if (!key.startsWith(cacheId)) {
                    String[] values = (String[])params.get(key);
                    String valueString = values[0];
                    buffer.append(" <input type=\"hidden\" name=\"" +                 
                                             key + "\" value=\"" +
                                            valueString  + 
                                            "\" />");
                } 
            }
        }
        /**
          *  Now serialize the request attributes into the page (only sealizable objects are going
          *  to be processed).
          */
        if (encodeRequestAttributes) {
                // put the request attributes into tattributres
                Enumeration enum = request.getAttributeNames();
                while (enum.hasMoreElements()) {
                     String key = (String)enum.nextElement();
                      // check if we have already serialized the items
                      // also don't serialize javax items because
                     if (!key.startsWith(cacheId) &&
                         !key.startsWith("javax.servlet")) {
                         Object value = request.getAttribute(key);
                         if (serializableClass == null) {
                            try {
                                 serializableClass = getClass().forName("java.io.Serializable");
                             } catch (java.lang.ClassNotFoundException cnf) {
                                 System.err.println("ClientStateTag caught: " + cnf);
                             }
                         }
                         // check if seralizable
                         if (serializableClass.isAssignableFrom(value.getClass())) {
                             try {
                                 ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                 ObjectOutput out = new ObjectOutputStream(bos);
                                 out.writeObject(value);
                                 out.close();
                                 buffer.append(" <input type=\"hidden\" name=\"" + cacheId +                
                                        "_attribute_" + key + "\" value=\"" +
                                       new String(Base64.encode(bos.toByteArray()), "ISO-8859-1")  + "\" />");
                         } catch (java.io.IOException iox) {
                                 System.err.println("ClientStateTag caught: " + iox);
                         }
                     } else {
                         System.out.println(key + " not to Serializeable");
                     }
                 }
                     
            } 
        }// end get attributes
        // now put the link in
        if (imageURL != null) {
            buffer.append(" <input alt=\"" + altText+ "\" type=\"image\" " + 
                           "src=\"" + imageURL + "\"/>");
        } else {
            buffer.append(" <input alt=\"" + altText + "\"  type=\"submit\" " + 
                           "value=\"" + buttonText + "\"/>");
        }
        buffer.append("</form>");
        // write the output to the output stream
        try {
                JspWriter out = pageContext.getOut();
                out.print(buffer.toString());
        } catch (IOException ioe) {
            System.err.println("ClientStateTag: Problems with writing...");
        }
        parameters = null;
        return EVAL_PAGE;
    }
    
    public void putParameter(String key, String value) {
        if (parameters == null) parameters = new HashMap();
        parameters.put(key,value);
    }
}
