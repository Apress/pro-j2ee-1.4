package com.wrox.auction.message.ejb;

import javax.ejb.*;
import javax.jms.*;
import java.util.Iterator;
import java.math.BigDecimal;

import com.wrox.auction.entity.interfaces.*;
import com.sun.j2ee.blueprints.servicelocator.ejb.ServiceLocator;

public class BidCalculatorEJB implements MessageDrivenBean, MessageListener {

  private OfferLocalHome offerHome;

  public void setMessageDrivenContext(MessageDrivenContext mdc) { }

  public void ejbCreate() { 

    ServiceLocator sl = new ServiceLocator();
    offerHome = (OfferLocalHome)
    sl.getLocalHome("java:comp/env/ejb/offer");

  }

  public void onMessage(Message inMessage) {

    try {

      ObjectMessage msg = (ObjectMessage)inMessage;
      String offerId = (String)msg.getObject();

      OfferLocal offer = offerHome.findByPrimaryKey(offerId);
      String offerName = offer.getName();
      String offerDescription = offer.getDescription();
      BigDecimal offerAskPrice = offer.getAskPrice();

      CustomerLocal offerCustomer = offer.getCustomer();
      String offerCustomerName = offerCustomer.getName();
      String offerCustomerEmail = offerCustomer.getEmail();

      BidLocal highestBid = null;
      BigDecimal maxBid = new BigDecimal(0.0);

      Iterator bids = offer.getBids().iterator();
      while(bids.hasNext()) {
        BidLocal bid = (BidLocal)bids.next();
        if(bid.getBidPrice().compareTo(maxBid) > 0) {
          maxBid = new BigDecimal(bid.getBidPrice().doubleValue());
          highestBid = bid;
        }
      }

      if(highestBid == null) return;

      CustomerLocal bidCustomer = highestBid.getCustomer();
      String bidCustomerName = bidCustomer.getName();
      String bidCustomerEmail = bidCustomer.getEmail();

      //Send an email to the offer customer
      System.out.println("offerName:" + offerName);
      System.out.println("offerDescription:" + offerDescription);
      System.out.println("offerAskPrice:" + offerAskPrice);
      System.out.println("offerCustomerName:" + offerCustomerName);
      System.out.println("offerCustomerEmail:" + offerCustomerEmail);
      System.out.println("maxBid:" + maxBid);
      System.out.println("bidCustomerName:" + bidCustomerName);
      System.out.println("bidCustomerEmail:" + bidCustomerEmail);

    }catch(FinderException ex) {
      throw new EJBException(ex);
    }catch(JMSException ex) {
      throw new EJBException(ex);
    }

  }
    
  public void ejbRemove() {}

}