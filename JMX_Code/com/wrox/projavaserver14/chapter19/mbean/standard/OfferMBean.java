package com.wrox.projavaserver14.chapter19.mbean.standard;

public interface OfferMBean {
    
    public int getId();
    
    public String getName();    
    public void setName(String newName);
    
    public String getDescription();    
    public void setDescription(String newDescription);
    
    public double getAskPrice();
    
    public void bid(double bidPrice);
    
}
