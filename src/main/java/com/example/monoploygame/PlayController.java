package com.example.monoploygame;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class PlayController implements Initializable {

  static int playerNumbers; // Slider player numbers
  static int defaultTime = 1000; // Animation time
  static int currentPlayerTurnID = 1; // Can play now
  static int nextPlayerTurnID = 2; // Can play next time

  static String p1N, p2N, p3N, p4N; // Player names
  static String defaultColor = "#282828";

  static Player player1, player2, player3, player4; // Players

  static ArrayList<Rectangle> rectBlocks = new ArrayList<Rectangle>(); // All rectangles that contains [Cities, RandomItems, Chests, Taxes]
  static ArrayList<Rectangle> belongsBlocks = new ArrayList<Rectangle>(); // All rectangles that contains [Cities, RandomItems, Chests, Taxes]

  static ArrayList<Player> players = new ArrayList<Player>(); // Players list

  static int[] taxes = {
    150, // Water tax
    300, // Electric tax
    50, // Train monthly tax
    500, // Wife items
  };

  static boolean nextButtonStatus = false; // Disable next button or not

  @FXML
  Rectangle belongsRect1, belongsRect2, belongsRect3, belongsRect4, belongsRect5, belongsRect6, belongsRect7, belongsRect8, belongsRect9, belongsRect10,
            belongsRect11, belongsRect12, belongsRect13, belongsRect14, belongsRect15, belongsRect16, belongsRect17, belongsRect18, belongsRect19, belongsRect20, belongsRect21,
            belongsRect22, belongsRect23, belongsRect24, belongsRect25, belongsRect26, belongsRect27, belongsRect28;

  @FXML
  Group groupPlayer1, groupPlayer2, groupPlayer3, groupPlayer4; // Group of [Player name, Cities, Money, Status] Labels

  @FXML // Labels for every player [Name, Money, Position, Status, Cities]
  Label player1NameLabel, player2NameLabel, player3NameLabel, player4NameLabel,
        player1MoneyLabel, player2MoneyLabel, player3MoneyLabel, player4MoneyLabel,
        player1PositionLabel, player2PositionLabel, player3PositionLabel, player4PositionLabel,
        player1StatusLabel, player2StatusLabel, player3StatusLabel, player4StatusLabel,
        player1CitiesLabel, player2CitiesLabel, player3CitiesLabel, player4CitiesLabel;

  @FXML // Cities Labels [Name]
  Label NewYorkNameLabel, SydneyNameLabel, IstanbulNameLabel, ParisNameLabel,
        BerlinNameLabel, MadridNameLabel, FCBarcelonaNameLabel, LondonNameLabel,
        DCNameLabel, WhiteHouseNameLabel, MumbaiNameLabel, BuenosIrisNameLabel,
        MexicoNameLabel, SevilleNameLabel, ManchesterNameLabel, RomeNameLabel;

  @FXML // Cities Labels [Prices]
  Label NewYorkPriceLabel, SydneyPriceLabel, IstanbulPriceLabel, ParisPriceLabel,
        BerlinPriceLabel, MadridPriceLabel, FCBarcelonaPriceLabel, LondonPriceLabel,
        DCPriceLabel, WhiteHousePriceLabel, MumbaiPriceLabel, BuenosIrisPriceLabel,
        MexicoPriceLabel, SevillePriceLabel, ManchesterPriceLabel, RomePriceLabel;

  @FXML // Dice Label
  Label diceResultLabel;

  @FXML // Rectangles that contain [Cities, RandomItem, Chest, Taxes]
  Rectangle rect1, rect2, rect3, rect4, rect5, rect6, rect7, rect8, rect9, rect10,
            rect11, rect12, rect13, rect14, rect15, rect16, rect17, rect18, rect19, rect20, rect21,
            rect22, rect23, rect24, rect25, rect26, rect27, rect28;

  @FXML // Players blocks
  Rectangle player1Block, player2Block, player3Block, player4Block;

  @FXML // Buttons of playing the game
  Button playButton, nextButton, buyButton, sellButton;

  @Override // When the game gets initilized
  public void initialize(URL url, ResourceBundle resourceBundle) {

    // Creating the players
    player1 = new Player(p1N, 1, "#a433ff");
    player2 = new Player(p2N, 2, "#d2ff1f");
    player3 = new Player(p3N, 3, "#ff1f43");
    player4 = new Player(p4N, 4, "#ff921f");

    // Buttons status which is disabled
    this.setButtonsStatus(false, true, true, true);

    // Adding p1, p2, .... to players ArrayList
    players.add(new Player("development", 0, "#000"));
    players.add(player1);
    players.add(player2);
    players.add(player3);
    players.add(player4);

    belongsBlocks.add(belongsRect1);

    // First player that can play
    player1.setCanPlay(true);

    // Groups Opacity
    this.setPlayersGroupsOpacity(1F, 0.5F, 0.5F, 0.5F);

    // Updating labels, add the blocks to rectBlocks, init groups, init player blocks
    this.addBelongsToAll();
    this.addRectsToAll();
    this.updateAllLabels();
    this.initPlayersNumberGroupVisibility();
    this.initPlayersBlocksVisibilty();
  }

  // When Click "Throw" Button
  public void play() {

    if (player1.getCanPlay() == true) {

      int generatedDiceNum1 = generateDice();

      this.player1.move(generatedDiceNum1);
      this.player1.savePosition();
      this.diceResultLabel.setText(String.valueOf("You rolled: " + generatedDiceNum1 + " !"));
      this.movePlayerBlock(player1Block, player1.getPosition(), 0);
      this.setPlayersStatusOfBuying(true, false, false, false);
      this.setPlayersStatusOfBuilding(true, false, false, false);
      this.setPlayersStatusOfSelling(true, false, false, false);

      player1.setCanPlay(false);

      City city = GameApplication.Cities.stream().filter(c -> c.getID() == player1.getPosition()).findAny().orElse(null);

      if (city != null && city.getOwnerID() != player1.getID() && city.getOwnerID() != 0) {

        System.out.println("Here's the name: " + city.getName());

        Player cityOwner = players.stream().filter(p -> p.getID() == city.getOwnerID()).findAny().orElse(null);

        player1.moneyHistory.add("City taxes: -" + city.taxes + " - Money before Update: " + player1.getMoney());

        player1.setMoney(player1.getMoney() - city.taxes);
        cityOwner.setMoney(cityOwner.getMoney() + city.taxes);

        Message.message("Taxes Paid!", "Player " + player1.name + " has paid fees: " + city.taxes + " to player: " + cityOwner.name);

      }

      switch (player1.getPosition()) {

        // Jail
        case 15:
          player1.setStatus(6);
          player1.setMoney(player1.getMoney() - 1000);
          Message.message("Player: " + player1.name, "Has been entered the jail!");
          break;

        // Send to jail
        case 8:
          player1.setPosition(15);
          player1.setMoney(player1.getMoney() - 1000);
          this.movePlayerBlock(player1Block, 15, 0);
          Message.message("Player: " + player1.name, "Player: " + player2.name + " has been sent to jail");
          break;

        // Chest
        case 11:
        case 27:
          player1.setStatus(4);
          Chest chest1 = new Chest();
          chest1.updateRewards();
          boolean isGenerated = chest1.generateReward(player1);
          if (isGenerated) this.updateBelongsColor(chest1.selectedCity.getID(), player1.color, 1);
          this.updateCitiesLabels();
          break;

        // Random
        case 4:
        case 16:
          RandomItem item1 = new RandomItem();
          item1.randomItem(player1);
          if (item1.selectedCity != null) this.updateBelongsColor(item1.selectedCity.getID(), player1.color, 1);
          this.updateCitiesLabels();
          this.updateMoneyLabels();
          player1.setStatus(5);
          break;

        // Taxes and fees
        case 6: // Wife
          player1.moneyHistory.add("Wife taxes: -" + this.taxes[3] + "- Money before update: " + player1.getMoney());
          player1.setStatus(3);
          player1.setMoney(player1.getMoney() - this.taxes[3]);
          Message.message("Player: " + player1.name, "Has paid " + this.taxes[3] + " for his wife items and shopping cart!");
          break;
        case 25: // Water
          player1.moneyHistory.add("Water Company taxes: -" + this.taxes[3] + "- Money before update: " + player1.getMoney());
          player1.setStatus(3);
          player1.setMoney(player1.getMoney() - this.taxes[0]);
          Message.message("Player: " + player1.name, "Has paid " + this.taxes[0] + " for water company!");
          break;
        case 20: // Train
          player1.moneyHistory.add("Train Transportation Company taxes: -" + this.taxes[3] + "- Money before update: " + player1.getMoney());
          player1.setStatus(3);
          player1.setMoney(player1.getMoney() - this.taxes[2]);
          Message.message("Player: " + player1.name, "Has paid " + this.taxes[0] + " for train company!");
          break;
        case 18:  // Electric Company
          player1.moneyHistory.add("Electric service Company taxes: -" + this.taxes[3] + "- Money before update: " + player1.getMoney());
          player1.setStatus(3);
          player1.setMoney(player1.getMoney() - this.taxes[1]);
          Message.message("Player: " + player1.name, "Has paid " + this.taxes[0] + " for electric company!");
          break;

        // Parking
        case 22:
          player1.setStatus(2);
          Message.message("Player: " + player1.name, player1.name + "has landed on garage!");
          break;

        // Waiting
        case 1:
          player1.setStatus(0);
          break;

        default:
          player1.setStatus(0);
          break;
      }

    }

    if (player2.getCanPlay() == true) {

      int generatedDiceNum2 = generateDice();

      this.player2.move(generatedDiceNum2);
      this.player2.savePosition();
      this.diceResultLabel.setText(String.valueOf("You rolled: " + generatedDiceNum2 + " !"));
      this.movePlayerBlock(player2Block, player2.getPosition(), 15);
      this.setPlayersStatusOfBuying(false, true, false, false);
      this.setPlayersStatusOfBuilding(false, true, false, false);
      this.setPlayersStatusOfSelling(false, true, false, false);

      player2.setCanPlay(false);

      City city = GameApplication.Cities.stream().filter(c -> c.getID() == player2.getPosition()).findAny().orElse(null);

      if (city != null && city.getOwnerID() != player2.getID() && city.getOwnerID() != 0) {

        Player cityOwner = players.stream().filter(p -> p.getID() == city.getOwnerID()).findAny().orElse(null);

        player2.moneyHistory.add("City taxes: -" + city.taxes + " - Money before Update: " + player2.getMoney());

        player2.setMoney(player2.getMoney() - city.taxes);
        cityOwner.setMoney(cityOwner.getMoney() + city.taxes);

        Message.message("Taxes Paid!", "Player " + player2.name + " has paid fees: " + city.taxes + " to player: " + cityOwner.name);

      }

      switch (player2.getPosition()) {

        // Jail
        case 15:
          player2.setStatus(6);
          player2.setMoney(player2.getMoney() - 1000);
          Message.message("Player: " + player2.name, "Has been entered the jail!");
          break;

        // Send to jail
        case 8:
          player2.setPosition(15);
          player2.setMoney(player2.getMoney() - 1000);
          this.movePlayerBlock(player2Block, 15, 15);
          Message.message("Player: " + player2.name, "Player: " + player2.name + " has been sent to jail");
          break;

        // Chest
        case 11:
        case 27:
          player2.setStatus(4);
          Chest chest1 = new Chest();
          chest1.updateRewards();
          boolean isGenerated = chest1.generateReward(player2);
          if (isGenerated) this.updateBelongsColor(chest1.selectedCity.getID(), player2.color, 1);
          this.updateCitiesLabels();
          break;

        // Random
        case 4:
        case 16:
          RandomItem item1 = new RandomItem();
          item1.randomItem(player2);
          if (item1.selectedCity != null) this.updateBelongsColor(item1.selectedCity.getID(), player2.color, 1);
          this.updateCitiesLabels();
          this.updateMoneyLabels();
          player2.setStatus(5);
          break;

        // Taxes and fees
        case 6: // Wife
          player2.moneyHistory.add("Wife taxes: -" + this.taxes[3] + "- Money before update: " + player2.getMoney());
          player2.setStatus(3);
          player2.setMoney(player2.getMoney() - this.taxes[3]);
          Message.message("Player: " + player2.name, "Has paid " + this.taxes[3] + " for his wife items and shopping cart!");
          break;
        case 25: // Water
          player2.moneyHistory.add("Water Company taxes: -" + this.taxes[3] + "- Money before update: " + player2.getMoney());
          player2.setStatus(3);
          player2.setMoney(player2.getMoney() - this.taxes[0]);
          Message.message("Player: " + player2.name, "Has paid " + this.taxes[0] + " for water company!");
          break;
        case 20: // Train
          player2.moneyHistory.add("Train Transportation Company taxes: -" + this.taxes[3] + "- Money before update: " + player2.getMoney());
          player2.setStatus(3);
          player2.setMoney(player2.getMoney() - this.taxes[2]);
          Message.message("Player: " + player2.name, "Has paid " + this.taxes[0] + " for train company!");
          break;
        case 18:  // Electric Company
          player2.savePosition();
          player2.moneyHistory.add("Electric service Company taxes: -" + this.taxes[3] + "- Money before update: " + player2.getMoney());
          player2.setStatus(3);
          player2.setMoney(player2.getMoney() - this.taxes[1]);
          Message.message("Player:" + player2.name, "Has paid " + this.taxes[0] + " for electric company!");
          break;

        // Parking
        case 22:
          player2.setStatus(2);
          Message.message("Player: " + player2.name, player2.name + "has landed on garage!");
          break;

        // Waiting
        case 1:
          player2.setStatus(0);
          break;

        default:
          player2.setStatus(0);
          break;
      }

    }

    if (player3.getCanPlay() == true) {

      int generatedDiceNum3 = generateDice();

      this.player3.move(generatedDiceNum3);
      this.diceResultLabel.setText(String.valueOf("You rolled: " + generatedDiceNum3 + " !"));
      this.movePlayerBlock(player3Block, player3.getPosition(), 30);
      this.setPlayersStatusOfBuying(false, false, true, false);
      this.setPlayersStatusOfBuilding(false, false, true, false);
      this.setPlayersStatusOfSelling(false, false, true, false);

      player3.setCanPlay(false);

      City city = GameApplication.Cities.stream().filter(c -> c.getID() == player3.getPosition()).findAny().orElse(null);

      if (city != null && city.getOwnerID() != player3.getID() && city.getOwnerID() != 0) {

        Player cityOwner = players.stream().filter(p -> p.getID() == city.getOwnerID()).findAny().orElse(null);

        player3.moneyHistory.add("City taxes: -" + city.taxes + " - Money before Update: " + player3.getMoney());

        player3.setMoney(player3.getMoney() - city.taxes);
        cityOwner.setMoney(cityOwner.getMoney() + city.taxes);

        Message.message("Taxes Paid!", "Player " + player3.name + " has paid fees: " + city.taxes + " to player: " + cityOwner.name);

      }

      switch (player3.getPosition()) {

        // Jail
        case 15:
          player3.savePosition();
          player3.setStatus(6);
          player3.setMoney(player3.getMoney() - 1000);
          Message.message("Player: " + player3.name, "Has been entered the jail!");
          break;

        // Send to jail
        case 8:
          player3.savePosition();
          player3.setPosition(15);
          player3.setMoney(player3.getMoney() - 1000);
          this.movePlayerBlock(player3Block, 15, 30);
          Message.message("Player: " + player3.name, "Player: " + player2.name + " has been sent to jail");
          break;

        // Chest
        case 11:
        case 27:
          player3.savePosition();
          player3.setStatus(4);
          Chest chest1 = new Chest();
          chest1.updateRewards();
          boolean isGenerated = chest1.generateReward(player3);
          if (isGenerated) this.updateBelongsColor(chest1.selectedCity.getID(), player3.color, 1);
          this.updateCitiesLabels();
          break;

        // Random
        case 4:
        case 16:
          player3.savePosition();
          RandomItem item1 = new RandomItem();
          item1.randomItem(player3);
          if (item1.selectedCity != null) this.updateBelongsColor(item1.selectedCity.getID(), player3.color, 1);
          this.updateCitiesLabels();
          this.updateMoneyLabels();
          player3.setStatus(5);
          break;

        // Taxes and fees
        case 6: // Wife
          player3.savePosition();
          player3.moneyHistory.add("Wife taxes: -" + this.taxes[3] + "- Money before update: " + player3.getMoney());
          player3.setStatus(3);
          player3.setMoney(player3.getMoney() - this.taxes[3]);
          Message.message("Player: " + player3.name, "Has paid " + this.taxes[3] + " for his wife items and shopping cart!");
          break;
        case 25: // Water
          player3.savePosition();
          player3.moneyHistory.add("Water Company taxes: -" + this.taxes[3] + "- Money before update: " + player3.getMoney());
          player3.setStatus(3);
          player3.setMoney(player3.getMoney() - this.taxes[0]);
          Message.message("Player: " + player3.name, "Has paid " + this.taxes[0] + " for water company!");
          break;
        case 20: // Train
          player3.savePosition();
          player3.moneyHistory.add("Train Transportation Company taxes: -" + this.taxes[3] + "- Money before update: " + player3.getMoney());
          player3.setStatus(3);
          player3.setMoney(player3.getMoney() - this.taxes[2]);
          Message.message("Player: " + player3.name, "Has paid " + this.taxes[0] + " for train company!");
          break;
        case 18:  // Electric Company
          player3.savePosition();
          player3.moneyHistory.add("Electric service Company taxes: -" + this.taxes[3] + "- Money before update: " + player3.getMoney());
          player3.setStatus(3);
          player3.setMoney(player3.getMoney() - this.taxes[1]);
          Message.message("Player: " + player3.name, "Has paid " + this.taxes[0] + " for electric company!");
          break;

        // Parking
        case 22:
          player3.savePosition();
          player3.setStatus(2);
          Message.message("Player: " + player3.name, player3.name + "has landed on garage!");
          break;

        // Waiting
        case 1:
          player3.savePosition();
          player3.setStatus(0);
          break;

        default:
          player3.setStatus(0);
          break;
      }

    }

    if (player4.getCanPlay() == true) {

      int generatedDiceNum4 = generateDice();

      this.player4.move(generatedDiceNum4);
      this.diceResultLabel.setText(String.valueOf("You rolled: " + generatedDiceNum4 + " !"));
      this.movePlayerBlock(player4Block, player4.getPosition(), 45);
      this.setPlayersStatusOfBuying(false, false, false, true);
      this.setPlayersStatusOfBuilding(false, false, false, true);
      this.setPlayersStatusOfSelling(false, false, false, true);

      player4.setCanPlay(false);

      City city = GameApplication.Cities.stream().filter(c -> c.getID() == player4.getPosition()).findAny().orElse(null);

      if (city != null && city.getOwnerID() != player4.getID() && city.getOwnerID() != 0) {

        Player cityOwner = players.stream().filter(p -> p.getID() == city.getOwnerID()).findAny().orElse(null);

        player4.moneyHistory.add("City taxes: -" + city.taxes + " - Money before Update: " + player4.getMoney());

        player4.setMoney(player4.getMoney() - city.taxes);
        cityOwner.setMoney(cityOwner.getMoney() + city.taxes);

        Message.message("Taxes Paid!", "Player " + player4.name + " has paid fees: " + city.taxes + " to player: " + cityOwner.name);

      }

      switch (player4.getPosition()) {

        // Jail
        case 15:
          player4.savePosition();
          player4.setStatus(6);
          player4.setMoney(player4.getMoney() - 1000);
          Message.message("Player: " + player4.name, "Has been entered the jail!");
          break;

        // Send to jail
        case 8:
          player4.savePosition();
          player4.setPosition(15);
          player4.setMoney(player4.getMoney() - 1000);
          this.movePlayerBlock(player4Block, 15, 45);
          Message.message("Player: " + player4.name, "Player: " + player4.name + " has been sent to jail");
          break;

        // Chest
        case 11:
        case 27:
          player4.savePosition();
          player4.setStatus(4);
          Chest chest1 = new Chest();
          chest1.updateRewards();
          boolean isGenerated = chest1.generateReward(player4);
          if (isGenerated) this.updateBelongsColor(chest1.selectedCity.getID(), player4.color, 1);
          this.updateCitiesLabels();
          break;

        // Random
        case 4:
        case 16:
          player4.savePosition();
          RandomItem item1 = new RandomItem();
          item1.randomItem(player4);
          if (item1.selectedCity != null) this.updateBelongsColor(item1.selectedCity.getID(), player4.color, 1);
          this.updateCitiesLabels();
          this.updateMoneyLabels();
          player4.setStatus(5);
          break;

        // Taxes and fees
        case 6: // Wife
          player4.savePosition();
          player4.moneyHistory.add("Wife taxes: -" + this.taxes[3] + "- Money before update: " + player4.getMoney());
          player4.setStatus(3);
          player4.setMoney(player4.getMoney() - this.taxes[3]);
          Message.message("Player: " + player4.name, "Has paid " + this.taxes[3] + " for his wife items and shopping cart!");
          break;
        case 25: // Water
          player4.savePosition();
          player4.moneyHistory.add("Water Company taxes: -" + this.taxes[3] + "- Money before update: " + player4.getMoney());
          player4.setStatus(3);
          player4.setMoney(player4.getMoney() - this.taxes[0]);
          Message.message("Player: " + player4.name, "Has paid " + this.taxes[0] + " for water company!");
          break;
        case 20: // Train
          player4.savePosition();
          player4.moneyHistory.add("Train Transportation Company taxes: -" + this.taxes[3] + "- Money before update: " + player4.getMoney());
          player4.setStatus(3);
          player4.setMoney(player4.getMoney() - this.taxes[2]);
          Message.message("Player: " + player4.name, "Has paid " + this.taxes[0] + " for train company!");
          break;
        case 18:  // Electric Company
          player4.savePosition();
          player4.moneyHistory.add("Electric service Company taxes: -" + this.taxes[3] + "- Money before update: " + player4.getMoney());
          player4.setStatus(3);
          player4.setMoney(player4.getMoney() - this.taxes[1]);
          Message.message("Player: " + player4.name, "Has paid " + this.taxes[0] + " for electric company!");
          break;

        // Parking
        case 22:
          player4.savePosition();
          player4.setStatus(2);
          Message.message("Player: " + player4.name, player4.name + "has landed on garage!");
          break;

        // Waiting
        case 1:
          player4.savePosition();
          player4.setStatus(0);
          break;

        default:
          player4.setStatus(0);
          break;
      }

    }

    nextButtonStatus = true;

    this.setButtonsStatus(true, false, false, false);
    this.updateMoneyLabels();
    this.updateStatusLabels();
    this.updatePositionLabels();
  }

  // When Click "Next" Button
  public void next() {
    if (nextButtonStatus) {
      this.updatePlayerTurn();
      this.setButtonsStatus(true, false, false, false);
    } else {
      this.updatePlayerTurn();
    }
    this.setButtonsStatus(false, true, true, true);
    nextButtonStatus = false;
  }

  // When Click "Buy" Button
  public void buy() {

    for (Player player: players) {

      City city = GameApplication.Cities.stream().filter(c -> c.getID() == player.getPosition() && c.getIsAvailable() == true).findAny().orElse(null);

      if (player.getCanBuy() == true && player.getCanPlay() == false && player.getID() != 0) {

        boolean doneBuying = player.buyCity(city);

        if (!doneBuying) {
          this.next();
        }

        this.updateBelongsColor(city.getID(), player.color, 1);
        this.setButtonsStatus(false, true, true, true);
        this.next();

      }

    }

    nextButtonStatus = false;

    this.updateCitiesLabels();
    this.updateMoneyLabels();
  }

  // When Click "Sell" Button
  public void sell() {

    for (Player player: players) {

      City city = GameApplication.Cities.stream().filter(c -> c.getID() == player.getPosition() && c.getIsAvailable() == false).findAny().orElse(null);

      if (player.getCanSell() == true && player.getCanPlay() == false && player.getID() != 0) {

        boolean doneSelling = player.sellCity(city);

        if (!doneSelling) {
          this.next();
        }

        this.updateBelongsColor(city.getID(), defaultColor, 0);
        this.setButtonsStatus(false, true, true, true);
        this.next();

      }
    }

    nextButtonStatus = false;

    this.updateCitiesLabels();
    this.updateMoneyLabels();

  }

  // When Click "Build Hotel" button
  public void buildHotel() {

    for (Player player: players) {

      if (player.getCanBuild() == true && player.getCanPlay() == false) {

        City city = GameApplication.Cities.stream().filter(c -> c.getID() == player.getPosition()).findAny().orElse(null);

        Build build = new Build();

        build.build(BuildingTypes.Hotel, city, player);

      }

    }

    nextButtonStatus = false;

    this.updateCitiesLabels();
    this.updateMoneyLabels();

  }

  // When Click "Build Hotel" button
  public void buildGarage() {

    for (Player player: players) {

      if (player.getCanBuild() == true && player.getCanPlay() == false) {

        City city = GameApplication.Cities.stream().filter(c -> c.getID() == player.getPosition()).findAny().orElse(null);

        Build build = new Build();

        build.build(BuildingTypes.Garage, city, player);

      }

    }

  }

  // Showing History
  public void showP1MoneyHistory() {
    String text = "";
    if (player1.moneyHistory.size() > 0) {
      for (String i: player1.moneyHistory) {
        text += i + "\n";
      }
      Message.message("Player 1: Money History", text);
    } else {
      Message.message("Player 1: Money History", "No History Found!");
    }
  }
  public void showP2MoneyHistory() {
    String text = "";
    if (player2.moneyHistory.size() > 0) {
      for (String i: player2.moneyHistory) {
        text += i + "\n";
      }
      Message.message("Player 2 - Money History", text);
    } else {
      Message.message("Player 2 - Money History", "No History Found!");
    }
  }
  public void showP3MoneyHistory() {
    String text = "";
    if (player3.moneyHistory.size() > 0) {
      for (String i: player3.moneyHistory) {
        text += i + "\n";
      }
      Message.message("Player 3 - Money History", text);
    } else {
      Message.message("Player 3 - Money History", "No History Found!");
    }
  }
  public void showP4MoneyHistory() {
    String text = "";
    if (player4.moneyHistory.size() > 0) {
      for (String i: player4.moneyHistory) {
        text += i + "\n";
      }
      Message.message("Player 4 - Money History", text);
    } else {
      Message.message("Player 4 - Money History", "No History Found!");
    }
  }

  public void showP1PositionHistory() {
    String text = "";
    if (player1.positionHistory.size() > 0) {
      for (String i: player1.positionHistory) {
        text += i + "\n";
      }
      Message.message("Player 1: Position History", text);
    } else {
      Message.message("Player 1: Position History", "No History Found!");
    }
  }
  public void showP2PositionHistory() {
    String text = "";
    if (player2.positionHistory.size() > 0) {
      for (String i: player2.positionHistory) {
        text += i + "\n";
      }
      Message.message("Player 2 - Position History", text);
    } else {
      Message.message("Player 2 - Position History", "No History Found!");
    }
  }
  public void showP3PositionHistory() {
    String text = "";
    if (player3.positionHistory.size() > 0) {
      for (String i: player3.positionHistory) {
        text += i + "\n";
      }
      Message.message("Player 3 - Position History", text);
    } else {
      Message.message("Player 3 - Position History", "No History Found!");
    }
  }
  public void showP4PositionHistory() {
    String text = "";
    if (player4.positionHistory.size() > 0) {
      for (String i: player4.positionHistory) {
        text += i + "\n";
      }
      Message.message("Player 4 - Position History", text);
    } else {
      Message.message("Player 4 - Position History", "No History Found!");
    }
  }

  // Generate dice number
  public int generateDice() {
    Random r = new Random();
    return r.nextInt((6 - 1) + 1) + 1;
  }

  // Update "Next" button status
  public void updateNextButtonStatus() {
    if (nextButtonStatus == true) {
      nextButtonStatus = false;
    } else {
      nextButtonStatus = true;
    }
  }

  // Groups visibilty
  public void initPlayersNumberGroupVisibility() {
    if (playerNumbers == 2) {
      this.groupPlayer3.setVisible(false);
      this.groupPlayer4.setVisible(false);
    }
    if (playerNumbers >= 3) {
      this.groupPlayer3.setVisible(true);
    }
    if (playerNumbers > 3) {
      this.groupPlayer4.setVisible(true);
    }

  }

  // Player blocks visibilty
  public void initPlayersBlocksVisibilty() {
    if (playerNumbers == 2) {
      this.player3Block.setVisible(false);
      this.player4Block.setVisible(false);
    }
    if (playerNumbers == 3) {
      this.player3Block.setVisible(true);
    }
    if (playerNumbers == 4) {
      this.player4Block.setVisible(true);
    }
  }

  // Changing current player turn to next
  public void updatePlayerTurn() {

    switch (playerNumbers) {

      case 2:
        if (nextPlayerTurnID == 2) {
          this.setCurrentPlayerTurnID(2);
          this.setNextPlayerTurn(1);
          setPlayersStatusOfPlaying(false, true, false, false);
          setPlayersGroupsOpacity(0.5F, 1F, 0.5F, 0.5F);
        } else if (nextPlayerTurnID == 1) {
          this.setCurrentPlayerTurnID(1);
          this.setNextPlayerTurn(2);
          setPlayersStatusOfPlaying(true, false, false, false);
          setPlayersGroupsOpacity(1F, 0.5F, 0.5F, 0.5F);
        }
        break;

      case 3:
        if (nextPlayerTurnID == 2) {
          this.setCurrentPlayerTurnID(2);
          this.setNextPlayerTurn(3);
          setPlayersStatusOfPlaying(false, true, false, false);
          setPlayersGroupsOpacity(0.5F, 1F, 0.5F, 0.5F);
        } else if (nextPlayerTurnID == 3) {
          this.setCurrentPlayerTurnID(3);
          this.setNextPlayerTurn(1);
          setPlayersStatusOfPlaying(false, false, true, false);
          setPlayersGroupsOpacity(0.5F, 0.5F, 1F, 0.5F);
        }
        else if (nextPlayerTurnID == 1) {
          this.setCurrentPlayerTurnID(1);
          this.setNextPlayerTurn(2);
          setPlayersStatusOfPlaying(true, false, false, false);
          setPlayersGroupsOpacity(1F, 0.5F, 0.5F, 0.5F);
        }
        break;

      case 4:
        if (nextPlayerTurnID == 2) {
          this.setCurrentPlayerTurnID(2);
          this.setNextPlayerTurn(3);
          player2.setCanPlay(true);
          setPlayersStatusOfPlaying(false, true, false, false);
          setPlayersGroupsOpacity(0.5F, 1F, 0.5F, 0.5F);
        } else if (nextPlayerTurnID == 3) {
          this.setCurrentPlayerTurnID(3);
          this.setNextPlayerTurn(4);
          setPlayersStatusOfPlaying(false, false, true, false);
          setPlayersGroupsOpacity(0.5F, 0.5F, 1F, 0.5F);
        } else if (nextPlayerTurnID == 4) {
          this.setCurrentPlayerTurnID(4);
          this.setNextPlayerTurn(1);
          player4.setCanPlay(true);
          setPlayersStatusOfPlaying(false, false, false, true);
          setPlayersGroupsOpacity(0.5F, 0.5F, 0.5F, 1F);
        } else if (nextPlayerTurnID == 1) {
          this.setCurrentPlayerTurnID(1);
          this.setNextPlayerTurn(2);
          setPlayersStatusOfPlaying(true, false, false, false);
          setPlayersGroupsOpacity(1F, 0.5F, 0.5F, 0.5F);
          setPlayersGroupsOpacity(1F, 0.5F, 0.5F, 0.5F);
        }
        break;
    }

  }

 /**
  * Animation of moving the block of the player
  * @param block the player block to move
  * @param playerPosition to where the block will move.
  * @param shift to shift the blocks from each other
  * @return void
  */
  public void movePlayerBlock(Rectangle block, int playerPosition, int shift) {
    TranslateTransition tt = new TranslateTransition(Duration.millis(this.defaultTime), block);
    tt.jumpTo(Duration.ZERO);
    tt.setToX((float) -1 * Math.abs(rectBlocks.get(playerPosition - 1).getLayoutX() - block.getLayoutX() + shift));
    tt.setToY((float) -1 * Math.abs(rectBlocks.get(playerPosition - 1).getLayoutY() - block.getLayoutY()));
    tt.play();
  }

  // Adding rectangles to rectBlocks arraylist
  public void addRectsToAll() {
    this.rectBlocks.add(rect1);
    this.rectBlocks.add(rect2);
    this.rectBlocks.add(rect3);
    this.rectBlocks.add(rect4);
    this.rectBlocks.add(rect5);
    this.rectBlocks.add(rect6);
    this.rectBlocks.add(rect7);
    this.rectBlocks.add(rect8);
    this.rectBlocks.add(rect9);
    this.rectBlocks.add(rect10);
    this.rectBlocks.add(rect11);
    this.rectBlocks.add(rect12);
    this.rectBlocks.add(rect13);
    this.rectBlocks.add(rect14);
    this.rectBlocks.add(rect15);
    this.rectBlocks.add(rect16);
    this.rectBlocks.add(rect17);
    this.rectBlocks.add(rect18);
    this.rectBlocks.add(rect19);
    this.rectBlocks.add(rect20);
    this.rectBlocks.add(rect21);
    this.rectBlocks.add(rect22);
    this.rectBlocks.add(rect23);
    this.rectBlocks.add(rect24);
    this.rectBlocks.add(rect25);
    this.rectBlocks.add(rect26);
    this.rectBlocks.add(rect27);
    this.rectBlocks.add(rect28);
  }

  // Adding belongs blocks to belongsBlocks
  public void addBelongsToAll() {
    belongsBlocks.add(belongsRect1);
    belongsBlocks.add(belongsRect2);
    belongsBlocks.add(belongsRect3);
    belongsBlocks.add(belongsRect4);
    belongsBlocks.add(belongsRect5);
    belongsBlocks.add(belongsRect6);
    belongsBlocks.add(belongsRect7);
    belongsBlocks.add(belongsRect8);
    belongsBlocks.add(belongsRect9);
    belongsBlocks.add(belongsRect10);
    belongsBlocks.add(belongsRect11);
    belongsBlocks.add(belongsRect12);
    belongsBlocks.add(belongsRect13);
    belongsBlocks.add(belongsRect14);
    belongsBlocks.add(belongsRect15);
    belongsBlocks.add(belongsRect16);
    belongsBlocks.add(belongsRect17);
    belongsBlocks.add(belongsRect18);
    belongsBlocks.add(belongsRect19);
    belongsBlocks.add(belongsRect20);
    belongsBlocks.add(belongsRect21);
    belongsBlocks.add(belongsRect22);
    belongsBlocks.add(belongsRect23);
    belongsBlocks.add(belongsRect24);
    belongsBlocks.add(belongsRect25);
    belongsBlocks.add(belongsRect26);
    belongsBlocks.add(belongsRect27);
    belongsBlocks.add(belongsRect28);
  }

  // Update belongs rectangle
  public void updateBelongsColor(int position, String color, double opacity) {
    belongsBlocks.get(position).setOpacity(opacity);
    belongsBlocks.get(position).setFill(Color.web(color));
  }

  // Updating all labels
  public void updateAllLabels() {
    this.initCitiesPricesUI();
    this.initCitiesNamesUI();
    this.updatePlayersNamesLabels();
    this.updateMoneyLabels();
    this.updatePositionLabels();
    this.updateStatusLabels();
    this.updateCitiesLabels();
  }

  // Updating names labels
  public void updatePlayersNamesLabels() {
    player1NameLabel.setText(p1N);
    player2NameLabel.setText(p2N);
    player3NameLabel.setText(p3N);
    player4NameLabel.setText(p4N);
  }

  // Updating money labels
  public void updateMoneyLabels() {
    player1MoneyLabel.setText(String.valueOf(player1.getMoney() + " $"));
    player2MoneyLabel.setText(String.valueOf(player2.getMoney() + " $"));
    player3MoneyLabel.setText(String.valueOf(player3.getMoney() + " $"));
    player4MoneyLabel.setText(String.valueOf(player4.getMoney() + " $"));
  }

  // Updating position labels
  public void updatePositionLabels() {
    player1PositionLabel.setText(String.valueOf(player1.getPosition()));
    player2PositionLabel.setText(String.valueOf(player2.getPosition()));
    player3PositionLabel.setText(String.valueOf(player3.getPosition()));
    player4PositionLabel.setText(String.valueOf(player4.getPosition()));
  }

  // Updating status labels
  public void updateStatusLabels() {
    player1StatusLabel.setText(Player.statusReference[player1.getStatus()]);
    player2StatusLabel.setText(Player.statusReference[player2.getStatus()]);
    player3StatusLabel.setText(Player.statusReference[player3.getStatus()]);
    player4StatusLabel.setText(Player.statusReference[player4.getStatus()]);
  }

  // Updating cities labels
  public void updateCitiesLabels() {
    player1CitiesLabel.setText(player1.getCities());
    player2CitiesLabel.setText(player2.getCities());
    player3CitiesLabel.setText(player3.getCities());
    player4CitiesLabel.setText(player4.getCities());
  }

  // Showing cities names in the UI
  public void initCitiesNamesUI() {
    this.NewYorkNameLabel.setText(GameApplication.NewYorkCity.name);
    this.SydneyNameLabel.setText(GameApplication.SydneyCity.name);
    this.IstanbulNameLabel.setText(GameApplication.IstanbulCity.name);
    this.ParisNameLabel.setText(GameApplication.ParisCity.name);
    this.BerlinNameLabel.setText(GameApplication.BerlinCity.name);
    this.MadridNameLabel.setText(GameApplication.MadridCity.name);
    this.FCBarcelonaNameLabel.setText(GameApplication.FCBarcelonaCity.name);
    this.LondonNameLabel.setText(GameApplication.LondonCity.name);
    this.DCNameLabel.setText(GameApplication.DCCity.name);
    this.WhiteHouseNameLabel.setText(GameApplication.WhiteHouseCity.name);
    this.MumbaiNameLabel.setText(GameApplication.MumbaiCity.name);
    this.BuenosIrisNameLabel.setText(GameApplication.BuenosIrisCity.name);
    this.MexicoNameLabel.setText(GameApplication.MexicoCity.name);
    this.SevilleNameLabel.setText(GameApplication.SevilleCity.name);
    this.ManchesterNameLabel.setText(GameApplication.ManchesterCity.name);
    this.RomeNameLabel.setText(GameApplication.RomeCity.name);
  }

  // Showing cities prices in the UI
  public void initCitiesPricesUI() {
    this.NewYorkPriceLabel.setText(String.valueOf(GameApplication.NewYorkCity.getPrice() + " $"));
    this.SydneyPriceLabel.setText(String.valueOf(GameApplication.SydneyCity.getPrice() + " $"));
    this.IstanbulPriceLabel.setText(String.valueOf(GameApplication.IstanbulCity.getPrice() + " $"));
    this.ParisPriceLabel.setText(String.valueOf(GameApplication.ParisCity.getPrice() + " $"));
    this.BerlinPriceLabel.setText(String.valueOf(GameApplication.BerlinCity.getPrice() + " $"));
    this.MadridPriceLabel.setText(String.valueOf(GameApplication.MadridCity.getPrice() + " $"));
    this.FCBarcelonaPriceLabel.setText(String.valueOf(GameApplication.FCBarcelonaCity.getPrice() + " $"));
    this.LondonPriceLabel.setText(String.valueOf(GameApplication.LondonCity.getPrice() + " $"));
    this.DCPriceLabel.setText(String.valueOf(GameApplication.DCCity.getPrice() + " $"));
    this.WhiteHousePriceLabel.setText(String.valueOf(GameApplication.WhiteHouseCity.getPrice() + " $"));
    this.MumbaiPriceLabel.setText(String.valueOf(GameApplication.MumbaiCity.getPrice() + " $"));
    this.BuenosIrisPriceLabel.setText(String.valueOf(GameApplication.BuenosIrisCity.getPrice() + " $"));
    this.MexicoPriceLabel.setText(String.valueOf(GameApplication.MexicoCity.getPrice() + " $"));
    this.SevillePriceLabel.setText(String.valueOf(GameApplication.SevilleCity.getPrice() + " $"));
    this.ManchesterPriceLabel.setText(String.valueOf(GameApplication.ManchesterCity.getPrice() + " $"));
    this.RomePriceLabel.setText(String.valueOf(GameApplication.RomeCity.getPrice() + " $"));
  }

 /**
  * Update next player turm
  * @param ID accepts next player id
  * @return void
  */
  public void setNextPlayerTurn(int ID) {
    this.nextPlayerTurnID = ID;
  }

 /**
  * Update current player turm
  * @param ID accepts next player id
  * @return void
  */
  public void setCurrentPlayerTurnID(int ID) {
    this.currentPlayerTurnID = ID;
  }

 /**
  * Update who can play
  * @param p1 accepts true if player1 can play
  * @param p2 accepts true if player2 can play
  * @param p3 accepts true if player3 can play
  * @param p4 accepts true if player4 can play
  * @return void
  */
  public void setPlayersStatusOfPlaying(boolean p1, boolean p2, boolean p3, boolean p4) {
    player1.setCanPlay(p1);
    player2.setCanPlay(p2);
    player3.setCanPlay(p3);
    player4.setCanPlay(p4);
  }

 /**
  * Update who can buy
  * @param p1 accepts true if player1 can buy
  * @param p2 accepts true if player2 can buy
  * @param p3 accepts true if player3 can buy
  * @param p4 accepts true if player4 can buy
  * @return void
  */
  public void setPlayersStatusOfBuying(boolean p1, boolean p2, boolean p3, boolean p4) {
    player1.setCanBuy(p1);
    player2.setCanBuy(p2);
    player3.setCanBuy(p3);
    player4.setCanBuy(p4);
  }

 /**
  * Update who can sell
  * @param p1 accepts true if player1 can sell
  * @param p2 accepts true if player2 can sell
  * @param p3 accepts true if player3 can sell
  * @param p4 accepts true if player4 can sell
  * @return void
  */
  public void setPlayersStatusOfSelling(boolean p1, boolean p2, boolean p3, boolean p4) {
    player1.setCanSell(p1);
    player2.setCanSell(p2);
    player3.setCanSell(p3);
    player4.setCanSell(p4);
  }

 /**
  * Update who can build
  * @param p1 accepts true if player1 can build
  * @param p2 accepts true if player2 can build
  * @param p3 accepts true if player3 can build
  * @param p4 accepts true if player4 can build
  * @return void
  */
  public void setPlayersStatusOfBuilding(boolean p1, boolean p2, boolean p3, boolean p4) {
    player1.setCanBuild(p1);
    player2.setCanBuild(p2);
    player3.setCanBuild(p3);
    player4.setCanBuild(p4);
  }

 /**
  * Update groups opacity
  * @param p1 for player1 group
  * @param p2 for player2 group
  * @param p3 for player3 group
  * @param p4 for player4 group
  * @return void
  */
  public void setPlayersGroupsOpacity(float p1, float p2, float p3, float p4) {
    groupPlayer1.setOpacity(p1);
    groupPlayer2.setOpacity(p2);
    groupPlayer3.setOpacity(p3);
    groupPlayer4.setOpacity(p4);
  }

 /**
  * Update buttons status of playing the game
  * @param play button status
  * @param next button status
  * @param buy button status
  * @param sell button status
  * @return void
  */
  public void setButtonsStatus(boolean play, boolean next, boolean buy, boolean sell) {
    playButton.setDisable(play);
    nextButton.setDisable(next);
    buyButton.setDisable(buy);
    sellButton.setDisable(sell);
  }
}
