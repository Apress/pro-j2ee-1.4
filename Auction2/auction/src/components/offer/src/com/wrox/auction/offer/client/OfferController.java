package com.wrox.auction.offer.client;

import javax.ejb.EJBObject;
import java.rmi.RemoteException; 
import com.wrox.auction.entity.interfaces.*;

import java.math.BigDecimal;

public interface OfferController extends EJBObject {

  public OfferData[] searchOffers(String searchText)
  throws RemoteException;

  public OfferData[] listOffers(String userId)
  throws RemoteException, OfferException;

  public void createOffer(OfferData offerData, String userId)
  throws RemoteException, OfferException;

  public void updateOffer(OfferData offerData)
  throws RemoteException, OfferException;

  public void deleteOffer(String offerId)
  throws RemoteException, OfferException;

  public void offerBid(String offerId, BigDecimal bidPrice)
  throws RemoteException, OfferException;

}