package com.wrox.projavaserver14.chapter17.dom;

import org.apache.crimson.tree.XmlDocument;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class OfferReader {
    
    static String offers[][] = {
        {"1", "MP3 Player", "256 Mb MP3 Player"},
        {"2", "iPod", "15 Gb Mini iPod"}
    };
    
    public static void main(String args[]) throws Exception {
        
        Document offers = buildDOM();
        
        ((XmlDocument)offers).write(System.out);
        
        Element root = offers.getDocumentElement();
        printContents(root);
        
    }
    
    private static Document buildDOM() {
        
        Document dom = new XmlDocument();
        
        Element root = dom.createElement("offers");

        dom.appendChild(root);
        
        for(int i = 0;i < offers.length;i++) {

            Element offer = dom.createElement("offer");
            offer.setAttribute("id", offers[i][0]);
            offer.setAttribute("name", offers[i][1]);
            offer.setAttribute("description", offers[i][2]);
            root.appendChild(offer);
        }
        
        return dom;
        
    }
    
    private static void printContents(Element root) {
        
        NodeList offerList = root.getChildNodes();
        
        for(int i = 0;i < offerList.getLength();i++) {
            
            System.out.print("Offer[" + i + "]: ");

            Node offer = offerList.item(i);

            NamedNodeMap offerAttributes = offer.getAttributes();
            
            for(int j = 0;j < offerAttributes.getLength();j++) {
                Node offerAttribute = offerAttributes.item(j);
                System.out.print(offerAttribute.getNodeName() + "=" + 
                offerAttribute.getNodeValue() + " ");
            }
            System.out.println();
            
        }
        
    }
    
}
