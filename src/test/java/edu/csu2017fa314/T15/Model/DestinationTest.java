package edu.csu2017fa314.T15.Model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**

 * Tests the methods of the Destination class
 * @version 1.01 - 9/2/17 - initial

 */

public class DestinationTest {



  private Destination b;
  @Before
  public void setUp() throws Exception {
	b = new Destination();
  }



  /**
   * Tests if Destination was imported correctly
   * @throws Exception - A problem importing brewery
   */

  @Test
  public void testsImport() throws Exception {
	  b = new Destination();
  }

  @Test
  public void testsgetters(){
	  b = new Destination("0", "Test Destination", "Test City", "01", "02", "1000");

	  assertEquals(b.getId(), "0");
	  assertEquals(b.getName(), "Test Destination");
	  assertEquals(b.getCity(), "Test City");
	  assertEquals(b.getLatitude(), "01");
	  assertEquals(b.getLongitude(), "02");
	  assertEquals(b.getElevation(), "1000");
  }

  @Test
  public void teststoString() {
	  b = new Destination("0", "Test Destination", "Test City", "01", "02", "1000");

	  String test = "ID: 0" + '\n'
				+ "Name: Test Destination" + '\n'
				+ "City: Test City" + '\n'
				+ "Latitude: 01" + '\n'
				+ "Longitude: 02" + '\n'
				+ "Elevation: 1000";

	  assertEquals(b.toString(),test);
  }
  @Test
  public void testGetKeys(){
  	b = new Destination("0", "Test Destination", "Test City", "01", "02", "1000");
	assertEquals(b.getKeys().toString(), "[elevation, city, latitude, name, id, longitude]");
  }

}