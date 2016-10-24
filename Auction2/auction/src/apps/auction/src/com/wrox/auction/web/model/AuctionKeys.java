package com.wrox.auction.web.model;

import com.sun.j2ee.blueprints.waf.controller.web.util.WebKeys;

public class AuctionKeys extends WebKeys {

    private AuctionKeys() {} 

    public static final String CUSTOMER_DELEGATE = 
        "com.wrox.auction.delegate.CUSTOMER_DELEGATE";
    public static final String OFFER_DELEGATE = 
        "com.wrox.auction.delegate.OFFER_DELEGATE";

    public static final String CUSTOMER_BEAN = "customer";
    public static final String OFFER_LIST = "offerList";
    public static final String ERROR_KEY = "ERROR_KEY";
    public static final String SUCCESS_KEY = "SUCCESS_KEY";
}

