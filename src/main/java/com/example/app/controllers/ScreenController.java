package com.example.app.controllers;

import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class ScreenController {

  @FXML
  BorderPane borderPane;

  @FXML
  Button init_button;

  private Button button;

  @FXML
  public void initialize() throws Exception {
    URL url = getClass().getResource("/com/example/app/fxml/screens/units.fxml");

    this.button = init_button;

    borderPane.setCenter(FXMLLoader.load(url));
  }

  public void load(Event event) throws Exception {
    Button button = (Button) event.getSource();
    String screen = (String) button.getUserData();
    URL url = getClass().getResource(screen);

    this.button.getStyleClass().remove("btn-active");
    this.button = button;
    this.button.getStyleClass().add("btn-active");

    borderPane.setCenter(FXMLLoader.load(url));
  }

}
