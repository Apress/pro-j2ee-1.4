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
 $Id: ConfigFileSignOnDAO.java,v 1.3 2002/11/21 21:41:55 inder Exp $ */

package com.sun.j2ee.blueprints.signon.web;

import org.xml.sax.InputSource;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.SAXException;


// jaxp 1.0.1 imports
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * This class provides the data bindings for the screendefinitions.xml
 * and the requestmappings.xml file.
 * The data obtained is maintained by the ScreenFlowManager
 */

public class ConfigFileSignOnDAO  {

    // xml tag constants
    public static final String SIGNON_FORM_LOGIN_PAGE = "signon-form-login-page";
    public static final String SIGNON_FORM_ERROR_PAGE = "signon-form-error-page";
    public static final String SECURITY_CONSTRAINT = "security-constraint";
    public static final String WEB_RESOURCE_COLLECTION = "web-resource-collection";
    public static final String WEB_RESOURCE_NAME = "web-resource-name";
    public static final String URL_PATTERN = "url-pattern";
    public static final String AUTH_CONSTRAINT = "auth-constraint";
    public static final String ROLE_NAME = "role-name";


    private String signOnLoginPage = null;
    private String signOnErrorPage = null;
    private HashMap protectedResources = null;

    public ConfigFileSignOnDAO (URL configURL) {
        Element root = loadDocument (configURL);
        protectedResources = getProtectedResources(root);
    }

    public String getSignOnPage() {
        return signOnLoginPage;
    }

    public String getSignOnErrorPage() {
        return signOnErrorPage;
    }

    public HashMap getProtectedResources() {
        return protectedResources;
    }

    private  Element loadDocument(URL url) {
        Document doc = null;
        try {
            InputSource xmlInp = new InputSource(url.openStream());

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = docBuilderFactory.newDocumentBuilder();
            doc = parser.parse(xmlInp);
            Element root = doc.getDocumentElement();
            root.normalize();
            return root;
        } catch (SAXParseException err) {
            System.err.println ("ConfigFileSignOnDAO  ** Parsing error" + ", line " +
                        err.getLineNumber () + ", uri " + err.getSystemId ());
            System.err.println("ConfigFileSignOnDAO  error: " + err.getMessage ());
        } catch (SAXException e) {
            System.err.println("ConfigFileSignOnDAO  error: " + e);
        } catch (java.net.MalformedURLException mfx) {
            System.err.println("ConfigFileSignOnDAO  error: " + mfx);
        } catch (java.io.IOException e) {
            System.err.println("ConfigFileSignOnDAO  error: " + e);
        } catch (Exception pce) {
            System.err.println("ConfigFileSignOnDAO  error: " + pce);
        }
        return null;
    }

    private HashMap getProtectedResources(Element root) {
        HashMap resources = new HashMap();
        // get the signon page and signon error page
        signOnLoginPage = getTagValue(root, SIGNON_FORM_LOGIN_PAGE ).trim();
        signOnErrorPage = getTagValue(root, SIGNON_FORM_ERROR_PAGE ).trim();

        // get protected pages //
        NodeList outterList = root.getElementsByTagName(SECURITY_CONSTRAINT);
        for (int outterLoop = 0; outterLoop < outterList.getLength(); outterLoop++) {
            Element element = (Element)outterList.item(outterLoop);
            // get  roles that can see this page
            ArrayList roles = new ArrayList();
            NodeList roleList = element.getElementsByTagName(AUTH_CONSTRAINT);
            for (int roleLoop = 0; roleLoop < roleList.getLength(); roleLoop++) {
                        Node  roleNode = roleList.item(roleLoop);
                        String roleName = getSubTagValue(roleNode, ROLE_NAME);
                        if ((roleName != null) && !roleName.equals("")) roles.add(roleName);
            }

            NodeList list = element.getElementsByTagName(WEB_RESOURCE_COLLECTION);
            for (int loop = 0; loop < list.getLength(); loop++) {
                Node node = list.item(loop);
                if (node != null) {
                    String resourceName = getSubTagValue(node, WEB_RESOURCE_NAME);
                    String urlPattern = getSubTagValue(node, URL_PATTERN);
                    ProtectedResource resource = new ProtectedResource(resourceName, urlPattern, roles);
                    if (!resources.containsKey(resourceName)) {
                         resources.put(resourceName, resource);
                    } else {
                        System.err.println("*** Non Fatal errror: Protected Resource " + resourceName +
                                       " defined more than once in screen definitions file");
                    }
                }
            }
        }
        return resources;
    }
    private String getSubTagAttribute(Element root, String tagName, String subTagName, String attribute) {
        String returnString = "";
        NodeList list = root.getElementsByTagName(tagName);
        for (int loop = 0; loop < list.getLength(); loop++) {
            Node node = list.item(loop);
            if (node != null) {
                NodeList  children = node.getChildNodes();
                for (int innerLoop =0; innerLoop < children.getLength(); innerLoop++) {
                    Node  child = children.item(innerLoop);
                    if ((child != null) && (child.getNodeName() != null) && child.getNodeName().equals(subTagName) ) {
                        if (child instanceof Element) {
                            return ((Element)child).getAttribute(attribute);
                        }
                    }
                } // end inner loop
            }
        }
        return returnString;
    }

    private String getSubTagValue(Node node, String subTagName) {
        String returnString = "";
        if (node != null) {
            NodeList  children = node.getChildNodes();
            for (int innerLoop =0; innerLoop < children.getLength(); innerLoop++) {
                Node  child = children.item(innerLoop);
                if ((child != null) && (child.getNodeName() != null) && child.getNodeName().equals(subTagName) ) {
                    Node grandChild = child.getFirstChild();
                    if (grandChild.getNodeValue() != null) return grandChild.getNodeValue();
                }
            } // end inner loop
        }
        return returnString;
    }

    private String getSubTagValue(Element root, String tagName, String subTagName) {
        String returnString = "";
        NodeList list = root.getElementsByTagName(tagName);
        for (int loop = 0; loop < list.getLength(); loop++) {
            Node node = list.item(loop);
            if (node != null) {
                NodeList  children = node.getChildNodes();
                for (int innerLoop =0; innerLoop < children.getLength(); innerLoop++) {
                    Node  child = children.item(innerLoop);
                    if ((child != null) && (child.getNodeName() != null) && child.getNodeName().equals(subTagName) ) {
                        Node grandChild = child.getFirstChild();
                        if (grandChild.getNodeValue() != null) return grandChild.getNodeValue();
                    }
                } // end inner loop
            }
        }
        return returnString;
    }

    private String getTagValue(Element root, String tagName) {
        String returnString = "";
        NodeList list = root.getElementsByTagName(tagName);
        for (int loop = 0; loop < list.getLength(); loop++) {
            Node node = list.item(loop);
            if (node != null) {
                Node child = node.getFirstChild();
                if ((child != null) && child.getNodeValue() != null) return child.getNodeValue();
            }
        }
        return returnString;
    }
}


