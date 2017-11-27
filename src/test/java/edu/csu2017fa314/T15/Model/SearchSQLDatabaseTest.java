package edu.csu2017fa314.T15.Model;

import static java.lang.System.getenv;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SearchSQLDatabaseTest {

  private SearchSQLDatabase sql; // test class
  private static final String schoolUrl = "jdbc:mysql://faure.cs.colostate.edu/cs314";
  private static final String[] schoolLogin = {"josiahm", "831085445"};
  private static final String travisUrl = "jdbc:mysql://localhost"; //172.0.0.1
  private static final String[] travisLogin = {"root", ""}; // Login info
  private static Statement st; // statements to sql server
  private static Connection conn; // connection to serve
  private static String testData = "." + File.separator+ "data" + File.separator
          + "test_input" + File.separator + "DataBaseSetup.csv"; // data file for population
  private static boolean notCSU = true;

  /**
   * Makes a SQL server at the localhost(172.0.0.1), makes a database called TestDatabase314,
   * a table called destinations, and populates it with entries for the tests.
   */
  @BeforeClass
  public static void setupDatabase(){
    // Check if not on school computers
    String travis = getenv("TRAVIS");
    if (travis == null)
    {
      try {
        // School computers are not set to local host
        String host = InetAddress.getLocalHost().getHostAddress();
        if( !host.equals("127.0.0.1")) {
          notCSU = false;
        }
      } catch (UnknownHostException e) {
        e.printStackTrace();
      }
    }
    // Setup if not on school computers
    if(notCSU) {
      try {
        // Connect to to database
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(travisUrl, travisLogin[0], travisLogin[1]);
        st = conn.createStatement();
        //Remove old database if it exists and make new one
        st.executeUpdate("DROP DATABASE IF EXISTS TestDatabase314");
        st.executeUpdate("CREATE DATABASE TestDatabase314");
        st.executeUpdate("USE TestDatabase314");

        // make airports table in TestDatabase314
        st.executeUpdate("DROP TABLE IF EXISTS airports");
        st.executeUpdate("CREATE TABLE airports "
                + "(id int(11),"
                + "code VARCHAR(11),"
                + "type VARCHAR(1000),"
                + "name VARCHAR(1000),"
                + "latitude VARCHAR(1000),"
                + "longitude VARCHAR(1000),"
                + "elevation VARCHAR(1000),"
                + "continent VARCHAR(1000),"
                + "iso_country VARCHAR(100),"
                + "iso_region VARCHAR(100),"
                + "municipality VARCHAR(1000),"
                + "scheduled_service VARCHAR(1000),"
                + "gps_code VARCHAR(1000),"
                + "iata_code VARCHAR(1000),"
                + "local_code VARCHAR(1000),"
                + "home_link VARCHAR(1000),"
                + "wikipedia_link VARCHAR(1000),"
                + "keywords VARCHAR(1000))");

        // create continents table
        st.executeUpdate("CREATE TABLE continents "
                + "(id int(11),"
                + "name VARCHAR(100),"
                + "code VARCHAR(3),"
                + "wikipedia_link VARCHAR(1000))");

        // create countries table
        st.executeUpdate("CREATE TABLE countries "
                + "(id int(11),"
                + "code VARCHAR(100),"
                + "name VARCHAR(1000),"
                + "continent VARCHAR(3),"
                + "wikipedia_link VARCHAR(1000),"
                + "keywords VARCHAR(1000))");

        // create regions table
        st.executeUpdate("CREATE TABLE regions "
                + "(id int(11),"
                + "code VARCHAR(100),"
                + "local_code tinytext,"
                + "name tinytext,"
                + "continent tinytext,"
                + "iso_country varchar(100),"
                + "wikipedia_link tinytext,"
                + "keywords tinytext)");


        //Populate database tables with test data
        st.executeUpdate("LOAD DATA LOCAL INFILE '" + System.getProperty("user.dir")+"/data/test_input/airports.csv' "
                + "INTO TABLE airports "
                + "FIELDS TERMINATED BY ',' "
                + "LINES TERMINATED BY '\n' "
                + "IGNORE 1 LINES");

        st.executeUpdate("LOAD DATA LOCAL INFILE '"+ System.getProperty("user.dir")+"/data/test_input/continents.csv' "
                + "INTO TABLE continents "
                + "FIELDS TERMINATED BY ',' "
                + "LINES TERMINATED BY '\n' "
                + "IGNORE 1 LINES");
        st.executeUpdate("LOAD DATA LOCAL INFILE '"+ System.getProperty("user.dir")+"/data/test_input/countries.csv' "
                + "INTO TABLE countries "
                + "FIELDS TERMINATED BY ',' "
                + "LINES TERMINATED BY '\n' "
                + "IGNORE 1 LINES");
        st.executeUpdate("LOAD DATA LOCAL INFILE '"+ System.getProperty("user.dir")+"/data/test_input/regions.csv' " +
                "INTO TABLE regions " +
                "FIELDS TERMINATED BY ',' "
                + "LINES TERMINATED BY '\n' "
                + "IGNORE 1 LINES");

        // Test to see data
      /*ResultSet r = st.executeQuery("select * from airports");
      while(r.next()){
        System.out.println(r.getString("name"));
      }
      */
      } catch (Exception e) {
        System.err.println(e.getMessage());
        assertTrue(false);
      }
    }
  }

  /**
   * Connect test class to sqlDatabase
   */
  @Before
  public void setup(){
   if(notCSU){
     try {
       sql = new SearchSQLDatabase(travisLogin, travisUrl + "/TestDatabase314");
     } catch (Exception e) /* should not happen*/ {
       System.err.println(e.getMessage());
       assertTrue(false);
     }
   }
   else{
     try {

       sql = new SearchSQLDatabase(schoolLogin);

     } catch (Exception e) /* should not happen*/ {
       System.err.println(e.getMessage());
       assertTrue(false);
     }
   }

  }

  /**
   * Closes the connection before it is replaced
   */
  @After
  public void cleanup(){
    sql.close();
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
   * Test to make sure the new function queryInOrder and its helper function are working
   */
  @Test
  public void testQueryInOrder()throws SQLException{
    String[] searchFor = {"CKZ7", "4XA8", "KINK"};
    Destination[] dests = sql.queryInOrder(searchFor, "CODE");
    assertEquals(dests[0].getId(),"CKZ7" );
    assertEquals(dests[1].getId(),"4XA8" );
    assertEquals(dests[2].getId(),"KINK" );
  }

  /**
   * Tests to see if one item is returned from query
   */
  @Test
  public void queryOne(){
    String[] find = {"Wink Tv Heliport"};
    Destination[] rt = null;
    try {
      rt = sql.query(find);
    } catch (SQLException e) {
      assertTrue(false);
    }
    assertTrue(rt.length == 1);
    assertTrue("Wink Tv Heliport".equals(rt[0].getName()));
  }

  /**
   * Tests for many return results from query
   */
  @Test
  public void queryMany(){
    String[] find = {"Winkler"};
    Destination[] rt = null;
    try {
      rt = sql.query(find);
    } catch (SQLException e) {
      assertTrue(false);
    }

    assertTrue(rt.length == 3);
    // Test to see if the correct airports were found
    String[] ids ={ "CKZ7", "4XA8","KINK"};
    for (int i = 0; i < ids.length; i++) {
      assertEquals(rt[i].getId(), ids[i]);
    }
  }

  /**
   * Tests for no return results from query
   */
  @Test
  public void queryNone(){
    String[] find = {"I SHOULD NOT EXIST"};
    Destination[] rt = null;
    try {
      rt = sql.query(find);
    } catch (SQLException e) {
      assertTrue(false);
    }
    assertTrue(rt.length == 0);

  }

  /**
   * Searches by id
   */
  @Test
  public void queryIDSearch(){
    String[] find ={"09CO", "3FL6", "4XA8"};
    String[] in = {"CODE"};
    Destination[] rt = null;
    try {
      rt = sql.query(find, in);
    } catch (SQLException e) {
      assertTrue(false);
    }
    assertTrue(rt.length == 3);
    for (int i = 0; i < find.length; i++) {
      assertEquals(rt[i].getId(),find[i]);
    }
  }

  /**
   * Tests for invaild search terms
   * @throws SQLException If error in sql code
   */
  @Test (expected = IllegalArgumentException.class)
  public void queryBADField() throws SQLException {
    String[] find ={"0CO3"};
    String[] in = {"BAD"};
    Destination[] rt = sql.query(find, in);
  }

  /**
   * Remove test database from sql server and close connections at end of test
   */
  @AfterClass
  public static void deleteDatabase() {
    if (notCSU) {
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
}
