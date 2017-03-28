package com.wrox.auction.signon.client;

import javax.ejb.EJBObject;
import java.rmi.RemoteException; 

public interface SignOnController extends EJBObject {

  public boolean authenticate(String userName, String password)
  throws RemoteException;

}