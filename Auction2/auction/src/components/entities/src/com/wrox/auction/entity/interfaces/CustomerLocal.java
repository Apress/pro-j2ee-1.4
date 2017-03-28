package com.wrox.auction.entity.interfaces;

public interface CustomerLocal
   extends javax.ejb.EJBLocalObject
{

   public java.lang.String getUserId(  ) ;

   public java.lang.String getName(  ) ;
   public void setName( java.lang.String name ) ;

   public java.lang.String getPassword(  ) ;
   public void setPassword( java.lang.String password ) ;

   public java.lang.String getEmail(  ) ;
   public void setEmail( java.lang.String email ) ;

   public java.util.Collection getBids(  ) ;
   public void setBids( java.util.Collection bids ) ;

   public com.wrox.auction.entity.interfaces.CustomerData getData(  ) ;
   public void setData( com.wrox.auction.entity.interfaces.CustomerData data ) ;

   public java.util.Collection getOffers(  ) ;
   public void setOffers( java.util.Collection offers ) ;


}
