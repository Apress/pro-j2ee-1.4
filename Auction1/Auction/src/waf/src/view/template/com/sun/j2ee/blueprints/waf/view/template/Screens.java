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
 $Id: Screens.java,v 1.2 2002/11/19 20:37:44 gmurray Exp $ */

package com.sun.j2ee.blueprints.waf.view.template;

import java.util.HashMap;

public class Screens implements java.io.Serializable {

    private HashMap screenMap;
    private HashMap templateMap;
    private String defaultTemplate;

    public Screens (String defaultTemplate) {
        screenMap = new HashMap();
        templateMap = new HashMap();
        this.defaultTemplate = defaultTemplate;
    }

    public String getDefaultTemplate() {
        return defaultTemplate;
    }

    public void addScreen(String screenName, Screen screen) {
        if (screenMap.containsKey(screenName)) {
            screenMap.remove(screenName);
        }
        screenMap.put(screenName, screen);
    }

    public void addTemplate(String templateName, String templateURL) {
        if (templateMap.containsKey(templateName)) {
            templateMap.remove(templateName);
        }
        templateMap.put(templateName, templateURL);
    }

    public Screen getScreen(String screenName) {

        if (screenMap.containsKey(screenName)) {
            return (Screen)screenMap.get(screenName);
        } else {
            System.err.println("Screens Error: Screen " + screenName + " not defined.");
           return null;
        }
    }
    
    public boolean containsScreen(String screenName) {
        return screenMap.containsKey(screenName);
    }
    
    public boolean containsTemplate(String templateName) {
        return templateMap.containsKey(templateName);
    }

    public String getTemplate(String screenName) {
        if (screenMap.containsKey(screenName)) {
            String templateName = ((Screen)screenMap.get(screenName)).getTemplate();
            if ((templateName != null) && templateMap.containsKey(templateName)) {
                    return (String)templateMap.get(templateName);
            } else {
                // return the default if template not defined for screen
                return defaultTemplate;
            }
        } else {
                System.err.println("Screens:getTemplate() error: Screen " + screenName + " not defined.");
                return null;
        }
    }
    
    public String toString() {
        return "[Screens: defaultTemplate=" + defaultTemplate + ", " +
                    "screenMap=" + screenMap +
                    "templateMap=" + templateMap + "]";
    }
}
