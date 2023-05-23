package com.example.monoploygame;

enum BuildingTypes {
  Garage,
  Hotel
}


public class Build {

  private int moreTaxesForHotel = 300;
  private int buildPriceForHotel = 150;

  private int moreTaxesForGarage = 500;
  private int buildPriceForGarage = 250;

  private String successTitle = "City upgrade!";
  private String failedTitle = "Building problem!";

  public void build(BuildingTypes type, City city, Player player) {

    if (type == BuildingTypes.Hotel) {

      if (city.getOwnerID() == player.getID()) {
        if (player.getMoney() >= this.buildPriceForHotel) {
          if (city.getHasHotel() == false) {
            city.setTaxes(city.getTaxes() + this.moreTaxesForHotel);
            city.setHasHotel(true);
            player.setMoney(player.getMoney() - this.buildPriceForHotel);
            System.out.println(city.getTaxes());
            Message.message(this.successTitle + " - Building Hotel", "Player: " + player.name + " has built an hotel and the city: " + city.getName() + " taxes will be: " + city.getTaxes());
          } else {
            Message.message(this.failedTitle, "You can't build because there's another hotel is here!");
          }
        } else {
          Message.message(this.failedTitle, "You don't have enough money to build this hotel!");
        }
      } else {
        Message.message(this.failedTitle, "You can't build on this city! It belongs to another player! or it's still on waiting to buy!");
      }
    } else if (type == BuildingTypes.Garage) {
      if (city.getOwnerID() == player.getID()) {
        if (player.getMoney() >= this.buildPriceForGarage) {
          if (city.getHasGarage() == false) {
            city.setTaxes(city.getTaxes() + this.moreTaxesForGarage);
            city.setHasGarage(true);
            player.setMoney(player.getMoney() - this.buildPriceForGarage);
            System.out.println(city.getTaxes());
            Message.message(this.successTitle + " - Building Garage", "Player: " + player.name + " has built an garage and the city: " + city.getName() + " taxes will be: " + city.getTaxes());
          } else {
            Message.message(this.failedTitle, "You can't build because there's another garage is here!");
          }
        } else {
          Message.message(this.failedTitle, "You don't have enough money to build this garage!");
        }
      } else {
        Message.message(this.failedTitle, "You can't build on this city! It belongs to another player! or it's still on waiting to buy!");
      }
    }


  }

}
