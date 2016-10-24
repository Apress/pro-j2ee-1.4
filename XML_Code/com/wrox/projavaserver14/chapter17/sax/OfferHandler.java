package com.wrox.projavaserver14.chapter17.sax;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXParseException;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Stack;

import org.apache.crimson.tree.XmlDocument;

public class OfferHandler extends DefaultHandler {
    
    private Stack elements;
    
    private Document offerDOM;
    
    public Document getOfferDOM() { return offerDOM; }
    
    public void error(SAXParseException ex) throws SAXException {
        throw ex;
    }
    
    public void fatalError(SAXParseException ex) throws SAXException {
        throw ex;
    }
    
    public void warning(SAXParseException ex) throws SAXException {
        throw ex;
    }
    
    public void characters(char[] ch, int start, int length) {
        Element current = (Element)elements.peek();
        current.appendChild(offerDOM.createTextNode(new String(ch, 
        start, length)));
    }
    
    public void endDocument() {
        offerDOM.appendChild((Element)elements.pop());
    }
    
    public void endElement(String nsURI, String localName, String qName) {
        if(elements.size() != 1) elements.pop();
    }
    
    public void startDocument() {
        elements = new Stack();
        offerDOM = new XmlDocument();
    }
   
    public void startElement(String nsURI, String localName, String qName, 
    Attributes atts) {
        
        Element child = offerDOM.createElement(qName);
        
        for(int i = 0;i < atts.getLength();i++)
            child.setAttribute(atts.getQName(i), atts.getValue(i));
        
        if(elements.isEmpty())
            elements.push(child);
        else {
            ((Element)elements.peek()).appendChild(child);
            elements.push(child);
        }
        
    }
    
}
