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
}