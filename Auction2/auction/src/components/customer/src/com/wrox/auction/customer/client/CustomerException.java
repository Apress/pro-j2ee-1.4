package com.wrox.auction.customer.client;

import javax.ejb.EJBObject;
import java.rmi.RemoteException; 

public class CustomerException extends Exception {

  public CustomerException(String msg) { super(msg); }

}