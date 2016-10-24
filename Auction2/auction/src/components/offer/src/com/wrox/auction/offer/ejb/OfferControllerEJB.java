package com.wrox.auction.offer.ejb;

import javax.ejb.*;
import javax.jms.*;

import com.wrox.auction.entity.interfaces.*;
import com.sun.j2ee.blueprints.servicelocator.ejb.ServiceLocator;
import com.wrox.auction.offer.client.*;

import java.rmi.server.UID;
import java.util.Collection;
import java.util.Iterator;

import java.math.BigDecimal;

public class OfferControllerEJB implements SessionBean, TimedObject {

  private CustomerLocalHome customerHome;
  private OfferLocalHome offerHome;
  private BidLocalHome bidHome;

  private long delay;
  private long interval;

  private Queue bidQueue;
  private QueueConnectionFactory qcf;

  private SessionContext ctx;

  public void ejbCreate() {

    ServiceLocator sl = new ServiceLocator();
    customerHome = (CustomerLocalHome)
    sl.getLocalHome("java:comp/env/ejb/customer");
    offerHome = (OfferLocalHome)
    sl.getLocalHome("java:comp/env/ejb/offer");
    bidHome = (BidLocalHome)
    sl.getLocalHome("java:comp/env/ejb/bid");

    bidQueue = (Queue)
    sl.getQueue("java:comp/env/jms/bidQueue");
    qcf = (QueueConnectionFactory)
    sl.getQueueConnectionFactory("java:comp/env/jms/qcf");

    delay = sl.getLong("java:comp/env/delay");
    interval = sl.getLong("java:comp/env/interval");

  }

  public void ejbActivate() {}
  public void ejbPassivate() {}
  public void ejbRemove() {}
  public void setSessionContext(SessionContext ctx) { this.ctx = ctx; }

  public OfferData[] searchOffers(String searchText) {

    try {

      Collection col = offerHome.findByNameOrDescription("%" + searchText + "%");
      OfferData offerData[] = new OfferData[col.size()];

      Iterator it = col.iterator();
      for(int i = 0;i < offerData.length && it.hasNext();i++) 
        offerData[i] = ((OfferLocal)it.next()).getData();

      return offerData;

    }catch(FinderException ex) {
      throw new EJBException(ex);
    }

  }

  public OfferData[] listOffers(String userId)
  throws OfferException {

    try {

      Collection col = customerHome.findByPrimaryKey(userId).getOffers();
      OfferData offerData[] = new OfferData[col.size()];

      Iterator it = col.iterator();
      for(int i = 0;i < offerData.length && it.hasNext();i++) 
        offerData[i] = ((OfferLocal)it.next()).getData();

      return offerData;

    }catch(ObjectNotFoundException ex) {
      throw new OfferException("Customer not found");
    }catch(FinderException ex) {
      throw new EJBException(ex);
    }

  }

  public void createOffer(OfferData offerData, String userId)
  throws OfferException {

    try {

      OfferLocal offer = offerHome.create(new UID().toString(), 
      offerData.getName(), offerData.getDescription(), 
      offerData.getAskPrice(), customerHome.findByPrimaryKey(userId));

      ctx.getTimerService().createTimer(delay, interval, offer.getOfferId());

    }catch(ObjectNotFoundException ex) {
      throw new OfferException("Customer not found");
    }catch(FinderException ex) {
      throw new EJBException(ex);
    }catch(CreateException ex) {
      throw new EJBException(ex);
    }

  }

  public void updateOffer(OfferData offerData)
  throws OfferException {

    try {

      offerHome.findByPrimaryKey(offerData.getOfferId()).setData(offerData);

    }catch(ObjectNotFoundException ex) {
      throw new OfferException("Offer not found");
    }catch(FinderException ex) {
      throw new EJBException(ex);
    }

  }

  public void deleteOffer(String offerId)
  throws OfferException {

    try {

      offerHome.findByPrimaryKey(offerId).remove();

    }catch(ObjectNotFoundException ex) {
      throw new OfferException("Offer not found");
    }catch(FinderException ex) {
      throw new EJBException(ex);
    }catch(RemoveException ex) {
      throw new EJBException(ex);
    }

  }

  public void offerBid(String offerId, BigDecimal bidPrice)
  throws OfferException {

    try {

      OfferLocal offer = offerHome.findByPrimaryKey(offerId);
      CustomerLocal customer = offer.getCustomer();
      bidHome.create(new UID().toString(), bidPrice, customer, offer);

    }catch(ObjectNotFoundException ex) {
      throw new OfferException("Offer not found");
    }catch(FinderException ex) {
      throw new EJBException(ex);
    }catch(CreateException ex) {
      throw new EJBException(ex);
    }

  }

  public void ejbTimeout(Timer timer) {

    String offerId = (String)timer.getInfo();

    try {

      offerHome.findByPrimaryKey(offerId);
      System.out.println("Processing offer:" + offerId);

      sendMessage(offerId);

    }catch(ObjectNotFoundException ex) {
      System.out.println("Cancelling offer:" + offerId);
      timer.cancel();
    }catch(FinderException ex) {
      throw new EJBException(ex);
    }

  }

  private void sendMessage(String offerId) {

    QueueConnection con = null;

    try {

      con = qcf.createQueueConnection();
      QueueSession sess = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

      QueueSender sender = sess.createSender(bidQueue);

      Message msg = sess.createObjectMessage(offerId);
      sender.send(msg);

    }catch(JMSException ex) {
      throw new EJBException(ex);
    }finally {
      try {
        if(con != null) con.close();
      }catch(JMSException ex) {
        throw new EJBException(ex);
      }
    }

  }

}