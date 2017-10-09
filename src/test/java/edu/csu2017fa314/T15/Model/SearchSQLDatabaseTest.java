package edu.csu2017fa314.T15.Model;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SearchSQLDatabaseTest {

  private SearchSQLDatabase sql;
  private static final String url = "\"jdbc:mysql://127.0.0.1:";
  private static final String[] login = {"travis", ""};

  @BeforeClass
  public static void setupDatabase(){
    Connection conn;
    Statement st;
    try {
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(url, login[0], login[1] );
      st = conn.createStatement();
      st.executeUpdate("CREATE DATABASE DESTINATIONS");
      st.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Before
  public void setup(){
    sql = new SearchSQLDatabase(login, url);
  }

  @Test
  public void testSetup(){
    assertTrue(sql != null);
  }
}