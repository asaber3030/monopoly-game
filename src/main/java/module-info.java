module com.example.monoploygame {
  requires javafx.controls;
  requires javafx.fxml;


  opens com.example.monoploygame to javafx.fxml;
  exports com.example.monoploygame;
}