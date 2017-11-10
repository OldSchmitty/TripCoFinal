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
  private String samplePath = "."+File.separator+"data" + File.separator+ "resources" + File.separator;
  private View v;
  private Destination[] des;
  private ArrayList<Edge> edges;

  /**
   * Sets up View class, HashMap, and edges for tests
   */
  @Before
  public void setUp() {
    v = new View(path);
    // Set up Hash Map
    String[] key = {"id", "name", "latitude", "longitude"};
    String[] value1 = {"1", "Fort Collins", "40°33′33″N", "105°4′41″W" };
    String[] value2 = {"2", "Denver", "39°45′43″N", "104°52′52″W" };
    String[] value3 = {"3", "Limon", "39°15′50″N", "103°41′32″W" };
    Destination des1 = new Destination(key,value1);
    des1.setIdentifier(0);
    Destination des2 = new Destination(key,value2);
    des2.setIdentifier(1);
    Destination des3 = new Destination(key,value3);
    des3.setIdentifier(2);
    des=new Destination[3];
    des[0] = des1;
    des[1] = des2;
    des[2] = des3;

    // Set up edges
    edges = new ArrayList<>();
    edges.add(new Edge(0, 1, 10000));
    edges.add(new Edge(1, 2, 20000));
    edges.add(new Edge(2, 0, 30000));
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

  @Test
  public void testMapString(){
    String map = v.drawMapString(des, edges);
    assertNotNull(map);
  }
  /**
   * <p>Test to see if Itinerary JSON made correctly. Copies JSONWriter test.</p>
   */
  @Test
  public void testMakeItinerary(){
    ArrayList<Edge> edges = new ArrayList<>();
    edges.add(new Edge(1, 2, 10000));
    edges.add(new Edge(2, 3, 20000));
    edges.add(new Edge(3, 4, 30000));
    edges.add(new Edge(4, 4, 40000));

    v.makeItinerary(edges);

    File file1 = new File(path + "Itinerary.json");
    File file2 = new File(samplePath + "Sample.json");
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
