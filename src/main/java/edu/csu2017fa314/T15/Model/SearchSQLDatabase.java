package edu.csu2017fa314.T15.Model;
import java.sql.Connection; // https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html
import java.sql.DatabaseMetaData;
import java.sql.DriverManager; // https://www.tutorialspoint.com/jdbc/
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.HashMap;

public class SearchSQLDatabase {

  private String myDriver="com.mysql.cj.jdbc.Driver"; // add dependencies in pom.xml
  private String myUrl="jdbc:mysql://faure.cs.colostate.edu/cs314";
  private Connection conn;
  private String table = "airports";

  /**
   * Constructor for connecting to the CS314 sql database.
   * @param loginInfo [0] - eid
   *                  [1] - student number
   */
  public SearchSQLDatabase(String[] loginInfo) throws SQLException, ClassNotFoundException {
    this(loginInfo, "jdbc:mysql://faure.cs.colostate.edu/cs314");
  }

  /**
   * Constructor for connecting to sql databases.
   * @param loginInfo [0] - eid
   *                  [1] - student number
   * @param myUrl     Ip address and socket of sql database
   */
  public SearchSQLDatabase(String[] loginInfo, String myUrl)
      throws ClassNotFoundException, SQLException {
    try{
      Class.forName(myDriver);
      conn = DriverManager.getConnection(myUrl, loginInfo[0], loginInfo[1]);
    }
    catch (ClassNotFoundException e){
      System.err.printf("myDriver: %s not found\n", myDriver );
      System.err.println(e.getMessage());
      throw e;

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
   * Makes an
   * @param searchFor
   * @return
   */
  public HashMap<String, Destination> query(String[] searchFor) throws SQLException {
    String[] all = {"*"};
    return query(searchFor, all);
  }

  /**
   * Makes an sql query for entries that are like the given search terms in given columns
   * @param searchFor All the terms to search for
   * @param inColumns Where to search for those terms
   * @return A hashmap with all the return results saved as Destinations and id as the key
   */
  public HashMap<String, Destination> query(String[] searchFor, String[] inColumns)
      throws SQLException {
    HashMap<String, Destination> rt = new HashMap<>();
    String qry  = makeQueryStatment(searchFor, inColumns);
    try {
      Statement st = conn.createStatement();
      try {
        ResultSet rs = st.executeQuery(qry);
        ResultSetMetaData meta = rs.getMetaData();
        int size = meta.getColumnCount() +1;
        while(rs.next())
        {
          Destination des = new Destination();
          for (int i = 2; i < size; i++) {
            String info;
            String field = meta.getColumnName(i);
            try {
              info = rs.getString(field);
            }catch (NullPointerException e) {
              info = "";
            }
            des.setValue(field, info);
          }
          rt.put(des.getId(), des);
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
  public String makeQueryStatment(String[] searchFor, String[] inColumns){
    if(searchFor.length == 0)
    { throw new RuntimeException("No items to search for\n");}
    if(inColumns.length == 0)
    { throw new RuntimeException("No Columns to search for\n");}
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
          for (int i=1; i <stop;){
            search += " " + meta.getColumnName(i) + " " + where;
            if(++i != stop){
              search += " OR";
            }
          }
          rs.close();

        } finally {
          st.close();
        }

      } catch (SQLException e) {
        e.printStackTrace();
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
}
