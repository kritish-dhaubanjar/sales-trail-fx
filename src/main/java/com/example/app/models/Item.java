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

  public Item(int id, String name, String description, Double rate) {
    this.id = id;
    this.name = name;
    this.rate = rate;
    this.description = description;
  }

  public static ArrayList<Item> all(int page, int limit, String q) {
    int offset = (page - 1) * limit;
    String sql = String.format("SELECT * FROM %s WHERE name LIKE '%%%s%%' LIMIT %s OFFSET %s", TABLE_NAME, q, limit,
        offset);

    ArrayList<Item> response = new ArrayList<>();

    try {
      Statement s = connection.createStatement();
      ResultSet r = s.executeQuery(sql);

      while (r.next()) {
        response.add(new Item(r.getInt("id"), r.getString("name"), r.getString("description"), r.getDouble("price")));
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
}
