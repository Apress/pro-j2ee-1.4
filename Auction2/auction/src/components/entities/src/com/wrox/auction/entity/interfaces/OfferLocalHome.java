package com.wrox.auction.entity.interfaces;

public interface OfferLocalHome
   extends javax.ejb.EJBLocalHome
{
   public static final String COMP_NAME="java:comp/env/ejb/Offer";
   public static final String JNDI_NAME="com.wrox.auction.entity.interfaces.OfferLocalHome";

   public com.wrox.auction.entity.interfaces.OfferLocal create(java.lang.String param1 , java.lang.String param2 , 
   java.lang.String param3 , java.math.BigDecimal param4 , com.wrox.auction.entity.interfaces.CustomerLocal param5)
      throws javax.ejb.CreateException;

   public com.wrox.auction.entity.interfaces.OfferLocal findByPrimaryKey(java.lang.String pk)
      throws javax.ejb.FinderException;

   public java.util.Collection findByNameOrDescription(String searchText)
      throws javax.ejb.FinderException;

}
