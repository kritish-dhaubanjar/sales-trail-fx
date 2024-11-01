package com.example.app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
  private static Database database = null;
  private static Connection connection = null;

  private Database() {
    try {
      String url = "jdbc:sqlite:./database.sqlite";
      connection = DriverManager.getConnection(url);
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  public static Connection getConnection() {
    if (database == null) {
      database = new Database();
    }

    return connection;
  }

  public static void resetConnection() {
    try {
      if (connection == null) {
        connection.close();
      }
    } catch (SQLException e) {
      System.out.println(e);
    }
  }
}
