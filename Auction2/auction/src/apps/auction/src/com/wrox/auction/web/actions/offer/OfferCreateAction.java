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

//JDK imports
import java.math.BigDecimal;

import com.wrox.auction.entity.interfaces.*;

public final class OfferCreateAction extends HTMLActionSupport {   

    public Event perform(HttpServletRequest request) throws OfferCreateException {
     
        HttpSession session = request.getSession();
        AuctionComponentManager acm = 
            (AuctionComponentManager)session.getAttribute(AuctionKeys.COMPONENT_MANAGER);
        OfferDelegate delegate = acm.getOfferDelegate(session);

        String userId = (String)request.getSession().getAttribute(SignOnFilter.USER_NAME);

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        BigDecimal askPrice = new BigDecimal(request.getParameter("askPrice"));

        try {
            delegate.createOffer(new OfferData(null, name, description, askPrice), userId);
            request.setAttribute(AuctionKeys.SUCCESS_KEY, "Offer created");
        }catch (OfferException e) {
            request.setAttribute(AuctionKeys.ERROR_KEY, e.getMessage());
            throw new OfferCreateException(e.getMessage());
        }

        return null; 

    }
    
}

