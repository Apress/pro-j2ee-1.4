package com.wrox.projavaserver14.chapter19.jsr77;

import javax.management.j2ee.ManagementHome;
import javax.management.j2ee.Management;

import javax.management.j2ee.statistics.BoundedRangeStatistic;
import javax.management.j2ee.statistics.CountStatistic;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import java.util.Iterator;

import javax.management.ObjectName;
import javax.management.Query;

public class JVMMonitor {
  
  public static void main(String args[]) throws Exception {
    

    InitialContext ctx = new InitialContext();
    Object obj = ctx.lookup("ejb/mgmt/MEJB");
    ManagementHome home = 
    (ManagementHome)PortableRemoteObject.narrow(obj, ManagementHome.class);
    Management mejb = home.create();
    

    //ObjectName pattern = new ObjectName("*:j2eeType=JVM,*");
    ObjectName pattern = new ObjectName("*:*");
    Iterator j2eeServers = mejb.queryNames(pattern, 
    Query.eq(Query.attr("j2eeType"), Query.value("JVM"))).iterator();
    
    while(j2eeServers.hasNext()) {
      
      ObjectName serverName = (ObjectName)j2eeServers.next();

      Boolean statisticsProvider = 
      (Boolean)mejb.getAttribute(serverName, "statisticsProvider");
      
      if(!statisticsProvider.booleanValue()) continue;
      

      BoundedRangeStatistic heapSize =
      (BoundedRangeStatistic)mejb.getAttribute(serverName, "heapSize");
      CountStatistic upTime =
      (CountStatistic)mejb.getAttribute(serverName, "upTime");
      

      System.out.println("Up Time:" + upTime.getCount());
      System.out.println("Min Heap:" + heapSize.getLowerBound());
      System.out.println("Max Heap:" + heapSize.getUpperBound());
      
    }
    
    ctx.close();
    
  }
  
}
