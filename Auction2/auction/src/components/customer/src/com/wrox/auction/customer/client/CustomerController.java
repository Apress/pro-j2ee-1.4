package com.wrox.auction.customer.client;

import javax.ejb.EJBObject;
import java.rmi.RemoteException; 
import com.wrox.auction.entity.interfaces.*;

public interface CustomerController extends EJBObject {

  public void createCustomer(CustomerData customer) 
  throws CustomerException, RemoteException;

  public CustomerData getCustomer(String userId) 
  throws CustomerException, RemoteException;

  public void updateCustomer(CustomerData customer) 
  throws CustomerException, RemoteException;

}