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
 $Id: FormTag.java,v 1.2 2002/11/19 20:37:43 gmurray Exp $ */

package com.sun.j2ee.blueprints.waf.view.taglibs.smart;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.BodyContent;

import java.io.IOException;
import java.util.*;

/**
 * A smart form.
 */
public class FormTag extends BodyTagSupport {

    Map validatedFields = new TreeMap();
    String name;
    String action;
    String method;
    String formHTML;

    public void putValidatedField(String fieldName, String fieldType) {
        validatedFields.put(fieldName, fieldType);
    }

    public void setName(String n) { name = n; }
    
    public void setAction(String a) { action = a; }
    
    public void setMethod(String m) { method = m; }

    public int doStartTag() throws JspTagException {
        return EVAL_BODY_BUFFERED;
    }

    public int doAfterBody() throws JspTagException { 
        BodyContent bc = getBodyContent();
        formHTML = bc.getString();
        bc.clearBody();
        return SKIP_BODY; 
    }

    public int doEndTag() throws JspTagException {
        try {
            StringBuffer html = new StringBuffer();
            StringBuffer javaScript = new StringBuffer();

            javaScript.append("<script language=\"JavaScript\">\n");
            javaScript.append("function validate_" + name +"() {\n");
            javaScript.append("    var ret = true;\n");
            for (Iterator it = validatedFields.keySet().iterator();
                 it.hasNext(); ) {
                String fieldName = (String) it.next();
                String fieldType = (String) validatedFields.get(fieldName);
                javaScript.append("    if (window.document.");
                javaScript.append(name + ".");
                javaScript.append(fieldName + ".");
                javaScript.append("value");
                javaScript.append(" ==");
                javaScript.append(" \"\"");
                javaScript.append(") {\n");
                javaScript.append("        alert(\"" + fieldName 
                                  + " is empty.\");\n");
                javaScript.append("        ret = false;\n");
                javaScript.append("    }\n");
            }
            javaScript.append("    return ret;\n");
            javaScript.append("}\n");
            javaScript.append("</script>\n");

            html.append("<form");
            html.append(" name=\"" + name +"\"");
            html.append(" action=\"" + action + "\"");
            html.append(" method=\"" + method + "\"");
            html.append(" onSubmit=\"return validate_" + name + "();\"");
            html.append(">\n");
            html.append(formHTML);
            html.append("</form>");
            pageContext.getOut().print(javaScript.toString());
            pageContext.getOut().print(html.toString());
            return EVAL_PAGE;
        }
        catch (IOException e) {
            throw new JspTagException("FormTag: " + e.getMessage());
        }
    }
}

