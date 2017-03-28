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
 $Id: CheckboxTag.java,v 1.2 2002/11/19 20:37:42 gmurray Exp $ */

package com.sun.j2ee.blueprints.waf.view.taglibs.smart;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.BodyContent;

import java.io.IOException;
import java.lang.StringBuffer;

/**
 * HTML 'input' tag. Use with NameTag and ValueTag.
 */
public class CheckboxTag extends BodyTagSupport {

    private String value;
    private String name;
    private boolean checked = true;

    
    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int doStartTag() throws JspTagException {
        return EVAL_BODY_BUFFERED;
    }

    public int doEndTag() throws JspTagException { 
        try {
            FormTag tag 
                = (FormTag) findAncestorWithClass(this, FormTag.class);

            StringBuffer html = new StringBuffer();
            html.append("<input type=\"checkbox\"");
            html.append(name != null ? (" name=\"" + name + "\"") : "");
            html.append(value != null ? (" value=\"" + value + "\"") : "");
            if (!checked) html.append(" checked");
            html.append(">");
            pageContext.getOut().print(html.toString());
            return EVAL_PAGE; 
        }
        catch (IOException e) {
            throw new JspTagException("CheckboxTag: " + e.getMessage());
        }
    }
}

