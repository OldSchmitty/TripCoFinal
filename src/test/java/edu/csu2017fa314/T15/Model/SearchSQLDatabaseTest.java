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
  private static String testData = "."+ File.separator+"data" + File.separator +
      "test_input" + File.separator + "DataBaseSetup.csv";

  @BeforeClass
  public static void setupDatabase(){
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      conn = DriverManager.getConnection(url, login[0], login[1] );
      st = conn.createStatement();
      st.executeUpdate("DROP DATABASE IF EXISTS TestDatabase314");
      st.executeUpdate("CREATE DATABASE TestDatabase314");
      st.executeUpdate("USE TestDatabase314");
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

      BufferedReader reader = new BufferedReader(
          new InputStreamReader(new FileInputStream(testData)));
      String line;
      String[] values;
      reader.readLine();
      int i = 1;
      while((line=reader.readLine()) != null){
        values = line.split(",", -1);

        for (String v:values) {
          if(v.equals(""))
          {
            v="NULL";
          }
        }
        String entry = "INSERT INTO destinations VALUES( " + i++ + ", '"+ values[0] + "', '" +
            values[1] + "', '" + values[2] + "', '" + values[3] + "', '" + values[4] + "', '" +
            values[5] + "', '" + values[6] + "', '" + values[7] + "', '" + values[8] + "')";
        st.executeUpdate(entry);
      }
      /*ResultSet r = st.executeQuery("select * from destinations");
      while(r.next()){
        System.out.println(r.getString("name"));
      }*/

    } catch (Exception e) {
      System.err.println(e.getMessage());
      assertTrue(false);
    }
  }

  @Before
  public void setup(){
    sql = new SearchSQLDatabase(login, url + "/TestDatabase314");
  }

  @Test
  public void testSetup(){
    assertTrue(sql != null);
    sql.close();
  }

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
