package edu.csu2017fa314.T15.View;
import static org.junit.Assert.*;

import edu.csu2017fa314.T15.Model.Destination;
import edu.csu2017fa314.T15.Model.Edge;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;


public class TestView
{
  final private String path = "."+ File.separator+"data"+
      File.separator+"ViewItineraryWriterTest.json";
  private View v;

  @Before
  public void setUp() throws Exception
  {
      v = new View(path);
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
    assertNotNull(v);
  }

  @Test
  public void testMakeItinerary(){
    ArrayList<Edge> edges = new ArrayList<>();
    edges.add(new Edge("start name 1", "end name 2", 10000));
    edges.add(new Edge("start name 2", "end name 3", 20000));
    edges.add(new Edge("start name 3", "end name 4", 30000));
    edges.add(new Edge("start name 4", "end name 4", 40000));

    File file1 = new File(path);
    File file2 = new File("."+File.separator+"web"+File.separator+"Sample1.json");
    boolean isEqual;
    try {
      isEqual = FileUtils.contentEquals(file1, file2);
    }
    catch (IOException e) {
      isEqual = false;
    }
    assert(isEqual);
  }


}
