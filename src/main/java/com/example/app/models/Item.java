package com.example.app.models;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.example.app.database.Database;

public class Item {
  public static String TABLE_NAME = "items";
  public static Connection connection = Database.getConnection();

  private int id;
  private String name;
  private Double rate;
  private String description;

  private int unit_id;
  private String unitName;

  public Item(int id, String name, String description, Double rate, int unit_id, String unit_name) {
    this.id = id;
    this.name = name;
    this.rate = rate;
    this.unitName = unit_name;
    this.unit_id = unit_id;
    this.description = description;
  }

  public static ArrayList<Item> all(int page, int limit, String q) {
    int offset = (page - 1) * limit;
    String sql = String.format(
        "SELECT t1.id, t1.name, t1.description, t1.price, t2.id as unit_id, t2.name as unit_name FROM %s as t1 INNER JOIN %s as t2 ON %s WHERE t1.name LIKE '%%%s%%' LIMIT %s OFFSET %s",
        TABLE_NAME, Unit.TABLE_NAME, "t1.unit_id = t2.id", q, limit,
        offset);

    ArrayList<Item> response = new ArrayList<>();

    try {
      Statement s = connection.createStatement();
      ResultSet r = s.executeQuery(sql);

      while (r.next()) {
        response.add(new Item(r.getInt("id"), r.getString("name"), r.getString("description"), r.getDouble("price"),
            r.getInt("unit_id"), r.getString("unit_name")));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return response;
  }

  public static int count(String q) {
    String sql = String.format("SELECT COUNT(*) FROM %s WHERE name LIKE '%%%s%%'", TABLE_NAME, q);

    try {
      Statement s = connection.createStatement();
      ResultSet r = s.executeQuery(sql);

      if (r.next()) {
        return r.getInt(1);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return 0;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Double getRate() {
    return rate;
  }

  public String getUnitName() {
    return unitName;
  }

  public int getUnitId() {
    return unit_id;
  }
}
