package edu.csu2017fa314.T15.Model;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SearchSQLDatabaseTest {

  private SearchSQLDatabase sql; // test class
  private static final String url = "jdbc:mysql://localhost"; //172.0.0.1
  private static final String[] login = {"root", ""}; // Login info
  private static Statement st; // statements to sql server
  private static Connection conn; // connection to serve
  private static String testData = "."+ File.separator+"data" + File.separator +
      "test_input" + File.separator + "DataBaseSetup.csv"; // data file for population

  /**
   * Makes a SQL server at the localhost(172.0.0.1), makes a database called TestDatabase314,
   * a table called destinations, and populates it with entries for the tests.
   */
  @BeforeClass
  public static void setupDatabase(){
    try {
      // Connect to to database
      Class.forName("com.mysql.cj.jdbc.Driver");
      conn = DriverManager.getConnection(url, login[0], login[1] );
      st = conn.createStatement();
      //Remove old database if it exists and make new one
      st.executeUpdate("DROP DATABASE IF EXISTS TestDatabase314");
      st.executeUpdate("CREATE DATABASE TestDatabase314");
      st.executeUpdate("USE TestDatabase314");
      // Make table to match spring 3
      st.executeUpdate("DROP TABLE IF EXISTS destinations");
      st.executeUpdate("CREATE TABLE destinations " +
          "(inx INTEGER not NULL,"
          + " id VARCHAR(1000),"
          + " type VARCHAR(1000),"
          + " name VARCHAR(1000),"
          + " latitude VARCHAR(1000),"
          + " longitude VARCHAR(1000),"
          + " elevation VARCHAR(1000),"
          + " municipality VARCHAR(1000),"
          + " home_link VARCHAR(1000),"
          + " wikipedia_link VARCHAR(1000),"
          + " PRIMARY KEY ( inx ))");

      //Populate database with test data
      BufferedReader reader = new BufferedReader(
          new InputStreamReader(new FileInputStream(testData)));
      String line;
      String[] values;
      reader.readLine(); // skip key values
      int i = 1;
      while((line=reader.readLine()) != null){
        values = line.split(",", -1);
        // Change empty keys to NULL for statement
        for (String v:values) {
          if(v.equals(""))
          { v="NULL"; }
        }
        String entry = "INSERT INTO destinations VALUES( " + i++ + ", '"+ values[0] + "', '" +
            values[1] + "', '" + values[2] + "', '" + values[3] + "', '" + values[4] + "', '" +
            values[5] + "', '" + values[6] + "', '" + values[7] + "', '" + values[8] + "')";
        st.executeUpdate(entry);
      }
      // Test to see data
      /*ResultSet r = st.executeQuery("select * from destinations");
      while(r.next()){
        System.out.println(r.getString("name"));
      }*/

    } catch (Exception e) {
      System.err.println(e.getMessage());
      assertTrue(false);
    }
  }

  /**
   * Connect test class to sqlDatabase
   */
  @Before
  public void setup(){
    sql = new SearchSQLDatabase(login, url + "/TestDatabase314");
    sql.setTable("destinations");
  }

  /**
   * Test to see if class object was created correctly
   */
  @Test
  public void testSetup(){
    assertTrue(sql != null);
    sql.close();
  }

  /**
   * Tests searching everything in the database
   */
  @Test
  public void query(){
    //String[] find = {"Salida"};
    //HashMap<String, Destination> rt = sql.query(find);
    //assertTrue(rt.size() == 1);
    //assertTrue("Harriet Alexander Field".equals(rt.get("KANK").getName()));
  }

  /**
   * Tests searching in selected columns
   */
  @Test
  public void query1(){

  }

  /**
   * Checks making query statement for all of table
   */
  @Test
  public void makeQueryStatment1(){
    try {
      String[] s = {"Salida"};
      String[] i = {"*"};
      String rt = sql.makeQueryStatment(s,i);
      //System.out.println(rt);
      ResultSet r = st.executeQuery(rt);
      r.last();
      assertEquals(1, r.getRow());
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      assertTrue(false);
    }
  }

  /**
   * Check making query statement for one column
   */
  @Test
  public void makeQueryStatment2(){

    try {
      String[] s = {"KTAD", "KSTK", "CD02", "KSBS" ,"KANK"};
      String[] i = {"id"};
      String rt = sql.makeQueryStatment(s,i);
      //System.out.println(rt);
      ResultSet r = st.executeQuery(rt);
      r.last();
      assertEquals(5, r.getRow());
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      assertTrue(false);
    }
  }

  /**
   * Test to see invalid prams cause errors
   */
  @Test
  public void makeQueryStatment3(){
    String[] s = {};
    String[] i = {};
    try {
      sql.makeQueryStatment(s,i);
      assertTrue(false);
    }catch (RuntimeException e) {
      assertEquals(e.getMessage(), "No items to search for\n");
    }
    String[] s2 = {"NO"};
    try {
      sql.makeQueryStatment(s2,i);
      assertTrue(false);
    }catch (RuntimeException e) {
      assertEquals(e.getMessage(), "No Columns to search for\n");
    }
  }

  /**
   * Remove test database from sql server and close connections at end of test
   */
  @AfterClass
  public static void deleteDatabase(){
    try {
      st.executeUpdate("DROP DATABASE IF EXISTS TestDatabase314");
      st.close();
      conn.close();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      assertTrue(false);
    }
  }

}
