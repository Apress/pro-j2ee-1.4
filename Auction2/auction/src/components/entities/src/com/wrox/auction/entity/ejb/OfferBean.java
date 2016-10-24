package com.wrox.auction.entity.ejb;

public abstract class OfferBean extends AbstractBean {

   public abstract java.lang.String getOfferId();
   public abstract void setOfferId(java.lang.String offerId);

   public abstract java.lang.String getName();
   public abstract void setName(java.lang.String name);

   public abstract java.lang.String getDescription();
   public abstract void setDescription(java.lang.String description);

   public abstract java.math.BigDecimal getAskPrice();
   public abstract void setAskPrice(java.math.BigDecimal askPrice);

   public abstract com.wrox.auction.entity.interfaces.CustomerLocal getCustomer();
   public abstract void setCustomer(com.wrox.auction.entity.interfaces.CustomerLocal customer);

   public abstract java.util.Collection getBids();
   public abstract void setBids(java.util.Collection bids);

   public java.lang.String ejbCreate( java.lang.String offerId, java.lang.String name, 
   java.lang.String description, java.math.BigDecimal askPrice,
    com.wrox.auction.entity.interfaces.CustomerLocal customer ) throws javax.ejb.CreateException {
      setOfferId(offerId);
      setName(name);
      setDescription(description);
      setAskPrice(askPrice);
      return null;
   }

   public void ejbPostCreate( java.lang.String offerId, java.lang.String name, 
   java.lang.String description, java.math.BigDecimal askPrice,
    com.wrox.auction.entity.interfaces.CustomerLocal customer ) throws javax.ejb.CreateException {
      setCustomer(customer);
   }

   public com.wrox.auction.entity.interfaces.OfferData getData()
   {
      com.wrox.auction.entity.interfaces.OfferData dataHolder = null;
      try
      {
         dataHolder = new com.wrox.auction.entity.interfaces.OfferData();

         dataHolder.setOfferId( getOfferId() );
         dataHolder.setName( getName() );
         dataHolder.setDescription( getDescription() );
         dataHolder.setAskPrice( getAskPrice() );

      }
      catch (RuntimeException e)
      {
         throw new javax.ejb.EJBException(e);
      }

      return dataHolder;
   }

   public void setData( com.wrox.auction.entity.interfaces.OfferData dataHolder )
   {
      try
      {
         setName( dataHolder.getName() );
         setDescription( dataHolder.getDescription() );
         setAskPrice( dataHolder.getAskPrice() );

      }
      catch (Exception e)
      {
         throw new javax.ejb.EJBException(e);
      }
   }

}
