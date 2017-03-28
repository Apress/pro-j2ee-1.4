package com.wrox.auction.entity.interfaces;

public interface BidLocalHome
   extends javax.ejb.EJBLocalHome
{
   public static final String COMP_NAME="java:comp/env/ejb/Bid";
   public static final String JNDI_NAME="com.wrox.auction.entity.interfaces.BidLocalHome";

   public com.wrox.auction.entity.interfaces.BidLocal create(java.lang.String param1 , 
   java.math.BigDecimal param2 , com.wrox.auction.entity.interfaces.CustomerLocal param3 , 
   com.wrox.auction.entity.interfaces.OfferLocal param4)
      throws javax.ejb.CreateException;

   public com.wrox.auction.entity.interfaces.BidLocal findByPrimaryKey(java.lang.String pk)
      throws javax.ejb.FinderException;

}
