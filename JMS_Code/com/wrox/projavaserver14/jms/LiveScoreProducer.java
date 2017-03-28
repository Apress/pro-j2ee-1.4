package com.wrox.projavaserver14.jms;

import javax.naming.*;
import javax.jms.*;

public class LiveScoreProducer {
  
  public static void main(String args[]) throws Exception {
    

    InitialContext ctx = new InitialContext();
    ConnectionFactory cf = (ConnectionFactory)ctx.lookup("tcf");
    Destination dest = (Destination)ctx.lookup("premierLeagueScore");
    

    Connection con = cf.createConnection();
    Session sess = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
    

    MessageProducer prod = sess.createProducer(dest);
    Message msg = sess.createTextMessage("Chelsea 5 - Man United 0");
    

    prod.send(msg);
    
    con.close();
    
    System.out.println("Connection closed");
    
  }
  
}
