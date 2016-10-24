package com.wrox.auction.customer.client;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException; 

public interface CustomerControllerHome extends EJBHome {

  public CustomerController create()
  throws CreateException, RemoteException;

}