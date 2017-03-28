package com.wrox.projavaserver14.jms;

import javax.naming.*;
import javax.jms.*;

public class Requester {
  
  public static void main(String args[]) throws Exception {
    

    InitialContext ctx = new InitialContext();
    ConnectionFactory cf = (ConnectionFactory)ctx.lookup("qcf");
    Destination dest = (Destination)ctx.lookup("queue");
    
    Connection con = cf.createConnection();
    Session sess = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
    
    con.start();

    MessageProducer prod = sess.createProducer(dest);

    Message req = sess.createTextMessage();

    TemporaryQueue replyTo = sess.createTemporaryQueue();

    req.setJMSReplyTo(replyTo);

    prod.send(req);

    MessageConsumer cs = sess.createConsumer(replyTo);
    Message res = cs.receive();

    if(req.getJMSMessageID().equals(res.getJMSCorrelationID())) {
      System.out.println("Response received");
      System.out.println("Request ID: " + req.getJMSMessageID());
      System.out.println("Response Correlation ID: " + 
      res.getJMSCorrelationID());
      System.out.println("Response ID: " + res.getJMSMessageID());
    }

    replyTo.delete();
    
    con.close();
    
  }
  
}
