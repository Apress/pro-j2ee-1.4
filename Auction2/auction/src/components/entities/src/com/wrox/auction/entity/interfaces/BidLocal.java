package com.wrox.auction.entity.interfaces;

public interface BidLocal
   extends javax.ejb.EJBLocalObject
{

   public java.lang.String getBidId(  ) ;

   public java.math.BigDecimal getBidPrice(  ) ;
   public void setBidPrice( java.math.BigDecimal bidPrice ) ;

   public com.wrox.auction.entity.interfaces.CustomerLocal getCustomer(  ) ;
   public void setCustomer( com.wrox.auction.entity.interfaces.CustomerLocal customer ) ;

   public com.wrox.auction.entity.interfaces.BidData getData(  ) ;
   public void setData( com.wrox.auction.entity.interfaces.BidData data ) ;

   public com.wrox.auction.entity.interfaces.OfferLocal getOffer(  ) ;
   public void setOffer( com.wrox.auction.entity.interfaces.OfferLocal offer ) ;

}
