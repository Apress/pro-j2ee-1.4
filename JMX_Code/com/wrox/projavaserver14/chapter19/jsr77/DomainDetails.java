package com.wrox.projavaserver14.chapter19.jsr77;


import javax.management.j2ee.ManagementHome;
import javax.management.j2ee.Management;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import java.util.Iterator;


import javax.management.ObjectName;

public class DomainDetails {
  
  public static void main(String args[]) throws Exception {
    

    InitialContext ctx = new InitialContext();
    Object obj = ctx.lookup("ejb/mgmt/MEJB");
    ManagementHome home = 
    (ManagementHome)PortableRemoteObject.narrow(obj, ManagementHome.class);
    Management mejb = home.create();
    

    ObjectName pattern = new ObjectName("*:j2eeType=J2EEServer,*");
    Iterator j2eeServers = mejb.queryNames(pattern, null).iterator();
    
    if(j2eeServers.hasNext()) {
      

      ObjectName serverName = (ObjectName)j2eeServers.next();


      System.out.println("Vendor:" + mejb.getAttribute(serverName, 
      "serverVendor"));
      System.out.println("Version:" + mejb.getAttribute(serverName, 
      "serverVersion"));
      
    }
    
    ctx.close();
    
  }
  
}
