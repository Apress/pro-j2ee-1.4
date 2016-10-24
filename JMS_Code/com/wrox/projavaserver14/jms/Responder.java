package com.wrox.projavaserver14.jms;

import javax.naming.*;
import javax.jms.*;

public class Responder {
  
  public static void main(String args[]) throws Exception {
    
    InitialContext ctx = new InitialContext();
    ConnectionFactory cf = (ConnectionFactory)ctx.lookup("qcf");
    Destination dest = (Destination)ctx.lookup("queue");
    
    Connection con = cf.createConnection();
    Session sess = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
    
    con.start();
    MessageConsumer cs = sess.createConsumer(dest);
    
    Message msg = cs.receive();
    Destination replyTo = msg.getJMSReplyTo();
    String messageID = msg.getJMSMessageID();
      
    System.out.println("Request ID:" + messageID);
      
    Message reply = sess.createTextMessage();
    reply.setJMSCorrelationID(messageID);
    MessageProducer prod = sess.createProducer(replyTo);
    prod.send(reply);
      
    System.out.println("Response ID:" + reply.getJMSMessageID());
        
  }
  
}
