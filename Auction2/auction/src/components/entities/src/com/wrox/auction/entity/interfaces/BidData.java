/*
 * Generated file - Do not edit!
 */
package com.wrox.auction.entity.interfaces;

/**
 * Data object for Bid.
 */
public class BidData
   extends java.lang.Object
   implements java.io.Serializable
{
   private java.lang.String bidId;
   private java.math.BigDecimal bidPrice;

   public BidData()
   {
   }

   public BidData( java.lang.String bidId,java.math.BigDecimal bidPrice )
   {
      setBidId(bidId);
      setBidPrice(bidPrice);
   }

   public BidData( BidData otherData )
   {
      setBidId(otherData.getBidId());
      setBidPrice(otherData.getBidPrice());

   }

   public java.lang.String getBidId()
   {
      return this.bidId;
   }
   public void setBidId( java.lang.String bidId )
   {
      this.bidId = bidId;
   }

   public java.math.BigDecimal getBidPrice()
   {
      return this.bidPrice;
   }
   public void setBidPrice( java.math.BigDecimal bidPrice )
   {
      this.bidPrice = bidPrice;
   }

   public String toString()
   {
      StringBuffer str = new StringBuffer("{");

      str.append("bidId=" + getBidId() + " " + "bidPrice=" + getBidPrice());
      str.append('}');

      return(str.toString());
   }

   public boolean equals( Object pOther )
   {
      if( pOther instanceof BidData )
      {
         BidData lTest = (BidData) pOther;
         boolean lEquals = true;

         if( this.bidId == null )
         {
            lEquals = lEquals && ( lTest.bidId == null );
         }
         else
         {
            lEquals = lEquals && this.bidId.equals( lTest.bidId );
         }
         if( this.bidPrice == null )
         {
            lEquals = lEquals && ( lTest.bidPrice == null );
         }
         else
         {
            lEquals = lEquals && this.bidPrice.equals( lTest.bidPrice );
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

      result = 37*result + ((this.bidId != null) ? this.bidId.hashCode() : 0);

      result = 37*result + ((this.bidPrice != null) ? this.bidPrice.hashCode() : 0);

      return result;
   }

}
