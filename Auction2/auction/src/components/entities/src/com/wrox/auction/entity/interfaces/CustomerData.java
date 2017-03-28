/*
 * Generated file - Do not edit!
 */
package com.wrox.auction.entity.interfaces;

/**
 * Data object for Customer.
 */
public class CustomerData
   extends java.lang.Object
   implements java.io.Serializable
{
   private java.lang.String userId;
   private java.lang.String password;
   private java.lang.String name;
   private java.lang.String email;

   public CustomerData()
   {
   }

   public CustomerData( java.lang.String userId,java.lang.String password,java.lang.String name, java.lang.String email )
   {
      setUserId(userId);
      setPassword(password);
      setName(name);
      setEmail(email);
   }

   public CustomerData( CustomerData otherData )
   {
      setUserId(otherData.getUserId());
      setPassword(otherData.getPassword());
      setName(otherData.getName());
      setEmail(otherData.getEmail());

   }

   public java.lang.String getUserId()
   {
      return this.userId;
   }
   public void setUserId( java.lang.String userId )
   {
      this.userId = userId;
   }

   public java.lang.String getPassword()
   {
      return this.password;
   }
   public void setPassword( java.lang.String password )
   {
      this.password = password;
   }

   public java.lang.String getName()
   {
      return this.name;
   }
   public void setName( java.lang.String name )
   {
      this.name = name;
   }

   public java.lang.String getEmail()
   {
      return this.email;
   }
   public void setEmail( java.lang.String email )
   {
      this.email = email;
   }

   public String toString()
   {
      StringBuffer str = new StringBuffer("{");

      str.append("userId=" + getUserId() + " " + "password=" + getPassword() + " " + "name=" + getName() + " " + "email=" + getEmail());
      str.append('}');

      return(str.toString());
   }

   public boolean equals( Object pOther )
   {
      if( pOther instanceof CustomerData )
      {
         CustomerData lTest = (CustomerData) pOther;
         boolean lEquals = true;

         if( this.userId == null )
         {
            lEquals = lEquals && ( lTest.userId == null );
         }
         else
         {
            lEquals = lEquals && this.userId.equals( lTest.userId );
         }
         if( this.password == null )
         {
            lEquals = lEquals && ( lTest.password == null );
         }
         else
         {
            lEquals = lEquals && this.password.equals( lTest.password );
         }
         if( this.name == null )
         {
            lEquals = lEquals && ( lTest.name == null );
         }
         else
         {
            lEquals = lEquals && this.name.equals( lTest.name );
         }
         if( this.email == null )
         {
            lEquals = lEquals && ( lTest.email == null );
         }
         else
         {
            lEquals = lEquals && this.email.equals( lTest.email );
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

      result = 37*result + ((this.userId != null) ? this.userId.hashCode() : 0);

      result = 37*result + ((this.password != null) ? this.password.hashCode() : 0);

      result = 37*result + ((this.name != null) ? this.name.hashCode() : 0);

      result = 37*result + ((this.email != null) ? this.email.hashCode() : 0);

      return result;
   }

}
