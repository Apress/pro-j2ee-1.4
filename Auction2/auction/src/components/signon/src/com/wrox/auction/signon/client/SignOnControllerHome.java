package com.wrox.auction.signon.client;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException; 

public interface SignOnControllerHome extends EJBHome {

  public SignOnController create()
  throws CreateException, RemoteException;

}