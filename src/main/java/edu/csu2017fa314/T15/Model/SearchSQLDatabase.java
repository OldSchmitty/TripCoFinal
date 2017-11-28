package edu.csu2017fa314.T15.Model;

import java.sql.Connection; // https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html
import java.sql.DriverManager; // https://www.tutorialspoint.com/jdbc/
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.ResultSet;

public class SearchSQLDatabase {

  /**
   * Driver for SQl Connection
   */
  static final private String myDriver="com.mysql.jdbc.Driver"; // add dependencies in pom.xml
  /**
   * Server location for school sql database
   */
  static final private String myUrl="jdbc:mysql://faure.cs.colostate.edu/cs314";
  /**
   * Connection to sql sever
   */
  private Connection conn;


  /**
   * Constructor for connecting to the CS314 sql database.
   * @param loginInfo [0] - eid
   *                  [1] - student number
   */
  public SearchSQLDatabase(String[] loginInfo) throws SQLException {
    this(loginInfo, myUrl);
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
    // list of tables in order of search
    final String[] tables ={".continents", ".countries", ".regions", ""};
    try {
      try (PreparedStatement qry  = makeQueryStatement(searchFor, inColumns)) {
        ResultSet rs = qry.executeQuery();
        ResultSetMetaData meta = rs.getMetaData();
        int size = meta.getColumnCount() + 1;
        int numRows = 0;
        if (rs.last()) {
          numRows = rs.getRow();
          // not rs.first() because the rs.next() below will move on, missing the first element
          rs.beforeFirst();
        }
        rt = new Destination[numRows];
        int count = 0;

        while (rs.next()) {
          int tableCount = -1;// loop through the tables in search
          Destination des = new Destination();
          for (int i = 1; i < size; i++) {
            String field = meta.getColumnName(i);
            String info = rs.getString(i);
            // Add the table to the search term to prevent overriding keys in desinations
            if(!field.equals("id")){
              des.setValue(field+tables[tableCount], info);
            }
            else
            {
              tableCount++; // used to skip id in returns and move table count
            }
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
   * Queries all the fields in a database for an a given search term
   * @param searchFor What to search for
   * @param inColumns carries a flag for which type of search to do
   * @return A an Array of Destinations with the results of the search in order
   * @throws SQLException error in accessing the database
   */
  public Destination[] queryInOrder(String[] searchFor, String inColumns)
          throws SQLException {
    Destination[] rt = new Destination[searchFor.length];
    // list of tables in order of search
    final String[] tables ={".continents", ".countries", ".regions", ""};
    for(int i = 0; i < searchFor.length; i++) {
      try {
        try (PreparedStatement qry = makeSingleQueryStatement(searchFor[i], inColumns)) {
          ResultSet rs = qry.executeQuery();
          ResultSetMetaData meta = rs.getMetaData();
          int size = meta.getColumnCount() + 1;

          rs.next();
          int tableCount = -1;// loop through the tables in search
          Destination des = new Destination();
          for (int j = 1; j < size; j++) {
            String field = meta.getColumnName(j);
            String info = rs.getString(j);
            // Add the table to the search term to prevent overriding keys in desinations
            if (!field.equals("id")) {
              des.setValue(field + tables[tableCount], info);
            } else {
              tableCount++; // used to skip id in returns and move table count
            }
          }
          des.setIdentifier(i);
          rt[i] = des;
        }
      } catch (SQLException e) {
        System.err.printf("SearchSQLDatabase: bad SQL query\n%s\n", e.getMessage());
        throw e;
      }
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
  private PreparedStatement makeQueryStatement(String[] searchFor, String[] searchType)
      throws SQLException {
    searchDataValidation(searchFor, searchType);
    String search; // What we are searching for
    String where; // What tables to search in
    String front = getQueryJoinCommands(); // Common start for all searches
    // Looking for in (?,?,..)
    if(searchFor.length > 1)
    {
      search = " ( ";
      for (String s: searchFor) {
        search += " ? ,";
      }
      search = search.substring(0, search.length() -1);
      search += " )";
    }
    //Basic query
    else{
      search = "%" + searchFor[0] +"%";
    }
    PreparedStatement rt;
    try {
      // Search in all tables for possible matches
      if(searchType[0].equals("*")) {
        where = "airports.name like ? or municipality like ? or regions.name like ? "
                + "or countries.name like ? or continents.name like ? limit 100";
        rt = conn.prepareStatement(front + where);
        rt.setString(1, search);
        rt.setString(2, search);
        rt.setString(3, search);
        rt.setString(4, search);
        rt.setString(5, search);

      } else if(searchType[0].equals("CODE")) {
        // look by id
        where = "airports.code in "+ search;
        rt = conn.prepareStatement(front+where);
        for(int i = 0; i <searchFor.length; i++) {
          rt.setString(i+1, searchFor[i]);
        }
      }
      else {
        // This should not happen yet
        throw new IllegalArgumentException("Invalid search term " + searchType[0] + "\n");}
    } catch (SQLException e) {
      System.err.printf("SearchSQLDatabase:makeQueryStatement error "
              + "in getting column names\n%s\n", e.getMessage());
      throw e;
    }

    return rt;
  }

  /**
   * Checks to make sure no empty strings arrays are sent to make a query.
   * @param searchFor Items to search for
   * @param searchType Type of search
   * @throws IllegalArgumentException If search item or type is empty
   */
  private void searchDataValidation(final String[] searchFor, final String[] searchType)
      throws IllegalArgumentException {
    if(searchFor.length == 0)
    { throw new IllegalArgumentException("No items to search for\n");}
    if(searchType.length == 0)
    { throw new IllegalArgumentException("No Columns to search for\n");}
  }


  /**
     * Builds builds a prepared statement for a single item based on the airport code
     * @param searchFor What to search for
     * @param searchType Flag for what kind of search to do
     *                    <p>"CODE" airport id/code</p>
     * @return PreparedStatement
     */
  private PreparedStatement makeSingleQueryStatement(String searchFor, String searchType)
      throws SQLException {
    String query = getQueryJoinCommands(); // Common start for all searches
    //Basic query

    PreparedStatement rt;
    try {
      // Search in all tables for possible matches
        // look by id
         query += "airports.code = '"+ searchFor + "'";
        rt = conn.prepareStatement(query);
    } catch (SQLException e) {
      System.err.printf("SearchSQLDatabase:makeQueryStatement error "
        + "in getting column names\n%s\n", e.getMessage());
      throw e;
  }

    return rt;
  }

  /**
   * The common start for all queries
   * @return Starting select statement for all queries
   */
  private String getQueryJoinCommands() {
    return "SELECT * FROM continents INNER JOIN countries ON countries.continent = continents.code "
        + "INNER JOIN regions ON regions.iso_country = countries.code INNER JOIN airports "
        + "ON airports.iso_region = regions.code WHERE ";
  }
}
