package com.example.monoploygame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class GameApplication extends Application {

  // Array of all current cities
  static ArrayList<City> Cities = new ArrayList<City>();

  // Sold cities
  static ArrayList<City> SoldCities = new ArrayList<City>();

  // Creating all cities
  static City NewYorkCity                   = new City("New", 2, 1500, 150);
  static City SydneyCity                    = new City("Sydney", 3, 2300, 250);
  static City IstanbulCity                  = new City("Istanbul", 5, 2700, 300);
  static City ParisCity                     = new City("Paris", 7, 2000, 100);
  static City BerlinCity                    = new City("Berlin", 9, 1500, 250);
  static City MadridCity                    = new City("Madrid", 10, 1000, 750);
  static City FCBarcelonaCity               = new City("Barca", 12, 2100, 2000);
  static City LondonCity                    = new City("London", 13, 1200, 350);
  static City DCCity                        = new City("DC", 14, 1000, 700);
  static City WhiteHouseCity                = new City("WhiteH", 17, 1750, 100);
  static City MumbaiCity                    = new City("Mumbai", 19, 1300, 300);
  static City BuenosIrisCity                = new City("Buenos", 21, 3500, 500);
  static City MexicoCity                    = new City("Mexico", 23, 1100, 600);
  static City SevilleCity                   = new City("Seville", 24, 1700, 100);
  static City ManchesterCity                = new City("Man", 26, 1000, 300);
  static City RomeCity                      = new City("Rome", 28, 2000, 150);

  @Override
  public void start(Stage stage) throws IOException {

    // Adding Cities
    this.Cities.add(NewYorkCity);
    this.Cities.add(SydneyCity);
    this.Cities.add(IstanbulCity);
    this.Cities.add(ParisCity);
    this.Cities.add(BerlinCity);
    this.Cities.add(MadridCity);
    this.Cities.add(FCBarcelonaCity);
    this.Cities.add(LondonCity);
    this.Cities.add(DCCity);
    this.Cities.add(WhiteHouseCity);
    this.Cities.add(MumbaiCity);
    this.Cities.add(BuenosIrisCity);
    this.Cities.add(MexicoCity);
    this.Cities.add(SevilleCity);
    this.Cities.add(ManchesterCity);
    this.Cities.add(RomeCity);

    // Loading the starter.fxml file to show in the scene
    FXMLLoader fx = new FXMLLoader(GameApplication.class.getResource("starter.fxml"));

    Scene scene = new Scene(fx.load(), 918, 728);

    // Adding Css file to the scene
    scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());

    stage.setTitle("Monopoly Game");
    stage.setResizable(false);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
