package com.wrox.auction.offer.client;

import com.sun.j2ee.blueprints.servicelocator.web.ServiceLocator;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import com.wrox.auction.entity.interfaces.*;

import java.math.BigDecimal;

public class OfferDelegate {

  private OfferControllerHome home = null;

  public OfferDelegate() {

    ServiceLocator sl = ServiceLocator.getInstance();
    home = (OfferControllerHome)sl.getRemoteHome(
    "java:comp/env/ejb/offerController", 
    OfferControllerHome.class);

  }

  public OfferData[] searchOffers(String searchText) {

    try {

      return home.create().searchOffers(searchText);

    }catch(RemoteException ex) {
      throw new RuntimeException(ex);
    }catch(CreateException ex) {
      throw new RuntimeException(ex);
    }

  }

  public OfferData[] listOffers(String userId)
  throws OfferException {

    try {

      return home.create().listOffers(userId);

    }catch(RemoteException ex) {
      throw new RuntimeException(ex);
    }catch(CreateException ex) {
      throw new RuntimeException(ex);
    }

  }

  public void createOffer(OfferData offerData, String userId)
  throws OfferException {

    try {

      home.create().createOffer(offerData, userId);

    }catch(RemoteException ex) {
      throw new RuntimeException(ex);
    }catch(CreateException ex) {
      throw new RuntimeException(ex);
    }

  }

  public void updateOffer(OfferData offerData)
  throws OfferException {

    try {

      home.create().updateOffer(offerData);

    }catch(RemoteException ex) {
      throw new RuntimeException(ex);
    }catch(CreateException ex) {
      throw new RuntimeException(ex);
    }

  }

  public void deleteOffer(String offerId)
  throws OfferException {

    try {

      home.create().deleteOffer(offerId);

    }catch(RemoteException ex) {
      throw new RuntimeException(ex);
    }catch(CreateException ex) {
      throw new RuntimeException(ex);
    }

  }

  public void offerBid(String offerId, BigDecimal bidPrice)
  throws OfferException {

    try {

      home.create().offerBid(offerId, bidPrice);

    }catch(RemoteException ex) {
      throw new RuntimeException(ex);
    }catch(CreateException ex) {
      throw new RuntimeException(ex);
    }

  }

}
