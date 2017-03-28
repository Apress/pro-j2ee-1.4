/*
 * Generated file - Do not edit!
 */
package com.wrox.auction.entity.interfaces;

/**
 * Data object for Offer.
 */
public class OfferData
   extends java.lang.Object
   implements java.io.Serializable
{
   private java.lang.String offerId;
   private java.lang.String name;
   private java.lang.String description;
   private java.math.BigDecimal askPrice;

   public OfferData()
   {
   }

   public OfferData( java.lang.String offerId,java.lang.String name,java.lang.String description,java.math.BigDecimal askPrice )
   {
      setOfferId(offerId);
      setName(name);
      setDescription(description);
      setAskPrice(askPrice);
   }

   public OfferData( OfferData otherData )
   {
      setOfferId(otherData.getOfferId());
      setName(otherData.getName());
      setDescription(otherData.getDescription());
      setAskPrice(otherData.getAskPrice());

   }

   public java.lang.String getOfferId()
   {
      return this.offerId;
   }
   public void setOfferId( java.lang.String offerId )
   {
      this.offerId = offerId;
   }

   public java.lang.String getName()
   {
      return this.name;
   }
   public void setName( java.lang.String name )
   {
      this.name = name;
   }

   public java.lang.String getDescription()
   {
      return this.description;
   }
   public void setDescription( java.lang.String description )
   {
      this.description = description;
   }

   public java.math.BigDecimal getAskPrice()
   {
      return this.askPrice;
   }
   public void setAskPrice( java.math.BigDecimal askPrice )
   {
      this.askPrice = askPrice;
   }

   public String toString()
   {
      StringBuffer str = new StringBuffer("{");

      str.append("offerId=" + getOfferId() + " " + "name=" + getName() + " " + "description=" + getDescription() + " " + "askPrice=" + getAskPrice());
      str.append('}');

      return(str.toString());
   }

   public boolean equals( Object pOther )
   {
      if( pOther instanceof OfferData )
      {
         OfferData lTest = (OfferData) pOther;
         boolean lEquals = true;

         if( this.offerId == null )
         {
            lEquals = lEquals && ( lTest.offerId == null );
         }
         else
         {
            lEquals = lEquals && this.offerId.equals( lTest.offerId );
         }
         if( this.name == null )
         {
            lEquals = lEquals && ( lTest.name == null );
         }
         else
         {
            lEquals = lEquals && this.name.equals( lTest.name );
         }
         if( this.description == null )
         {
            lEquals = lEquals && ( lTest.description == null );
         }
         else
         {
            lEquals = lEquals && this.description.equals( lTest.description );
         }
         if( this.askPrice == null )
         {
            lEquals = lEquals && ( lTest.askPrice == null );
         }
         else
         {
            lEquals = lEquals && this.askPrice.equals( lTest.askPrice );
         }

         return lEquals;
      }
      else
      {
         return false;
      }
   }

   public int hashCode()
   {
      int result = 17;

      result = 37*result + ((this.offerId != null) ? this.offerId.hashCode() : 0);

      result = 37*result + ((this.name != null) ? this.name.hashCode() : 0);

      result = 37*result + ((this.description != null) ? this.description.hashCode() : 0);

      result = 37*result + ((this.askPrice != null) ? this.askPrice.hashCode() : 0);

      return result;
   }

}
