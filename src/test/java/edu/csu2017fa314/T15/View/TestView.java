package edu.csu2017fa314.T15.View;
import static org.junit.Assert.*;

import edu.csu2017fa314.T15.Model.Destination;
import edu.csu2017fa314.T15.Model.Edge;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;


public class TestView
{
  private View v;

  @Before
  public void setUp() throws Exception
  {
      v = new View("."+ File.separator+"data"+
          File.separator+"ViewItineraryWriterTest.json");
  }

  @Test
  public void testSetDistance()
  {
      v.setTotalDistance(4);
      assertTrue(v.getTotalDistance() == 4);
      v.setTotalDistance(-4);
      assertTrue(v.getTotalDistance() == -4);
  }

  /**
   * <p>Tests constructor for sprint 2</p>
   */
  @Test
  public void testConstructor(){

  }


}
