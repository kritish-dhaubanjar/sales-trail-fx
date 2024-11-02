module com.example.app {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;

  opens com.example.app to javafx.fxml;
  opens com.example.app.controllers to javafx.fxml;
  opens com.example.app.models to javafx.base;

  exports com.example.app;
}
