package com.wrox.auction.web.exceptions;

import com.sun.j2ee.blueprints.waf.controller.web.action.HTMLActionException;

public class CustomerCreateException extends HTMLActionException {

    public CustomerCreateException() {}
    public CustomerCreateException(String s) { super(s); }
}
