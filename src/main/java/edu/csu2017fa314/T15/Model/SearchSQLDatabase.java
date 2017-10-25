package edu.csu2017fa314.T15.Model;
import static java.lang.System.getenv;

import java.security.InvalidParameterException;
import java.sql.Connection; // https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html
import java.sql.DatabaseMetaData;
import java.sql.DriverManager; // https://www.tutorialspoint.com/jdbc/
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
    String qry  = makeQueryStatement(searchFor, inColumns);
    try {
      Statement st = conn.createStatement();
      try {
        ResultSet rs = st.executeQuery(qry);
        System.out.println("rs: "+rs);
        ResultSetMetaData meta = rs.getMetaData();
        System.out.println("meta: "+meta);
        int size = meta.getColumnCount() +1;
        System.out.println("size: "+size);
        rt = new Destination[size];
        int count = 0;
        while(rs.next())
        {
          Destination des = new Destination();
          for (int i = 2; i < size; i++) {
            String field = meta.getColumnName(i);
            String info = rs.getString(field);
            des.setValue(field, info);
          }
          des.setIdentifier(count);
          rt[count]= des;
          count++;
        }
      } finally { st.close(); }
    }catch (SQLException e){
      System.err.printf("SearchSQLDatabase: bad SQL query\n%s\n", e.getMessage());
      throw e;
    }
    return rt;
  }

  /**
   * Builds the string for the query
   * @param searchFor What to search for
   * @param inColumns Where to search
   * @return Complete query string
   */
  public String makeQueryStatement(String[] searchFor, String[] inColumns) throws SQLException {
    if(searchFor.length == 0)
    { throw new IllegalArgumentException("No items to search for\n");}
    if(inColumns.length == 0)
    { throw new IllegalArgumentException("No Columns to search for\n");}
    String search = "";
    String where;
    String front = "SELECT * FROM " + table + " WHERE";
    if(searchFor.length > 1)
    {
      where = "IN ( ";
      for (String s: searchFor) {
        where += "'" + s + "' ,";
      }
      where = where.substring(0, where.length() -1);
      where += " )";
    }
    else{
      where = "LIKE '%" + searchFor[0] +"%'";
    }
    if(inColumns[0].equals("*")) {
      try {
        Statement st = conn.createStatement();
        try {
          ResultSet rs = st.executeQuery("Select * from " + table);
          ResultSetMetaData meta = rs.getMetaData();
          int stop = meta.getColumnCount() +1;
          for (int i=2; i <stop;){
            search += " " + meta.getColumnName(i) + " " + where;
            if(++i != stop){
              search += " OR";
            }
          }
          rs.close();

        } finally { st.close(); }
      } catch (SQLException e) {
        System.err.printf("SearchSQLDatabase:makeQueryStatement error "
            + "in getting column names\n%s\n", e.getMessage());
        throw e;
      }
    }
    else {
      search = " " + inColumns[0] + " " + where;
    }

    return front + search;
  }

  public void setTable(String t){
    this.table = t;
  }

  public static void main(String[] args){
    String travis = getenv("TRAVIS");
    if (travis == null)
    {
      try {
        String[] login = {"josiahm", "831085445"};
        SearchSQLDatabase test = new SearchSQLDatabase(login);
        String[] ser = {"Denver"};
        String[] id = {"ID"};
        Destination[] rt = test.query(ser);
      } catch (Exception e){
        System.err.println(e.getMessage());

      }
    }
  }
}
