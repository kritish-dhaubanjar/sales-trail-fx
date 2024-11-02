package com.example.app.controllers;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.text.Text;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import com.example.app.models.Unit;

public class UnitController {

  @FXML
  TableView<Unit> table;

  @FXML
  TableColumn<Unit, Integer> id;

  @FXML
  TableColumn<Unit, String> name;

  @FXML
  ComboBox<String> combobox;

  @FXML
  TextField textfield;

  @FXML
  Text title, footer;

  private static int count = 0;
  private static int page = 1;
  private static int limit = 20;
  private static String query = "";

  private static ObservableList<Unit> units = FXCollections.observableArrayList();

  @FXML
  public void initialize() throws Exception {
    table.setItems(units);

    id.setCellValueFactory(new PropertyValueFactory<>("id"));
    name.setCellValueFactory(new PropertyValueFactory<>("name"));

    combobox.setValue(String.valueOf(limit));

    textfield.textProperty().addListener((observable, oldValue, newValue) -> {
      setQuery(newValue);
    });

    setUnits();
  }

  private int pages() {
    return (int) Math.ceil((double) count / limit);
  }

  private void setUnits() {
    count = Unit.count(query);

    units.clear();
    units.addAll(Unit.all(page, limit, query));

    title.setText(String.format("Units (%s)", count));
    footer.setText(String.format("Page %s of %s", page, pages()));
  }

  public void setLimit() {
    page = 1;
    limit = Integer.parseInt(combobox.getValue());
    setUnits();
  }

  public void clear() {
    query = "";
    textfield.setText("");
    setUnits();
  }

  public void setQuery(String q) {
    page = 1;
    query = q;
    setUnits();
  }

  public void next() {
    if (page == pages()) {
      return;
    }

    page++;
    setUnits();
  }

  public void previous() {
    if (page == 1) {
      return;
    }

    page--;
    setUnits();
  }
}
