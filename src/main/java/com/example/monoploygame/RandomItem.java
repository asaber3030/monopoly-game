package com.example.monoploygame;

import java.util.Random;

public class RandomItem {

  // Money rewards
  int[] moneyRewards = {
    0,
    200,
    1200,
    400,
    800,
    3000,
    100,
    2500,
    300,
    1000
  };

  public City selectedCity = null;

 /**
  * The passed player will be rewarded something
  * @param player accepts an object from Player class
  * @return void
  */
  public void randomItem(Player player) {

    Random r = new Random();

    int moneyOrChest = (int) (Math.random() * (3 - 1)) + 1; // Random integer to select the random reward: [ 1 => New City, 2 => +Money, 3 => Nothing ]

    if (moneyOrChest == 1) { // chest

      // Creating new Chest and reward it to the player
      Chest chest = new Chest();
      chest.updateRewards();
      chest.generateReward(player);

      selectedCity = chest.selectedCity;

    } else if (moneyOrChest == 2) { // money

      // Generating random index from this.moneyRewards
      int randomIndex = (int) (Math.random() * (9 - 1)) + 1;

      // Getting the reward
      int randomMoney = moneyRewards[randomIndex];

      // Showing the alert
      Message.message("Random Item Reward!", "Player: " + player.name + " has been awarded money!\nMoney: " + randomMoney);

      // Saving history
      player.moneyHistory.add("Gift: " +  randomMoney + " - Money Before update: " + player.getMoney());

      // Update player money
      player.setMoney(player.getMoney() + randomMoney);

    } else if (moneyOrChest == 3) { // Nothing

      Message.message("Random Item Reward!", "Better luck next time :( \nPlayer: " + player.name + " has been awarded Nothing!!");


    }

  }

}
