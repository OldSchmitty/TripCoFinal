package edu.csu2017fa314.T15.Model;

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
    cd = new CalculateDistance();
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
}