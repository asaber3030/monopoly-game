package com.example.monoploygame;

import java.util.ArrayList;
import java.util.Random;

public class Chest {

  // The available cities to reward to players!
  public static ArrayList<City> rewardsCities = new ArrayList<City>();

  public City selectedCity;

 /**
  * Updating current cities that was sold or not
  * @return void
  */
  public void updateRewards() {
    for (City city: GameApplication.Cities) {
      if (city.getIsAvailable() && !rewardsCities.contains(city)) {
        rewardsCities.add(city);
      }
    }
  }

 /**
  * Generating the reward(city) for specific player!
  * @param player an Object from Player class. The passed player to the method he'll be rewarded
  * @return void
  */
  public boolean generateReward(Player player) {

    // Indices of cities
    int startN = 0;
    int endN = rewardsCities.size() - 1;

    // Updating available cities
    this.updateRewards();

    if (rewardsCities.size() > 0) {

      Random rand = new Random();
      int generatedCityNum = rand.nextInt(endN - startN + 1) + startN; // The random city number from (rewardsCities)

      City city = rewardsCities.get(generatedCityNum); // Getting the city

      if (city.getIsAvailable() && city != null) { // If the city was found

        this.selectedCity = city;

        // Add the city to the player, Remove from rewards, show message
        player.addCity(city);
        rewardsCities.remove(city);

        Message.message("Chest found!!", "Player: " + player.name + " has been rewarded with city: " + city.getName());
        return true;
      } else {
        Message.message("Chest!", "Better luck next time!");
        return false;
      }
    } else {
      Message.message("Chest!", "No more cities!");
      return false;
    }

  }
}
