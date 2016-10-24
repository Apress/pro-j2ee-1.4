package com.wrox.auction.web.actions.offer;

// waf imports
import com.sun.j2ee.blueprints.waf.event.Event;
import com.sun.j2ee.blueprints.waf.controller.web.action.HTMLActionSupport;
import com.sun.j2ee.blueprints.waf.controller.web.action.HTMLActionException;

// signon filter - for the userId
import com.sun.j2ee.blueprints.signon.web.SignOnFilter;

// offer component imports
import com.wrox.auction.offer.client.OfferDelegate;
import com.wrox.auction.offer.client.OfferException;

//auction imports
import com.wrox.auction.web.model.AuctionKeys;
import com.wrox.auction.web.model.AuctionComponentManager;

// j2ee imports
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

import com.wrox.auction.entity.interfaces.*;

public final class OfferSearchAction extends HTMLActionSupport {   

    public Event perform(HttpServletRequest request) {
     
        HttpSession session = request.getSession();
        AuctionComponentManager acm = 
            (AuctionComponentManager)session.getAttribute(AuctionKeys.COMPONENT_MANAGER);
        OfferDelegate delegate = acm.getOfferDelegate(session);

        String searchText = request.getParameter("searchText");

        request.setAttribute(AuctionKeys.OFFER_LIST, delegate.searchOffers(searchText));

        return null; 

    }
    
}



