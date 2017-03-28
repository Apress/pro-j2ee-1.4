package com.wrox.auction.web.actions.customer;

// waf imports
import com.sun.j2ee.blueprints.waf.event.Event;
import com.sun.j2ee.blueprints.waf.controller.web.action.HTMLActionSupport;
import com.sun.j2ee.blueprints.waf.controller.web.action.HTMLActionException;

// signon filter - for the userId
import com.sun.j2ee.blueprints.signon.web.SignOnFilter;

// customer component imports
import com.wrox.auction.customer.client.CustomerDelegate;
import com.wrox.auction.customer.client.CustomerException;

//auction imports
import com.wrox.auction.web.model.AuctionKeys;
import com.wrox.auction.web.model.AuctionComponentManager;

// j2ee imports
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

import com.wrox.auction.entity.interfaces.*;

public final class CustomerUpdateAction extends HTMLActionSupport {   

    public Event perform(HttpServletRequest request) throws CustomerUpdateException {
     
        HttpSession session = request.getSession();
        AuctionComponentManager acm = 
            (AuctionComponentManager)session.getAttribute(AuctionKeys.COMPONENT_MANAGER);
        CustomerDelegate delegate = acm.getCustomerDelegate(session);

        String userId = request.getParameter("userId");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        try {
            delegate.updateCustomer(new CustomerData(userId, password, name, email));
            request.setAttribute(AuctionKeys.SUCCESS_KEY, "Details updated");
        }catch (CustomerException e) {
            request.setAttribute(AuctionKeys.ERROR_KEY, e.getMessage());
            throw new CustomerUpdateException(e.getMessage());
        }

        return null; 

    }
    
}

