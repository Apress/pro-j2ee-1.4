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
 $Id: CacheTag.java,v 1.2 2002/11/19 20:37:42 gmurray Exp $ */

package com.sun.j2ee.blueprints.waf.view.taglibs.smart;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.http.HttpServletRequest;

/**
 * A naive caching tag.
 */
public class CacheTag extends BodyTagSupport {

    String scope;
    String name;
    long duration;

    Entry entry;
    String key;

    public void setScope(String s) { scope = s; }

    public void setName(String n) { name = n; }

    public void setDuration(long d) { duration = d; }

    public int doStartTag() throws JspTagException {
        HttpServletRequest req = 
            ((HttpServletRequest) pageContext.getRequest());

        key = req.getRequestURL().toString() 
            + '#' + name
            + '?' + req.getQueryString();

        if ("context".equals(scope)) {
            entry = (Entry) pageContext.getServletContext().getAttribute(key);
        }
        else if ("session".equals(scope)) {
            entry = (Entry) pageContext.getSession().getAttribute(key);
        }
        else if ("request".equals(scope)) {
            entry = (Entry) pageContext.getRequest().getAttribute(key);
        }
        else if ("page".equals(scope)) {
            entry = (Entry) pageContext.getAttribute(key);
        }

        if (entry != null && entry.isExpired()) {
            entry = null;
        }

        return (entry == null) ? EVAL_BODY_BUFFERED : SKIP_BODY;
    }

    public int doEndTag() throws JspTagException {
        if (entry == null) {
            BodyContent bc = getBodyContent();
            if (bc != null) {
                String content = bc.getString();
                entry = new Entry(content, duration);
                
                if ("context".equals(scope)) {
                    pageContext.getServletContext().setAttribute(key, entry);
                }
                else if ("session".equals(scope)) {
                    pageContext.getSession().setAttribute(key, entry);
                }
                else if ("request".equals(scope)) {
                    pageContext.getRequest().setAttribute(key, entry);
                }
                else if ("page".equals(scope)) {
                    pageContext.setAttribute(key, entry);
                }
                
                try {
                    JspWriter out = bc.getEnclosingWriter();
                    out.print(content);
                }
                catch (IOException ioe) {
                    System.err.println("Problems with writing...");
                }
            }
        }
        else {
            try {
                JspWriter out = pageContext.getOut();
                out.print(entry.getContent());
            }
            catch (IOException ioe) {
                System.err.println("Problems with writing...");
            }
        }

        return EVAL_PAGE;
    }

    /*
     * An entry in the cache.
     */
    class Entry {

        String content;
        long timestamp;
        long duration;

        public Entry(String content, long duration) {
            this.content = content;
            timestamp = System.currentTimeMillis();
            this.duration = duration;
        }

        public String getContent() { return content; }

        public boolean isExpired() {
            long currentTime = System.currentTimeMillis();
            return ((currentTime - timestamp) > duration);
        }
    }
}
