package edu.csu2017fa314.T15.Model;
import java.sql.Connection; // https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html
import java.sql.DriverManager; // https://www.tutorialspoint.com/jdbc/
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class SearchSQLDatabase {

  private String myDriver="com.mysql.jdbc.Driver"; // add dependencies in pom.xml
  private String myUrl="jdbc:mysql://faure.cs.colostate.edu/cs314";
  private Connection conn;

  public SearchSQLDatabase(String[] loginInfo){
    this(loginInfo,"jdbc:mysql://faure.cs.colostate.edu/cs314");
  }
  public SearchSQLDatabase(String[] loginInfo, String myUrl){
    try{
      Class.forName(myDriver);
      conn = DriverManager.getConnection(myUrl, loginInfo[0], loginInfo[1]);
    }
    catch (ClassNotFoundException e){
      System.err.printf("myDriver: %s not found\n", myDriver );
      System.err.println(e.getMessage());

    } catch (SQLException e) {
      System.err.printf("Can not connect to server %s\n", myUrl);
      System.err.println(e.getMessage());
    }
  }

  public void closeConnection(){
    try {
      conn.close();
    } catch (SQLException e) {
      System.err.printf("Can not close connections:\n%s\n", e.getMessage());
      //e.printStackTrace();
    }
  }
}
