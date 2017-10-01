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

  final private String path = "."+ File.separator+"data"+ File.separator + "test_output" + File.separator;
  final private String mapPath = "."+ File.separator+
      "data"+File.separator+ "resources" + File.separator + "colorado.svg";
  private View v;
  private HashMap<String, Destination> des;
  private ArrayList<Edge> edges;

  /**
   * Sets up View class, HashMap, and edges for tests
   */
  @Before
  public void setUp() {
    des = new HashMap<>();
    v = new View(path, mapPath);
    // Set up Hash Map
    String[] key = {"id", "name", "latitude", "longitude"};
    String[] value1 = {"1", "Fort Collins", "40°33′33″N", "105°4′41″W" };
    String[] value2 = {"2", "Denver", "39°45′43″N", "104°52′52″W" };
    String[] value3 = {"3", "Limon", "39°15′50″N", "103°41′32″W" };

    des.put("1", new Destination(key,value1));
    des.put("2", new Destination(key,value2));
    des.put("3", new Destination(key,value3));

    // Set up edges
    edges = new ArrayList<>();
    edges.add(new Edge("1", "2", 10000));
    edges.add(new Edge("2", "3", 20000));
    edges.add(new Edge("3", "1", 30000));
  }

  @Test
  public void testDrawMap(){
    try {
      v.drawMap(des,edges);
    }
    catch (Exception e){
      System.out.println(e.toString());
      assertTrue(false);
    }
    assertTrue(new File( "."+ File.separator+"data" + File.separator
        + "test_output" + File.separator + "map.svg").exists());
  }

  /**
   * Test building Destination json
   */
  @Test
  public void testMakeDestination() {
    try {
      v.makeDestination(des);
    }
    catch (Exception e)
    {
      System.out.println(e.toString());
      assertTrue(false);
    }

    assertTrue(new File(path+"Destinations.json").exists());
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

  /**
   * <p>Tests to see if error is thrown when no path is set</p>
   */
  @Test
  public void testMakeItineraryNoPath(){
    v =new View();
    try{
      ArrayList<Edge> edges = new ArrayList<>();
      v.makeItinerary(edges);
      assertFalse(true); // no error thrown
    }
    catch (Exception e)
    {
      assertEquals("View path not set", e.getMessage());
    }
  }

  /**
   * <p>Test to see if Itinerary JSON made correctly. Copies JSONWriter test.</p>
   */
  @Test
  public void testMakeItinerary(){
    ArrayList<Edge> edges = new ArrayList<>();
    edges.add(new Edge("start name 1", "end name 2", 10000));
    edges.add(new Edge("start name 2", "end name 3", 20000));
    edges.add(new Edge("start name 3", "end name 4", 30000));
    edges.add(new Edge("start name 4", "end name 4", 40000));

    v.makeItinerary(edges);

    File file1 = new File(path + "Itinerary.json");
    File file2 = new File(path + "Sample.json");
    boolean isEqual;
    try {
      isEqual = FileUtils.contentEquals(file1, file2);
    }
    catch (IOException e) {
      isEqual = false;
    }
    assertTrue(isEqual);
  }


}
