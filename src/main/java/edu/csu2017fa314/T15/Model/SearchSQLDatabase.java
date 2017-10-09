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
    try{
      Class.forName(myDriver);
      conn = DriverManager.getConnection(myUrl, loginInfo[0], loginInfo[1]);
    }
    catch (ClassNotFoundException e){
      System.err.printf("myDriver: %s not found\n", myDriver );
      System.err.println(e.getMessage());

    } catch (SQLException e) {
      System.err.printf("Can not connect to server %s", myUrl);
      System.err.println(e.getMessage());
    }
  }
}
