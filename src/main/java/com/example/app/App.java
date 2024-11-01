package com.example.app;

import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;

import com.example.app.database.Database;

import javafx.application.Application;

/**
 * JavaFX App
 */
public class App extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/app.fxml"));

    Parent root = loader.load();
    Scene scene = new Scene(root);

    stage.setMinWidth(1024);
    stage.setMinHeight(720);
    stage.setTitle("Sales-Trail");

    stage.getIcons().add(new Image(getClass().getResourceAsStream("images/icon.png")));

    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    Database.getConnection();
    launch();
  }

}
