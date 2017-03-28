/*
 * OfferTransformer.java
 *
 * Created on 13 March 2004, 16:15
 */

package com.wrox.projavaserver14.chapter17.jaxp.transform;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerConfigurationException;

import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.sax.SAXResult;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.w3c.dom.Document;

/**
 *
 * @author  pznwc5
 */
public class OfferTransformer {
    
    private static final String XML_SYSTEM_ID = "file:///c:/offers.xml";
    private static final String XSL_SYSTEM_ID = "file:///c:/offers.xsl";
    
    public static void main(String args[]) throws Exception {
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document xsl = db.parse(XSL_SYSTEM_ID);
        TransformerFactory tf = TransformerFactory.newInstance();
        if(!tf.getFeature(DOMSource.FEATURE)) 
            throw new TransformerConfigurationException(
            "DOM Source not supported");
        DOMSource xslSource = new DOMSource(xsl, XSL_SYSTEM_ID);
        Transformer t = tf.newTransformer(xslSource);

        if(!tf.getFeature(StreamSource.FEATURE)) 
            throw new TransformerConfigurationException(
            "Stream Source not supported");
        StreamSource xmlSource = new StreamSource(XML_SYSTEM_ID);
        
        if(!tf.getFeature(SAXResult.FEATURE))
            throw new TransformerConfigurationException(
            "SAX Result not supported");
        SAXResult result = new SAXResult(new DefaultHandler() {
            
            public void startElement(String namespaceURI, String localName, 
            String qName, Attributes atts) {
                
                System.out.print("<" + localName);
                if(atts.getLength() > 0) {
                    System.out.print(" ");
                    for(int i = 0;i < atts.getLength();i++) {
                        System.out.print(atts.getLocalName(i) + "=\"" + 
                        atts.getValue(i) + "\"");
                    }
                }
                System.out.println(">");
                    
            }
            
            public void endElement(String namespaceURI, String localName, 
            String qName) {
                System.out.println("</" + localName + ">");
            }
            
            public void characters(char[] ch, int start, int length) {
                System.out.println(new String(ch, start, length));
            }
            
        });

        t.transform(xmlSource, result);
        
    }
    
}
