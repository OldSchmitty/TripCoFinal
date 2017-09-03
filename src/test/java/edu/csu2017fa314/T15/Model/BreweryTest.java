package edu.csu2017fa314.T15.Model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the methods of the Brewery class
 * @author jmay
 * @version 1.0 - 9/2/17 - initial
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

}