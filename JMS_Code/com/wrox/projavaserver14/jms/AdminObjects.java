package com.wrox.projavaserver14.jms;

import javax.naming.*;
import javax.jms.*;

public class AdminObjects {
  
  public static void main(String args[]) throws Exception {
    
    InitialContext ctx = new InitialContext();
    ConnectionFactory cf = 
      (ConnectionFactory)ctx.lookup("BidQueueConnectionFactory");
    Connection con = cf.createConnection();
    
    if(con instanceof TopicConnection) 
      System.out.println("Got topic connection");
    
  }
  
}
