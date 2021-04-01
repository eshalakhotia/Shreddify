package edu.brown.cs.abahl1elakhotimlu39skothar7.graph;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DatabaseConn {
  private static Connection conn;
  private List<String> usersList;
  
  public DatabaseConn() throws SQLException, ClassNotFoundException {
    conn = null;
    this.loadDatabase("databaseShreddify.sqlite3");
  }
  public void loadDatabase(String filename) throws SQLException, ClassNotFoundException {
    try {
      conn.close();
    } catch (Exception ignored) {
    }
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:" + filename;
    conn = DriverManager.getConnection(urlToDB);
  }
  public Connection getConn() {
    return conn;
  }
  public List<String> getAllExerciseAttributes() throws SQLException {
    usersList = new ArrayList<>();
    PreparedStatement idInfo = conn.prepareStatement(
      "SELECT * FROM exercises;");
    ResultSet resulting = idInfo.executeQuery();
    while (resulting.next()) {
      usersList.add(resulting.getString(1));
      usersList.add(resulting.getString(2));
      usersList.add(resulting.getString(3));
      usersList.add(resulting.getString(4));
      usersList.add(resulting.getString(5));
      usersList.add(resulting.getString(6));
      usersList.add(resulting.getString(7));
      usersList.add(resulting.getString(8));
      usersList.add(resulting.getString(9));
      usersList.add(resulting.getString(10));
      usersList.add(resulting.getString(11));
    }
    return usersList;
  }
}
