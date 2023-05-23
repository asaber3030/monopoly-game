package com.example.monoploygame;

import javafx.scene.control.Alert;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Player {

  private int money = 20000; // Initial money
  private int position = 1; // Initial Position
  private int cycleCount = 0; // Initial cycle count
  private int increasingMoney = 500; // Every cycle player gains 500 * this.cycleCount
  private int shift = 0;

  private int status = 0; // Current status

  private int id; // Id

  private boolean canPlay = false; // Can play?
  private boolean canBuy = false; // Can Buy?
  private boolean canSell = false; // Can Sell?
  private boolean canBuild = false; // Can Build?

  public String name;
  public String color;

  private ArrayList<City> ownedCities = new ArrayList<City>(); // Current owned cities

  public ArrayList<String> moneyHistory = new ArrayList<String>(); // History of money
  public ArrayList<String> positionHistory = new ArrayList<String>(); // History of position

  public static String[] statusReference = { // Status (this.status)
    "Waiting", // 0
    "His Turn", // 1
    "Parking", // 2
    "Paying Fees", // 3
    "Chest", // 4
    "Random Item", // 5
    "Jail", // 6
  };

 /**
  * Constructor of player to take Name, ID, Color
  * @param NAME player name
  * @param ID player id
  */
  public Player(String NAME, int ID, String COLOR) {
    this.name = NAME;
    this.id = ID;
    this.color = COLOR;
    this.moneyHistory.add("Initial money: " + this.getMoney());
    this.positionHistory.add("Position: " + this.getPosition());
  }

 /**
  * This method updating the player position depending on dice result!
  * @param numberOfMoves Dice integer result
  * @return int current Player position
  */
  public int move(int numberOfMoves) {

    if (numberOfMoves >= 1 && numberOfMoves <= 6) {
      if (this.getCanPlay() == true) {
        if (numberOfMoves + this.position >= 28) {
          this.cycleCount += 1;
          this.setMoney(this.getMoney() + (this.increasingMoney * this.cycleCount));
          this.setPosition((this.position - 28) + numberOfMoves);
        } else {
          this.setPosition(this.position + numberOfMoves);
        }
      } else {
        Message.message("Can't play", "Can't Play!!");
      }

    }

    City city = GameApplication.Cities.stream().filter(c -> c.getID() == this.getPosition() && c.getIsAvailable() == true).findAny().orElse(null);

    if (city != null) {
      this.canBuy = true;
    }

    return this.getPosition();
  }

 /**
  * Responsible for buying city for current player
  * @param city Accepts an object from City Class
  * @return boolean true if the buying process is done!
  */
  public boolean buyCity(City city) {

    if (!this.ownedCities.contains(city)) {

      if (this.getMoney() >= city.getPrice() && city.getIsAvailable() && city.getOwnerID() == 0) {

        this.ownedCities.add(city);
        this.money -= city.getPrice();

        city.setIsAvailable(false);
        city.setOwnerID(this.getID());

        Message.message("Buying Status", "Player: " + this.name + " has bought city: " + city.getName() + " for: " + city.getPrice());
        return true;

      } else {
        Message.message("Invalid creds!", "Can't buy!");
        return false;
      }
    } else {
      Message.message("Buying Status", "Cannot buy this city again!");
      return false;
    }
  }

 /**
  * Responsible for selling city
  * @param city Accepts an object from City Class
  * @return boolean true if the selling process is done!
  */
  public boolean sellCity(City city) {
    if (this.ownedCities.contains(city) && city.getOwnerID() == this.getID() && city.getIsAvailable() == false) {
      this.ownedCities.remove(city);
      this.setMoney(this.getMoney() + city.getPrice());

      city.setHasHotel(false);
      city.setHasGarage(false);
      city.setTaxes(city.getTempTaxes());
      city.setIsAvailable(true);
      city.setOwnerID(0);

      Message.message("Selling Status!", "Player: " + this.name + " has sold city: " + city.getName());
      return true;
    } else {
      Message.message("Selling Status!", "Can't sell!");
      return false;
    }
  }

 /**
  * Add new city to this.ownedCities
  * @param city Accepts an object from City Class
  * @return void
  */
  public void addCity(City city) {
    if (city.getIsAvailable() == true) {
      this.ownedCities.add(city);
      city.setIsAvailable(false);
      city.setOwnerID(this.getID());
    }
  }

 /**
  * Saving current position
  * @return void
  */
  public void savePosition() {
    this.positionHistory.add(String.valueOf("Position: " + this.getPosition()));
  }

 /**
  * Getting cities in String
  * Converting this.ownedCities to String
  * @return String
  */
  public String getCities() {

    String text = "";

    for (int i = 0; i <= this.ownedCities.size() - 1; i++) {
      text += ownedCities.get(i).getName() + "\n";
    }

    return text;
  }

 /**
  * @return int
  */
  public int getID() {
    return this.id;
  }

 /**
  * @param pos accepts new player position
  * @return int
  */
  public void setPosition(int pos) {
    this.position = pos;
  }

 /**
  * @return int
  */
  public int getPosition() {
    return this.position;
  }

 /**
  * @param money updates player money
  * @return int
  */
  public void setMoney(int money) {
    this.money = money;
  }

 /**
  * gets player money
  * @return int
  */
  public int getMoney() {
    return this.money;
  }

 /**
  * gets player money
  * @return int
  */
  public void setStatus(int sts) {
    this.status = sts;
  }

 /**
  * gets player status
  * @return String
  */
  public int getStatus() {
    return this.status;
  }

 /**
  * set player canSell
  * @return void
  */
  public void setCanSell(boolean can) {
    this.canSell = can;
  }

 /**
  * gets player canSell
  * @return boolean
  */
  public boolean getCanSell() {
    return this.canSell;
  }

 /**
  * set player canBuy
  * @return void
  */
  public void setCanBuy(boolean can) {
    this.canBuy = can;
  }

 /**
  * gets player canBuy prop
  * @return boolean
  */
  public boolean getCanBuy() {
    return this.canBuy;
  }

 /**
  * set player canPlay prop
  * @return void
  */
  public void setCanPlay(boolean can) {
    this.canPlay = can;
  }

 /**
  * gets player canPlay prop
  * @return boolean
  */
  public boolean getCanPlay() {
    return this.canPlay;
  }

  /**
   * set player canPlay prop
   * @return void
   */
  public void setCanBuild(boolean can) {
    this.canBuild = can;
  }

  /**
   * gets player canPlay prop
   * @return boolean
   */
  public boolean getCanBuild() {
    return this.canBuild;
  }

}
