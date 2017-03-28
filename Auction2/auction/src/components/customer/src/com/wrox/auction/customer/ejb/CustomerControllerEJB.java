package com.wrox.auction.customer.ejb;

import javax.ejb.*;

import com.wrox.auction.entity.interfaces.*;
import com.sun.j2ee.blueprints.servicelocator.ejb.ServiceLocator;
import com.wrox.auction.customer.client.*;

import java.rmi.server.UID;

public class CustomerControllerEJB implements SessionBean {

  private CustomerLocalHome customerHome;

  private SessionContext ctx;

  public void ejbCreate() {

    ServiceLocator sl = new ServiceLocator();
    customerHome = (CustomerLocalHome)
    sl.getLocalHome("java:comp/env/ejb/customer");

  }

  public void ejbActivate() {}
  public void ejbPassivate() {}
  public void ejbRemove() {}
  public void setSessionContext(SessionContext ctx) { this.ctx = ctx; }

  public void createCustomer(CustomerData customerData) throws CustomerException {

    try {
      
      customerHome.create(customerData.getUserId(), 
      customerData.getPassword(), customerData.getName(), customerData.getEmail());

    }catch(DuplicateKeyException ex) {
      ctx.setRollbackOnly();
      throw new CustomerException("User id already exists");
    }catch(Exception ex) {
      throw new EJBException(ex);
    }

  }

  public CustomerData getCustomer(String userId) throws CustomerException {

    try {

      return customerHome.findByPrimaryKey(userId).getData();

    }catch(ObjectNotFoundException ex) {
      ctx.setRollbackOnly();
      throw new CustomerException("Customer not found");
    }catch(Exception ex) {
      throw new EJBException(ex);
    }

  }

  public void updateCustomer(CustomerData customerData) throws CustomerException {

    try {

      customerHome.findByPrimaryKey(customerData.getUserId()).setData(customerData);

    }catch(ObjectNotFoundException ex) {
      ctx.setRollbackOnly();
      throw new CustomerException("Customer not found");
    }catch(Exception ex) {
      throw new EJBException(ex);
    }

  }

}