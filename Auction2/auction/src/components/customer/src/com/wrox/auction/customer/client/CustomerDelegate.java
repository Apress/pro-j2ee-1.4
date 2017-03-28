package com.wrox.auction.customer.client;

import com.sun.j2ee.blueprints.servicelocator.web.ServiceLocator;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import com.wrox.auction.entity.interfaces.*;

public class CustomerDelegate {

  private CustomerControllerHome home = null;

  public CustomerDelegate() {

    ServiceLocator sl = ServiceLocator.getInstance();
    home = (CustomerControllerHome)sl.getRemoteHome(
    "java:comp/env/ejb/customerController", 
    CustomerControllerHome.class);

  }

  public void createCustomer(CustomerData customer) throws CustomerException {

    try {

      home.create().createCustomer(customer);

    }catch(RemoteException ex) {
      throw new RuntimeException(ex);
    }catch(CreateException ex) {
      throw new RuntimeException(ex);
    }

  }

  public CustomerData getCustomer(String accountId) throws CustomerException {

    try {

      return home.create().getCustomer(accountId);

    }catch(RemoteException ex) {
      throw new RuntimeException(ex);
    }catch(CreateException ex) {
      throw new RuntimeException(ex);
    }

  }

  public void updateCustomer(CustomerData customer) throws CustomerException {

    try {

      home.create().updateCustomer(customer);

    }catch(RemoteException ex) {
      throw new RuntimeException(ex);
    }catch(CreateException ex) {
      throw new RuntimeException(ex);
    }

  }

}
