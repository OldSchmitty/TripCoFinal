package edu.csu2017fa314.T15.Model;

import static edu.csu2017fa314.T15.Model.CalculateDistance.setMiles;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * <p>Calculates the distance from two latitude and longitude points</p>
 * @author jmay
 * @version 1.0 -9/2/17 - initial
 */
public class CalculateDistanceTest {

  private CalculateDistance cd;

  @Before
  public void setUp() throws Exception {
    setMiles();
  }

  /**
   * Tests if CalculateDistance was imported correctly
   * @throws Exception - A problem importing CalculateDistance
   */
  @Test
  public void testsImport() throws Exception {
    cd = new CalculateDistance();
  }

  /**
   * Tests to see if strings were properly converted
   * @throws Exception - Not converted properly
   */
  @Test
  public void stringToDoubleForCoordinate() throws Exception {
    // Testing 106°49'43.24" W, 106°49.24' W, 106.24° W, -106.24
    // Should be -106.82867777777777, -106.82066666666667. -106.24, -106.24
    assertEquals(-106.82867777777777, CalculateDistance.stringToDoubleForCoordinate("106°49'43.24\" W"), .000001);
    assertEquals(-106.82066666666667, CalculateDistance.stringToDoubleForCoordinate("106°49.24' W"), .000001);
    assertEquals(-106.24, CalculateDistance.stringToDoubleForCoordinate("106.24° W"), .000001);
    assertEquals(-106.24, CalculateDistance.stringToDoubleForCoordinate("-106.24"), .000001);
  }

  /**
   * Tests distance between to location
   * @throws Exception - Unknown error
   */
  @Test
  public void greatDistanceCalculation() throws Exception {
    /*
     * 40°24'28.9"N, 105°06'52.1"W and 38°56'31" N,105°9'28" W is 101 mile apart
     */
    assertEquals(101,
        Math.round(CalculateDistance.greatDistanceCalculation(
            CalculateDistance.stringToDoubleForCoordinate("40°24'28.9\"N"),
            CalculateDistance.stringToDoubleForCoordinate("105°06'52.1\"W"),
            CalculateDistance.stringToDoubleForCoordinate("38°56'31\" N"),
            CalculateDistance.stringToDoubleForCoordinate("105°9'28\" W"))));
    /*
     * Sydney to Denver -33.868820, 151.209296 and 39.739236, -104.990251 is 8333
     *
    assertEquals(8333,
        Math.round(CalculateDistance.greatDistanceCalculation(-33.868820, 151.209296, 39.739236, -104.990251)));*/

    /*
     *  30, -150 and -30, -41 is 6195 -Works
     *  30, -150 and -30, -40 is 6244 - Does not work it is negative distance
     */
    //assertEquals(17310,
        //Math.round(CalculateDistance.greatDistanceCalculation(30.55, -150.55, -8.55, 40.55)));
  }

  /**
   * Test string to Lat longitude to distance
   * @throws Exception - bad string
   */
  @Test
  public void findDistanceBetween() throws Exception {
    /*
     * 40°24'28.9"N, 105°06'52.1"W and 38°56'31" N,105°9'28" W is 101 mile apart
     */
    assertEquals(101,
        CalculateDistance.findDistanceBetween(
            "40°24'28.9\"N",
            "105°06'52.1\"W",
            "38°56'31\" N",
            "105°9'28\" W"));
  }

  @Test
  public void findDistanceBetweenKM() throws Exception {
    /*
     * 40°24'28.9"N, 105°06'52.1"W and 38°56'31" N,105°9'28" W is 101 mile apart
     */
    CalculateDistance.setKilometers();
    assertEquals(163,
        CalculateDistance.findDistanceBetween(
            "40°24'28.9\"N",
            "105°06'52.1\"W",
            "38°56'31\" N",
            "105°9'28\" W"));
  }

  /**
   * Test the distance from Brew classes
   * @throws Exception - unexpected
   */
  @Test
  public void findDistanceBetween1() throws Exception {
    Destination b1= new Destination("abee","Two22 Brew", "Centennial","39°38'07\" N",
        "104°45'32\" W", "5872" );
    Destination b2 = new Destination("acwatson","Equinox Brewing", "Fort Collins","40°35'17\" N",
        "105°4'26\" W", "4988");
    assertEquals(68,CalculateDistance.findDistanceBetween(b1,b2));
  }
}