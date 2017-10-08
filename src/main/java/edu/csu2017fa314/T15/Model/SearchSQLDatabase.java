package edu.csu2017fa314.T15.Model;
import java.sql.Connection; // https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html
import java.sql.DriverManager; // https://www.tutorialspoint.com/jdbc/
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class SearchSQLDatabase {

  String myDriver="com.mysql.jdbc.Driver"; // add dependencies in pom.xml
  String myUrl="jdbc:mysql://faure.cs.colostate.edu/cs314";
  Connection conn;

  public SearchSQLDatabase(String[] loginInfo){
    try{
      Class.forName(myDriver);
    }
    catch (ClassNotFoundException e){
      System.out.printf("myDriver: %s not found\n", myDriver );
    }
  }
}
