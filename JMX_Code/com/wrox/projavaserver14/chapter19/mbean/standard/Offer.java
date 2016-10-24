package com.wrox.projavaserver14.chapter19.mbean.standard;

public class Offer implements OfferMBean {
    
  private int id;
    
  private String name;
    
  private String description;
    
  private double askPrice;
    
  public Offer(int newId, String newName, String newDescription) {
        
    id = newId;
    name = newName;
    description = newDescription;
        
  }
    
  public int getId() { return id; }
    
  public String getName() { return name; } 
  public void setName(String newName) { name = newName; }
    
  public String getDescription() { return description; }
  public void setDescription(String newDescription) {
      description = newDescription;
  }
    
  public double getAskPrice() { return askPrice; }
    
  public void bid(double bidPrice) {
      System.out.println("Bid received:" + bidPrice);
  }
    
}
