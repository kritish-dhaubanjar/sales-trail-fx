package com.example.app.controllers;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.text.Text;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.SVGPath;

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

  private ContextMenu getContextMenu() {
    ContextMenu contextMenu = new ContextMenu();

    SVGPath deleteIcon = new SVGPath();
    deleteIcon.setContent(
        "M 4.4 4.4 A 0.4 0.4 90 0 1 4.8 4.8 v 4.8 a 0.4 0.4 90 0 1 -0.8 0 V 4.8 a 0.4 0.4 90 0 1 0.4 -0.4 z m 2 0 a 0.4 0.4 90 0 1 0.4 0.4 v 4.8 a 0.4 0.4 90 0 1 -0.8 0 V 4.8 a 0.4 0.4 90 0 1 0.4 -0.4 z m 2.4 0.4 a 0.4 0.4 90 0 0 -0.8 0 v 4.8 a 0.4 0.4 90 0 0 0.8 0 V 4.8 z M 11.6 2.4 a 0.8 0.8 90 0 1 -0.8 0.8 H 10.4 v 7.2 a 1.6 1.6 90 0 1 -1.6 1.6 H 4 a 1.6 1.6 90 0 1 -1.6 -1.6 V 3.2 h -0.4 a 0.8 0.8 90 0 1 -0.8 -0.8 V 1.6 a 0.8 0.8 90 0 1 0.8 -0.8 H 4.8 a 0.8 0.8 90 0 1 0.8 -0.8 h 1.6 a 0.8 0.8 90 0 1 0.8 0.8 h 2.8 a 0.8 0.8 90 0 1 0.8 0.8 v 0.8 z M 3.2944 3.2 L 3.2 3.2472 V 10.4 a 0.8 0.8 90 0 0 0.8 0.8 h 4.8 a 0.8 0.8 90 0 0 0.8 -0.8 V 3.2472 L 9.5056 3.2 H 3.2944 z M 2 2.4 V 1.6 h 8.8 v 0.8 h -8.8 z");

    SVGPath editIcon = new SVGPath();
    editIcon.setContent("M 11.3928 1.2072 l 1.8 1.8 L 11.8206 4.38 l -1.8 -1.8 z M 4.8 9.6 h 1.8 l 4.3722 -4.3722 l -1.8 -1.8 L 4.8 7.8 z M 11.4 11.4 H 4.8948 c -0.0156 0 -0.0318 0.006 -0.0474 0.006 c -0.0198 0 -0.0396 -0.0054 -0.06 -0.006 H 3 V 3 h 4.1082 l 1.2 -1.2 H 3 c -0.6618 0 -1.2 0.5376 -1.2 1.2 v 8.4 c 0 0.6624 0.5382 1.2 1.2 1.2 h 8.4 a 1.2 1.2 90 0 0 1.2 -1.2 v -5.2008 l -1.2 1.2 V 11.4 z");

    MenuItem delete = new MenuItem("Delete", deleteIcon);
    MenuItem edit = new MenuItem("Edit", editIcon);

    contextMenu.getItems().addAll(edit, delete);

    return contextMenu;
  }

  @FXML
  public void initialize() throws Exception {
    table.setItems(units);

    ContextMenu contextMenu = getContextMenu();

    table.setRowFactory((e) -> {
      TableRow<Unit> row = new TableRow<>();

      row.emptyProperty().addListener((observable, wasEmpty, isEmpty) -> {
        row.setContextMenu(contextMenu);
      });

      return row;
    });

    id.setCellValueFactory(new PropertyValueFactory<>("id"));
    name.setCellValueFactory(new PropertyValueFactory<>("name"));

    combobox.setValue(String.valueOf(limit));

    textfield.setText(query);

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
