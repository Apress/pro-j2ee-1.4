package com.wrox.auction.entity.ejb;

public abstract class CustomerBean extends AbstractBean {

   public abstract java.lang.String getUserId();
   public abstract void setUserId(java.lang.String userId);

   public abstract java.lang.String getPassword();
   public abstract void setPassword(java.lang.String password);

   public abstract java.lang.String getName();
   public abstract void setName(java.lang.String name);

   public abstract java.lang.String getEmail();
   public abstract void setEmail(java.lang.String name);

   public abstract java.util.Collection getBids();
   public abstract void setBids(java.util.Collection bids);

   public abstract java.util.Collection getOffers();
   public abstract void setOffers(java.util.Collection offers);

   public java.lang.String ejbCreate( java.lang.String userId, java.lang.String password, 
   java.lang.String name, java.lang.String email ) throws javax.ejb.CreateException {
      setUserId(userId);
      setPassword(password);
      setName(name);
      setEmail(email);

      return null;
   }

   public void ejbPostCreate( java.lang.String userId, java.lang.String password, 
   java.lang.String name, java.lang.String email ) throws javax.ejb.CreateException {
   }

   public com.wrox.auction.entity.interfaces.CustomerData getData()
   {
      com.wrox.auction.entity.interfaces.CustomerData dataHolder = null;
      try
      {
         dataHolder = new com.wrox.auction.entity.interfaces.CustomerData();

         dataHolder.setUserId( getUserId() );
         dataHolder.setPassword( getPassword() );
         dataHolder.setName( getName() );
         dataHolder.setEmail( getEmail() );

      }
      catch (RuntimeException e)
      {
         throw new javax.ejb.EJBException(e);
      }

      return dataHolder;
   }

   public void setData( com.wrox.auction.entity.interfaces.CustomerData dataHolder )
   {
      try
      {
         setPassword( dataHolder.getPassword() );
         setName( dataHolder.getName() );
         setEmail( dataHolder.getEmail() );

      }
      catch (Exception e)
      {
         throw new javax.ejb.EJBException(e);
      }
   }

}
