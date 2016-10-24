package com.wrox.auction.entity.interfaces;

public interface CustomerLocalHome
   extends javax.ejb.EJBLocalHome
{
   public static final String COMP_NAME="java:comp/env/ejb/Customer";
   public static final String JNDI_NAME="com.wrox.auction.entity.interfaces.CustomerLocalHome";

   public com.wrox.auction.entity.interfaces.CustomerLocal create(java.lang.String param1 , 
   java.lang.String param2 , java.lang.String param3 , java.lang.String param4)
      throws javax.ejb.CreateException;

   public com.wrox.auction.entity.interfaces.CustomerLocal findByPrimaryKey(java.lang.String pk)
      throws javax.ejb.FinderException;

}
