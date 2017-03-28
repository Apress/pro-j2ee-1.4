package com.wrox.auction.entity.ejb;

public abstract class BidBean extends AbstractBean {

   public abstract java.lang.String getBidId();
   public abstract void setBidId(java.lang.String bidId);

   public abstract java.math.BigDecimal getBidPrice();
   public abstract void setBidPrice(java.math.BigDecimal bidPrice);

   public abstract com.wrox.auction.entity.interfaces.CustomerLocal getCustomer();
   public abstract void setCustomer(com.wrox.auction.entity.interfaces.CustomerLocal customer);

   public abstract com.wrox.auction.entity.interfaces.OfferLocal getOffer();
   public abstract void setOffer(com.wrox.auction.entity.interfaces.OfferLocal offer);

   public java.lang.String ejbCreate( java.lang.String bidId, java.math.BigDecimal bidPrice, 
   com.wrox.auction.entity.interfaces.CustomerLocal customer, 
    com.wrox.auction.entity.interfaces.OfferLocal offer ) throws javax.ejb.CreateException {
      setBidId(bidId);
      setBidPrice(bidPrice);
      return null;
   }

   public void ejbPostCreate( java.lang.String bidId, java.math.BigDecimal bidPrice, 
   com.wrox.auction.entity.interfaces.CustomerLocal customer, 
   com.wrox.auction.entity.interfaces.OfferLocal offer ) throws javax.ejb.CreateException {
      setCustomer(customer);
      setOffer(offer);
   }

   public com.wrox.auction.entity.interfaces.BidData getData()
   {
      com.wrox.auction.entity.interfaces.BidData dataHolder = null;
      try
      {
         dataHolder = new com.wrox.auction.entity.interfaces.BidData();

         dataHolder.setBidId( getBidId() );
         dataHolder.setBidPrice( getBidPrice() );

      }
      catch (RuntimeException e)
      {
         throw new javax.ejb.EJBException(e);
      }

      return dataHolder;
   }

   public void setData( com.wrox.auction.entity.interfaces.BidData dataHolder )
   {
      try
      {
         setBidPrice( dataHolder.getBidPrice() );

      }
      catch (Exception e)
      {
         throw new javax.ejb.EJBException(e);
      }
   }

}
