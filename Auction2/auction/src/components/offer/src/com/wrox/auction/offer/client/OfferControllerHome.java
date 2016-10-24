package com.wrox.auction.offer.client;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException; 

public interface OfferControllerHome extends EJBHome {

  public OfferController create()
  throws CreateException, RemoteException;

}