package com.example.app.models;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.example.app.database.Database;

public class Unit {
  public static String TABLE_NAME = "units";
  public static Connection connection = Database.getConnection();

  private int id;
  private String name;

  public Unit(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public static ArrayList<Unit> all(int page, int limit, String q) {
    int offset = (page - 1) * limit;
    String sql = String.format("SELECT * FROM %s WHERE name LIKE '%%%s%%' LIMIT %s OFFSET %s", TABLE_NAME, q, limit,
        offset);

    ArrayList<Unit> response = new ArrayList<>();

    try {
      Statement s = connection.createStatement();
      ResultSet r = s.executeQuery(sql);

      while (r.next()) {
        response.add(new Unit(r.getInt("id"), r.getString("name")));
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

  public void delete() {
    String sql = String.format("DELETE FROM %s WHERE id = %s", TABLE_NAME, id);

    try {
      Statement s = connection.createStatement();
      s.executeUpdate(sql);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public ArrayList<Item> items() {
    ArrayList<Item> items = Item.all(1, 10_00_000, "");

    items.removeIf((e) -> e.getUnitId() != id);

    return items;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
