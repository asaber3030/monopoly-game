package com.example.monoploygame;

import javafx.scene.control.Alert;

public class Message {

 /**
  * Creating new message by creating object from this class
  * @params t: Title of the alert
  * @params c: Content of the alert
  * @return void
  */
  public Message(String t, String c) {
    Alert a = new Alert(Alert.AlertType.INFORMATION);
    a.setGraphic(null);
    a.setHeaderText(null);
    a.setTitle(t);
    a.setContentText(c);
    a.show();
  }

 /**
  * Static method for showing an alert without creating object of Message Class
  * @params title: Title of the alert
  * @params content: Content of the alert
  * @return void
  */
  public static void message(String title, String content) {
    Alert a = new Alert(Alert.AlertType.INFORMATION);
    a.setGraphic(null);
    a.setHeaderText(null);
    a.setTitle(title);
    a.setContentText(content);
    a.show();
  }

}


