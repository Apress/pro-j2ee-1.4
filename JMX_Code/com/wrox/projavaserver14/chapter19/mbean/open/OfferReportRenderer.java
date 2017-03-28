package com.wrox.projavaserver14.chapter19.mbean.open;

import java.util.Iterator;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularType;

public class OfferReportRenderer {
  
  public static void main(String args[]) throws Exception {
        

	MBeanServer server = MBeanServerFactory.createMBeanServer();
    

	ObjectName reportName = 
	new ObjectName("AuctionApplication:type=report");
	server.registerMBean(new OfferReport(), reportName);
    

	TabularData report = 
	(TabularData)server.getAttribute(reportName, "report");


	TabularType reportType = report.getTabularType();
	String reportDescription = reportType.getDescription();



	Iterator rows = report.values().iterator();    
    
	System.out.print("<" + reportDescription + ">");
	while(rows.hasNext()) {  
	  printComposite((CompositeData)rows.next());
	}
	System.out.print("</" + reportDescription + ">");
    
  }
  

  private static void printComposite(CompositeData comp) {
    

	CompositeType type = comp.getCompositeType();

	String description = type.getDescription();
	System.out.print("<" + description + ">");

	Iterator itemNames = type.keySet().iterator();
	while(itemNames.hasNext()) {

	  String itemName = (String)itemNames.next();
	  OpenType itemType = type.getType(itemName);
	  String itemDescription = type.getDescription(itemName);

	  if(itemType.isArray()) {
		System.out.print("<" + itemDescription + ">");
		CompositeData[] values = (CompositeData[])comp.get(itemName);
		for(int i = 0;i < values.length;i++) 
		  printComposite(values[i]);
		System.out.print("</" + itemDescription + ">");

	  }else if(itemType instanceof SimpleType) {
		System.out.print("<" + itemDescription + ">");
		System.out.print(comp.get(itemName));
		System.out.print("</" + itemDescription + ">");

	  }else {
		printComposite((CompositeData)comp.get(itemName));
	  }
	}
	System.out.print("</" + description + ">");
    
  }
  
}
