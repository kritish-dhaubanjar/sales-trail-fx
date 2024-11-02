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

import com.example.app.models.Item;

public class ItemController {

  @FXML
  TableView<Item> table;

  @FXML
  TableColumn<Item, Integer> id;

  @FXML
  TableColumn<Item, Double> rate;

  @FXML
  TableColumn<Item, String> name, description, unit;

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

  private static ObservableList<Item> items = FXCollections.observableArrayList();

  @FXML
  public void initialize() throws Exception {
    table.setItems(items);

    id.setCellValueFactory(new PropertyValueFactory<>("id"));
    name.setCellValueFactory(new PropertyValueFactory<>("name"));
    unit.setCellValueFactory(new PropertyValueFactory<>("unitName"));
    rate.setCellValueFactory(new PropertyValueFactory<>("rate"));
    description.setCellValueFactory(new PropertyValueFactory<>("description"));

    combobox.setValue(String.valueOf(limit));

    textfield.setText(query);

    textfield.textProperty().addListener((observable, oldValue, newValue) -> {
      setQuery(newValue);
    });

    setItems();
  }

  private int pages() {
    return (int) Math.ceil((double) count / limit);
  }

  private void setItems() {
    count = Item.count(query);

    items.clear();
    items.addAll(Item.all(page, limit, query));

    title.setText(String.format("Items (%s)", count));
    footer.setText(String.format("Page %s of %s", page, pages()));
  }

  public void setLimit() {
    page = 1;
    limit = Integer.parseInt(combobox.getValue());
    setItems();
  }

  public void clear() {
    query = "";
    textfield.setText("");
    setItems();
  }

  public void setQuery(String q) {
    page = 1;
    query = q;
    setItems();
  }

  public void next() {
    if (page == pages()) {
      return;
    }

    page++;
    setItems();
  }

  public void previous() {
    if (page == 1) {
      return;
    }

    page--;
    setItems();
  }
}
