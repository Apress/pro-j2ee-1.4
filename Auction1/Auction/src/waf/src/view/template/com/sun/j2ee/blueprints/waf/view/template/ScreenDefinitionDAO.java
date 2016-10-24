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
 $Id: ScreenDefinitionDAO.java,v 1.2 2002/11/19 20:37:44 gmurray Exp $ */

package com.sun.j2ee.blueprints.waf.view.template;

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

import com.sun.j2ee.blueprints.waf.view.template.Screen;


/**
 * This class provides the data bindings for the screendefinitions.xml
 * and the requestmappings.xml file.
 * The data obtained is maintained by the ScreenFlowManager
 */

public class ScreenDefinitionDAO {

    // event - flow constants
    public static final String URL_MAPPING = "url-mapping";
    public static final String SCREEN_DEFINITION = "screen-definition";
    public static final String LANGUAGE = "language";
    public static final String TEMPLATE = "template";
    public static final String DEFAULT_TEMPLATE = "default-template";
    public static final String RESULT = "result";
    public static final String NEXT_SCREEN = "screen";
    public static final String USE_REQUEST_HANDLER = "useRequestHandler";
    public static final String USE_FLOW_HANDLER = "useFlowHandler";
    public static final String FLOW_HANDLER_CLASS = "class";
    public static final String REQUEST_HANDLER_CLASS = "request-handler-class";
    public static final String HANDLER_RESULT = "handler-result";
    public static final String FLOW_HANDLER = "flow-handler";

    // screendefinitions.xml contansts
    public static final String KEY = "key";
    public static final String VALUE= "value";
    public static final String DIRECT="direct";
    public static final String SCREEN= "screen";
    public static final String NAME = "name";
    public static final String URL = "url";
    public static final String PARAMETER = "parameter";

    public static Element loadDocument(URL url) {
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
            System.err.println ("ScreenFlowXmlDAO ** Parsing error" + ", line " +
                        err.getLineNumber () + ", uri " + err.getSystemId ());
            System.err.println("ScreenFlowXmlDAO error: " + err.getMessage ());
        } catch (SAXException e) {
            System.err.println("ScreenFlowXmlDAO error: " + e);
        } catch (java.net.MalformedURLException mfx) {
            System.err.println("ScreenFlowXmlDAO error: " + mfx);
        } catch (java.io.IOException e) {
            System.err.println("ScreenFlowXmlDAO error: " + e);
        } catch (Exception pce) {
            System.err.println("ScreenFlowXmlDAO error: " + pce);
        }
        return null;
    }

    public static Screens loadScreenDefinitions(URL location) {
        Element root = loadDocument(location);
        if (root != null) return getScreens(root);
        else return null;
    }




    public static HashMap getScreenDefinitions(Element root) {
        HashMap screensDefs = new HashMap();
        NodeList list = root.getElementsByTagName(SCREEN_DEFINITION);
        for (int loop = 0; loop < list.getLength(); loop++) {
            Node node = list.item(loop);
            if (node != null) {
                String language = null;
                String url = null;
                if (node instanceof Element) {
                    language = ((Element)node).getAttribute(LANGUAGE);
                    url = ((Element)node).getAttribute(URL);
                }
                if ((language != null) && (url != null) && !screensDefs.containsKey(language)) {
                    screensDefs.put(language, url);
                } else {
                    System.err.println("*** Non Fatal errror: ScreenDefinitions for language " + language + 
                                       " defined more than once in screen definitions file");
                }
            }
        }
        return screensDefs;
    }

    public static Screens getScreens(Element root) {
        // get the template
        String defaultTemplate = getTagValue(root, DEFAULT_TEMPLATE);
        if (defaultTemplate == null) {
            System.err.println("*** ScreenDefinitionDAO error: " + 
                               " Default Template not Defined.");
            return null;
        }

        Screens screens = new Screens(defaultTemplate);
        getTemplates(root, screens);
        // get screens
        NodeList list = root.getElementsByTagName(SCREEN);
        for (int loop = 0; loop < list.getLength(); loop++) {
            Node node = list.item(loop);
            if ((node != null) && node instanceof Element) {
                String templateName = ((Element)node).getAttribute(TEMPLATE);
                String screenName = ((Element)node).getAttribute(NAME);
                System.out.println("screenName:" + screenName);
                HashMap parameters = getParameters(node);
                Screen screen = new Screen(screenName, templateName, parameters);
                if (!screens.containsScreen(screenName)) {
                    screens.addScreen(screenName, screen);
                } else {
                    System.err.println("*** Non Fatal errror: Screen " + screenName + 
                                       " defined more than once in screen definitions file");
                }
            }
        }
        return screens;
    }

    /**
     *    Load the templates into the Screens object
     */

    public static void getTemplates(Element root, Screens screens) {
        NodeList list = root.getElementsByTagName(TEMPLATE);
        for (int loop = 0; loop < list.getLength(); loop++) {
            Node node = list.item(loop);
            String templateName = null;
            if (node != null) {
                if (node instanceof Element) {
                    templateName = ((Element)node).getAttribute(NAME);
                }
                String templateURL = getSubTagValue(node, URL);

                if (!screens.containsTemplate(templateName)) {
                    screens.addTemplate(templateName, templateURL);
                } else {
                    System.err.println("*** Non Fatal errror: Template " + templateName + 
                                       " defined more than once in screen definitions file");
                }
            }
        };
    }

    private static HashMap getParameters(Node node) {
        HashMap params = new HashMap();
        if (node != null) {
            NodeList  children = node.getChildNodes();
            for (int innerLoop =0; innerLoop < children.getLength(); innerLoop++) {
                Node  child = children.item(innerLoop);
                if ((child != null) && (child.getNodeName() != null) && child.getNodeName().equals(PARAMETER) ) {
                    if (child instanceof Element) {
                        Element childElement = ((Element)child);
                        String key = childElement.getAttribute(KEY);
                        String value = childElement.getAttribute(VALUE);
                        String directString = childElement.getAttribute(DIRECT);
                        boolean direct = false;
                        if ((directString != null) && directString.equals("true")) {
                            direct = true;
                        }
                        if (!params.containsKey(key)) {
                            params.put(key, new Parameter(key, value, direct));
                        } else {
                            System.err.println("*** Non Fatal errror: " + 
                                               "Parameter " + key + " is defined more than once");

                        }
                    }
                }
            } // end inner loop
        }
        return params;
    }

    public static String getSubTagValue(Node node, String subTagName) {
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

    public static String getSubTagValue(Element root, String tagName, String subTagName) {
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

    public static String getTagValue(Element root, String tagName) {
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


