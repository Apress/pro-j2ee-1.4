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
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.Stub;

public final class CustomerEditAction extends HTMLActionSupport {   

    public Event perform(HttpServletRequest request) throws CustomerEditException {
     
        HttpSession session = request.getSession();
        AuctionComponentManager acm = 
            (AuctionComponentManager)session.getAttribute(AuctionKeys.COMPONENT_MANAGER);
        CustomerDelegate delegate = acm.getCustomerDelegate(session);
        
        String userId = (String)request.getSession().getAttribute(SignOnFilter.USER_NAME);

        try {
            request.setAttribute(AuctionKeys.CUSTOMER_BEAN, delegate.getCustomer(userId));
        }catch (CustomerException e) {
            request.setAttribute(AuctionKeys.ERROR_KEY, e.getMessage());
            throw new CustomerEditException(e.getMessage());
        }

        return null; 

    }
    
}