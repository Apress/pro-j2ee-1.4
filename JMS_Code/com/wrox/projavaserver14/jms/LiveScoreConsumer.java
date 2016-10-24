package com.wrox.projavaserver14.jms;

import javax.naming.*;
import javax.jms.*;

public class LiveScoreConsumer {
  
  public static void main(String args[]) throws Exception {
    

    InitialContext ctx = new InitialContext();
    ConnectionFactory cf = (ConnectionFactory)ctx.lookup("tcf");
    Destination dest = (Destination)ctx.lookup("premierLeagueScore");
    

    Connection con = cf.createConnection();
    con.setClientID("Meeraj");
    

    Session sess = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
    

    TopicSubscriber sub = 
    sess.createDurableSubscriber((Topic)dest, "liveScore");

    con.start();
    

    TextMessage msg = (TextMessage)sub.receive(5*1000);
    if(msg != null)
      System.out.println(msg.getText());
    con.start();
    

    con.close();
    
    System.out.println("Connection closed");
    
  }
}
