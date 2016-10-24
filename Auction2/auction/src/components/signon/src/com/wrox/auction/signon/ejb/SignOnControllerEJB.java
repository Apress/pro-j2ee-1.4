package com.wrox.auction.signon.ejb;

import javax.ejb.*;

import com.wrox.auction.entity.interfaces.*;
import com.sun.j2ee.blueprints.servicelocator.ejb.ServiceLocator;

public class SignOnControllerEJB implements SessionBean {

  private CustomerLocalHome home;

  public void ejbCreate() {

    ServiceLocator sl = new ServiceLocator();
    home = (CustomerLocalHome)
    sl.getLocalHome("java:comp/env/ejb/customer");

  }

  public void ejbActivate() {}
  public void ejbPassivate() {}
  public void ejbRemove() {}
  public void setSessionContext(SessionContext ctx) {}

  public boolean authenticate(String userName, String password) {

    try {

      CustomerLocal local = home.findByPrimaryKey(userName);
      return password != null && password.trim().equals(local.getPassword());

    }catch(ObjectNotFoundException ex) {
      return false;
    }catch(FinderException ex) {
      throw new EJBException(ex);
    }

  }

}