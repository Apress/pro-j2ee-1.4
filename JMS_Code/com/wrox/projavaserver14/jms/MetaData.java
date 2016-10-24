package com.wrox.projavaserver14.jms;

import javax.naming.*;
import javax.jms.*;

public class MetaData {
  
  public static void main(String args[]) throws Exception {
    
    InitialContext ctx = new InitialContext();
    ConnectionFactory cf = (ConnectionFactory)ctx.lookup("trainingCF");
    Connection con = cf.createConnection();
    
    ConnectionMetaData metaData = con.getMetaData();
    
    System.out.println("JMS Version:" + metaData.getJMSVersion());
    System.out.println("Provider Version:" + metaData.getProviderVersion());
    System.out.println("Provider:" + metaData.getJMSProviderName());
    
  }
  
}
