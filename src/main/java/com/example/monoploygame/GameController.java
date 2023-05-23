package com.example.monoploygame;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class GameController implements Initializable {

  private boolean validateStatus = true; // Checks if the player can create the game or not.

  public Stage stage; // Current stage
  public Scene scene; // Current Scene

  @FXML
  public AnchorPane anPane; // Container of the first scene

  @FXML
  public Label playersNumberLabel; // Shows "Two players", "Three players", "Four players"

  @FXML
  public Slider playersSlider; // Selecting players numbers

  @FXML
  public Button createGameBtn, exitBtn, clearBtn; // Main buttons

  @FXML
  public TextField player1In, player2In, player3In, player4In; // TextFields of players names

  @FXML // When Click createGameBtn
  private void createGame(ActionEvent event) throws IOException {

    // Getting the inserted players names
    String player1Name = player1In.getText();
    String player2Name = player2In.getText();
    String player3Name = player3In.getText();
    String player4Name = player4In.getText();

    int playersNo = (int) playersSlider.getValue();
    PlayController.playerNumbers = playersNo; // setting playerNumbers in PlayController to the slider value

    // Alert of validation
    Alert errorsAlert = new Alert(Alert.AlertType.ERROR);
    errorsAlert.setTitle("Validation Errors");

    // Checking for players names is it empty or not
    if (playersNo == 2) {
      if (player1Name.isEmpty() || player2Name.isEmpty()) {
         validateStatus = false;
         errorsAlert.setContentText("Check for players names");
         errorsAlert.show();
      } else {
        // p1N & p2N [The names of the players]
        PlayController.p1N = player1In.getText();
        PlayController.p2N = player2In.getText();
      }
    }

    if (playersNo == 3) {
      if (player1Name.isEmpty() || player2Name.isEmpty() || player3Name.isEmpty()) {
        validateStatus = false;
        errorsAlert.setContentText("Check for players names");
        errorsAlert.show();
      } else {
        PlayController.p1N = player1In.getText();
        PlayController.p2N = player2In.getText();
        PlayController.p3N = player3In.getText();
      }
    }

    if (playersNo == 4) {
      if (player1Name.isEmpty() || player2Name.isEmpty() || player3Name.isEmpty() || player4Name.isEmpty()) {
        validateStatus = false;
        errorsAlert.setContentText("Check for players names");
        errorsAlert.show();
      } else {
        PlayController.p1N = player1In.getText();
        PlayController.p2N = player2In.getText();
        PlayController.p3N = player3In.getText();
        PlayController.p4N = player4In.getText();
      }
    }

    // if the validation succeeded!
    if (validateStatus) {

      // Changing to game.fxml scene
      Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));

      stage = (Stage)((Node)event.getSource()).getScene().getWindow();
      stage.setTitle("Playing...");

      // Getting the resolution of the current screen
      Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

      // Make the stage fit the screen
      stage.setX(primaryScreenBounds.getMinX());
      stage.setY(primaryScreenBounds.getMinY());
      stage.setWidth(primaryScreenBounds.getWidth());
      stage.setHeight(primaryScreenBounds.getHeight());

      scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
      stage.setScene(scene);
      stage.show();
    }

  }

  @FXML
  private void exitGame() { // When click exitGameBtn
    stage = (Stage) anPane.getScene().getWindow();
    stage.close();
  }

  @FXML
  private void clearPlayersNames() { // When click exitGameBtn
    player1In.setText("");
    player2In.setText("");
    player3In.setText("");
    player4In.setText("");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) { // When the "GameController" created

    playersSlider.setShowTickMarks(true);
    playersSlider.setMax(4.0);
    playersSlider.setMin(2.0);

    playersSlider.valueProperty().addListener(new ChangeListener<Number>() { // When the slider of playersNumbers changes

      @Override
      public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {

        int playersNo = (int) playersSlider.getValue();

        // Updating Label "playersNumberLabel" with the texts below

        switch (playersNo) {
          case 2:
            playersNumberLabel.setText("Two Players");
            player3In.setDisable(true);
            player4In.setDisable(true);
            break;

          case 3:
            playersNumberLabel.setText("Three Players");
            player3In.setDisable(false);
            player4In.setDisable(true);
            break;

          case 4:
            playersNumberLabel.setText("Four Players");
            player3In.setDisable(false);
            player4In.setDisable(false);
            break;
        }
      }

    });

  }

}