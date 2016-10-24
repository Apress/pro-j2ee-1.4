package com.wrox.projavaserver14.jms;

import javax.naming.*;
import javax.jms.*;

public class RequestResponse {
  
  public static void main(String args[]) throws Exception {
    
    InitialContext ctx = new InitialContext();
    QueueConnectionFactory cf = (QueueConnectionFactory)ctx.lookup("qcf");
    Queue dest = (Queue)ctx.lookup("queue");
    
    QueueConnection con = cf.createQueueConnection();
    QueueSession sess = con.createQueueSession(false, 
    Session.AUTO_ACKNOWLEDGE);
    
    con.start();
    
    QueueRequestor requester = new QueueRequestor(sess, dest);
    Message req = sess.createTextMessage();
    Message res = requester.request(req);
    
    System.out.println("Response received");
    System.out.println("Request ID: " + req.getJMSMessageID());
    System.out.println("Response Correlation ID: " + 
    res.getJMSCorrelationID());
    System.out.println("Response ID: " + res.getJMSMessageID());
    
    con.close();
    
  }
  
}
