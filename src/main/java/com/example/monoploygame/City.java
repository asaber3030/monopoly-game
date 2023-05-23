package com.example.monoploygame;

public class City {

  private int id; // Block ID
  private int price; // Price
  private int ownerID; // Belongs to whom?
  public int taxes; // Taxes
  private int tempTaxes; // temporary taxes

  private boolean hasHotel = false;
  private boolean hasGarage = false;
  private boolean isAvailable = true; // Is Sold or not

  public String name;

 /**
  * @param NAME defines city name
  * @param ID unique identifier for every city for easy search and processes
  * @param PRICE defines city price for buying and selling it
  * @return void
  */
  public City(String NAME, int ID, int PRICE, int TAXES) {
    this.name = NAME;
    this.id = ID;
    this.price = PRICE;
    this.taxes = TAXES;
    this.tempTaxes = TAXES;
  }

 /**
  * Getting City name
  * @return String
  */
  public String getName() {
    return this.name;
  }

 /**
  * Getting City ID
  * @return int
  */
  public int getID() {
    return this.id;
  }

 /**
  * Getting City Price
  * @return int
  */
  public int getPrice() {
    return this.price;
  }

 /**
  * Getting City Owner ID
  * @return int
  */
  public int getOwnerID() {
    if (this.isAvailable == false) {
      return this.ownerID;
    }
    return 0;
  }

 /**
  * Is available
  * @return boolean
  */
  public boolean getIsAvailable() {
    return this.isAvailable;
  }

 /**
  * Is available = is
  * @return void
  */
  public void setIsAvailable(boolean is) {
    this.isAvailable = is;
  }

 /**
  * Setting the new city owner id
  * @param ID accepts int to set the new city owner
  * @return void
  */
  public void setOwnerID(int ID) {
    this.ownerID = ID;
  }

  public void setTaxes(int t) { this.taxes = t; }
  public int getTaxes() { return this.taxes; }

  public void setHasGarage(boolean g) { this.hasGarage = g; }
  public boolean getHasGarage() { return this.hasGarage; }

  public void setHasHotel(boolean h) { this.hasHotel = h; }
  public boolean getHasHotel() { return this.hasHotel; }

  public int getTempTaxes() { return this.tempTaxes; }

}
