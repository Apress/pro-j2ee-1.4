/*
 * OfferReader.java
 *
 * Created on 13 March 2004, 16:01
 */

package com.wrox.projavaserver14.chapter17.sax;

import org.xml.sax.XMLReader;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.XMLReaderFactory;

import org.apache.crimson.tree.XmlDocument;

public class OfferReader {
    
    public static void main(String args[]) throws Exception {
        
        XMLReader reader = XMLReaderFactory.createXMLReader(
        "org.apache.crimson.parser.XMLReaderImpl");

        OfferHandler handler = new OfferHandler();
        
        reader.setContentHandler(handler);
        reader.setErrorHandler(handler);

        InputSource in = new InputSource(OfferReader.class.getClassLoader()
        .getResourceAsStream("offers.xml"));
        
        reader.parse(in);
        
        ((XmlDocument)handler.getOfferDOM()).write(System.out);

    }
    
}
