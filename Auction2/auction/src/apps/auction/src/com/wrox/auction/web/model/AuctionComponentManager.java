package com.wrox.auction.web.model;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;

import java.beans.Beans;

import com.sun.j2ee.blueprints.util.tracer.Debug;

import com.sun.j2ee.blueprints.waf.controller.web.WebComponentManager;

import com.wrox.auction.customer.client.CustomerDelegate;
import com.wrox.auction.offer.client.OfferDelegate;

public class AuctionComponentManager extends WebComponentManager
              implements HttpSessionListener {
            
    public void sessionCreated(HttpSessionEvent se) {
        super.sessionCreated(se);
    }
    
    public void sessionDestroyed(HttpSessionEvent se) {
        super.sessionDestroyed(se);
    }

    public CustomerDelegate getCustomerDelegate(HttpSession session) {

        ServletContext context = session.getServletContext();
        CustomerDelegate customerDelegate = null;
        if (context.getAttribute(AuctionKeys.CUSTOMER_DELEGATE) != null) {
            customerDelegate = (CustomerDelegate)context.getAttribute(AuctionKeys.CUSTOMER_DELEGATE);
        } else {
            customerDelegate = new CustomerDelegate();
            context.setAttribute(AuctionKeys.CUSTOMER_DELEGATE, customerDelegate);
        }
        return customerDelegate;


    }

    public OfferDelegate getOfferDelegate(HttpSession session) {

        ServletContext context = session.getServletContext();
        OfferDelegate offerDelegate = null;
        if (context.getAttribute(AuctionKeys.OFFER_DELEGATE) != null) {
            offerDelegate = (OfferDelegate)context.getAttribute(AuctionKeys.OFFER_DELEGATE);
        } else {
            offerDelegate = new OfferDelegate();
            context.setAttribute(AuctionKeys.OFFER_DELEGATE, offerDelegate);
        }
        return offerDelegate;


    }

}


