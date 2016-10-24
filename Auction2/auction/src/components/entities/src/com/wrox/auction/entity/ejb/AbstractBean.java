package com.wrox.auction.entity.ejb;

public abstract class AbstractBean implements javax.ejb.EntityBean
{

   public AbstractBean() {}

   public void ejbLoad() {} 
   public void ejbStore() {}
   public void ejbActivate() {} 
   public void ejbPassivate() {} 
   public void setEntityContext(javax.ejb.EntityContext ctx) {} 
   public void unsetEntityContext() {}
   public void ejbRemove() {} 

}
