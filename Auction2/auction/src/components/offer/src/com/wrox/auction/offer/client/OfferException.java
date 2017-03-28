package com.wrox.auction.offer.client;

import javax.ejb.EJBObject;
import java.rmi.RemoteException; 

public class OfferException extends Exception {

  public OfferException(String msg) { super(msg); }

}