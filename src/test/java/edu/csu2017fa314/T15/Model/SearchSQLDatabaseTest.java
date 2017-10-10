package edu.csu2017fa314.T15.Model;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SearchSQLDatabaseTest {

  private SearchSQLDatabase sql;
  private static final String url = "jdbc:mysql://localhost"; //have tried 172.0.0.1
  private static final String[] login = {"root", ""};
  private static Statement st;
  private static Connection conn;

  @BeforeClass
  public static void setupDatabase(){
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      conn = DriverManager.getConnection(url, login[0], login[1] );
      st = conn.createStatement();
      st.executeUpdate("DROP DATABASE IF EXISTS TestDatabase");
      st.executeUpdate("CREATE DATABASE TestDatabase");
      st.executeUpdate("USE TestDatabase");
      st.executeUpdate("DROP TABLE IF EXISTS destinations");
      st.executeUpdate("CREATE TABLE destinations " +
          "(id INTEGER not NULL,"
          + " name VARCHAR(225),"
          + " PRIMARY KEY ( id ))");

      st.executeUpdate("INSERT INTO destinations " + "VALUES(1, 'test')");
      ResultSet r = st.executeQuery("select * from destinations");
      r.next();
      System.out.println(r.getString("name"));

    } catch (Exception e) {
      System.err.println(e.getMessage());
      assertTrue(false);
    }
  }

  @Before
  public void setup(){
    sql = new SearchSQLDatabase(login, url + "/TestDatabase");
  }

  @Test
  public void testSetup(){
    assertTrue(sql != null);
    sql.close();
  }

  @AfterClass
  public static void deleteDatabase(){
    try {
      st.executeUpdate("DROP DATABASE IF EXISTS TestDatabase");
      st.close();
      conn.close();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      assertTrue(false);
    }

  }

}
