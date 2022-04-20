package edu.wpi.cs3733.D22.teamX.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {
  private static final String embeddedURL = "jdbc:derby:MealRequestAPI_embed_db;create=true";

  private Connection connection;

  private ConnectionSingleton() {
    try {
      connection = DriverManager.getConnection(embeddedURL);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private static class ConnectionSingletonHelper {
    private static final ConnectionSingleton conn = new ConnectionSingleton();
  }

  public static ConnectionSingleton getConnectionSingleton() {
    return ConnectionSingletonHelper.conn;
  }

  public Connection getConnection() {
    return connection;
  }
}
