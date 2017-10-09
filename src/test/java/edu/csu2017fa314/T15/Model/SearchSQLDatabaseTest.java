package edu.csu2017fa314.T15.Model;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.BeforeClass;

public class SearchSQLDatabaseTest {

  SearchSQLDatabase sql;

  @BeforeClass
  public static void setupDatabase(){
    Connection conn;
    Statement st;
    try {
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection("jdbc:mysql://faure.cs.colostate.edu/cs314",
          "root", "" );
      st = conn.createStatement();
      st.executeUpdate("CREATE DATABASE DESTINATIONS");

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

}