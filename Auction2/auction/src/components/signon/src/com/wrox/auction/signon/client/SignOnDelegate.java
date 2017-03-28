package com.wrox.auction.signon.client;

import com.sun.j2ee.blueprints.servicelocator.web.ServiceLocator;
import java.rmi.RemoteException;

public class SignOnDelegate {

  private SignOnControllerHome home = null;

  public SignOnDelegate () {

    ServiceLocator sl = ServiceLocator.getInstance();
    home = (SignOnControllerHome)sl.getRemoteHome(
    "java:comp/env/ejb/signonController", 
    SignOnControllerHome.class);

  }

  public boolean authenticate(String userName, String password) {

    try {

      return home.create().authenticate(userName, password);

    }catch(Exception ex) {
      throw new RuntimeException(ex);
    }

  }

}
