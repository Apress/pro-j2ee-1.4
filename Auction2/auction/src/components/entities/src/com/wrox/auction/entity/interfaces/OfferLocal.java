package com.wrox.auction.entity.interfaces;

public interface OfferLocal
   extends javax.ejb.EJBLocalObject
{

   public java.lang.String getOfferId(  ) ;

   public java.math.BigDecimal getAskPrice(  ) ;
   public void setAskPrice( java.math.BigDecimal askPrice ) ;

   public java.lang.String getDescription(  ) ;
   public void setDescription( java.lang.String description ) ;

   public java.lang.String getName(  ) ;
   public void setName( java.lang.String name ) ;

   public java.util.Collection getBids(  ) ;
   public void setBids( java.util.Collection bids ) ;

   public com.wrox.auction.entity.interfaces.CustomerLocal getCustomer(  ) ;
   public void setCustomer( com.wrox.auction.entity.interfaces.CustomerLocal customer ) ;

   public com.wrox.auction.entity.interfaces.OfferData getData(  ) ;
   public void setData( com.wrox.auction.entity.interfaces.OfferData data ) ;

}
