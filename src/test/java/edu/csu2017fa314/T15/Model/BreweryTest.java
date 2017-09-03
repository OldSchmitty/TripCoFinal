package edu.csu2017fa314.T15.Model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



/**

 * Tests the methods of the Brewery class
 * @version 1.01 - 9/2/17 - initial

 */

public class BreweryTest {



  private Brewery b;

  @Before

  public void setUp() throws Exception {

    b = new Brewery();

  }



  /**

   * Tests if Brewery was imported correctly

   * @throws Exception - A problem importing brewery

   */

  @Test
  public void testsImport() throws Exception {

	  b = new Brewery();

  }
  
  @Test
  public void testsgetters(){
	  b = new Brewery("0", "Test Brewery", "Test City", "01", "02", "1000");
	  
	  assertEquals(b.getId(), "0");
	  assertEquals(b.getName(), "Test Brewery");
	  assertEquals(b.getCity(), "Test City");
	  assertEquals(b.getLatitude(), "01");
	  assertEquals(b.getLongitude(), "02");
	  assertEquals(b.getElevation(), "1000");
  }
  
  @Test
  public void teststoString() {	  
	  b = new Brewery("0", "Test Brewery", "Test City", "01", "02", "1000");
  
	  String test = "ID: 0" + '\n'
				+ "Name: Test Brewery" + '\n'
				+ "City: Test City" + '\n'
				+ "Latitude: 01" + '\n'
				+ "Longitude: 02" + '\n'
				+ "Elevation: 1000";
	  
	  assertEquals(b.toString(),test);
  }


}