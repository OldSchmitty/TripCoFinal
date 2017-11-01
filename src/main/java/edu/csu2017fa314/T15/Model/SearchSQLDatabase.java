package edu.csu2017fa314.T15.Model;
import static java.lang.System.getenv;

import java.security.InvalidParameterException;
import java.sql.Connection; // https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html
import java.sql.DatabaseMetaData;
import java.sql.DriverManager; // https://www.tutorialspoint.com/jdbc/
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.HashMap;

public class SearchSQLDatabase {

  private String myDriver="com.mysql.jdbc.Driver"; // add dependencies in pom.xml
  private String myUrl="jdbc:mysql://faure.cs.colostate.edu/cs314?connectTimeout=3000";
  private Connection conn;
  private String table = "airports";

  /**
   * Constructor for connecting to the CS314 sql database.
   * @param loginInfo [0] - eid
   *                  [1] - student number
   */
  public SearchSQLDatabase(String[] loginInfo) throws SQLException {
    this(loginInfo, "jdbc:mysql://faure.cs.colostate.edu/cs314");
  }

  /**
   * Constructor for connecting to sql databases.
   * @param loginInfo [0] - eid
   *                  [1] - student number
   * @param myUrl     Ip address and socket of sql database
   */
  public SearchSQLDatabase(String[] loginInfo, String myUrl)
      throws SQLException {
    try{
      Class.forName(myDriver);
      conn = DriverManager.getConnection(myUrl, loginInfo[0], loginInfo[1]);
    }
    catch (ClassNotFoundException e){
      System.err.printf("myDriver: %s not found\n", myDriver );
      System.err.println(e.getMessage());
      throw new RuntimeException(e);

    } catch (SQLException e) {
      System.err.printf("Can not connect to server %s\n", myUrl);
      System.err.println(e.getMessage());
      throw e;
    }
  }

  /**
   * Closes the connection to sql database
   */
  public void close(){
    try {
      conn.close();
    } catch (SQLException e) {
      System.err.printf("Can not close connections:\n%s\n", e.getMessage());
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  /**
   * Queries all the fields in a database for an a given search term
   * @param searchFor What to search for
   * @return A hashmap with the results of the search
   * @throws SQLException error in accessing the database
   */
  public Destination[] query(String[] searchFor) throws SQLException {
    String[] all = {"*"};
    return query(searchFor, all);
  }

  /**
   * Makes an sql query for entries that are like the given search terms in given columns
   * @param searchFor All the terms to search for
   * @param inColumns Where to search for those terms
   * @return A hashmap with all the return results saved as Destinations and id as the key
   */
  public Destination[] query(String[] searchFor, String[] inColumns)
      throws SQLException {
    Destination[] rt;
    //PreparedStatement qry  = makeQueryStatement(searchFor, inColumns);
    try {
      try (PreparedStatement qry  = makeQueryStatement(searchFor, inColumns);) {
        ResultSet rs = qry.executeQuery();
        ResultSetMetaData meta = rs.getMetaData();
        int size = meta.getColumnCount() + 1;
        int numRows = 0;
        if (rs.last()) {
          numRows = rs.getRow();
          rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
        }
        rt = new Destination[numRows];
        int count = 0;
        while (rs.next()) {
          Destination des = new Destination();
          for (int i = 2; i < size; i++) {
            String field = meta.getColumnName(i);
            String info = rs.getString(field);
            des.setValue(field, info);
          }
          des.setIdentifier(count);
          rt[count] = des;
          count++;
        }
      }
    }catch (SQLException e){
      System.err.printf("SearchSQLDatabase: bad SQL query\n%s\n", e.getMessage());
      throw e;
    }
    return rt;
  }

  /**
   * Builds builds a prepared statement based on a search type
   * @param searchFor What to search for
   * @param searchType Where to search
   *                    <p>"*" default search</p>
   *                    <p>"CODE" airport id/code</p>
   * @return PreparedStatement
   */
  private PreparedStatement makeQueryStatement(String[] searchFor, String[] searchType) throws SQLException {
    if(searchFor.length == 0)
    { throw new IllegalArgumentException("No items to search for\n");}
    if(searchType.length == 0)
    { throw new IllegalArgumentException("No Columns to search for\n");}
    String search;
    String where;
    String front = "SELECT * FROM continents INNER JOIN countries ON countries.continent = continents.code "
        + "INNER JOIN regions ON regions.iso_country = countries.code INNER JOIN airports ON airports.iso_region = regions.code WHERE ";
    if(searchFor.length > 1)
    {
      search = " ( ";
      for (String s: searchFor) {
        search += "'" + s + "' ,";
      }
      search = search.substring(0, search.length() -1);
      search += " )";
    }
    else{
      search = " '%" + searchFor[0] +"%'";
    }
    PreparedStatement rt;
    try {
      if(searchType[0].equals("*")) {

        where = "airports.name like ? or municipality like ? or regions.name like ? "
            + "or countries.name like ? or continents.name like ? limit 100";
        rt = conn.prepareStatement(front + where);
        rt.setString(1, search);
        rt.setString(2, search);
        rt.setString(3, search);
        rt.setString(4, search);
        rt.setString(5, search);


    } else if(searchType[0].equals("CODE")){
      where = "airports.code in ?";
      rt = conn.prepareStatement(front+where);
      rt.setString(1, search);
    }
    else {
      throw new IllegalArgumentException("Invalid search term " + searchType[0] + "\n");}
    } catch (SQLException e) {
    System.err.printf("SearchSQLDatabase:makeQueryStatement error "
        + "in getting column names\n%s\n", e.getMessage());
    throw e;
  }

    return rt;
  }

  public void setTable(String t){
    this.table = t;
  }

}
