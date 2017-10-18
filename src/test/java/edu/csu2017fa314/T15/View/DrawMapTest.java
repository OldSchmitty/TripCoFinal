package edu.csu2017fa314.T15.View;

import static org.junit.Assert.*;

import java.io.File;
import org.junit.Before;
import org.junit.Test;

public class DrawMapTest {

  private String path = "."+ File.separator+"data" + File.separator + "test_output" + File.separator;
  private String baseFile = "." + File.separator+"data" +
      File.separator + "resources" + File.separator + "colorado.svg";
  private DrawMap d;
  private boolean worked;

  @Before
  public void initialize(){
    d = new DrawMap(path+"test.svg", baseFile);
    worked = false;
  }

  /**
   * Tests the setBaseFile method
   */
  @Test
  public void setBaseFile() {
    d.setBaseFile("TEST.svg");
    assertEquals(d.getBaseFile(),"TEST.svg"  );
  }

  /**
   * Test the gets base map path
   */
  @Test
  public void getBaseFile(){
    assertEquals(d.getBaseFile(), "." + File.separator+"data" +
        File.separator + "resources" + File.separator + "colorado.svg");
  }
  /**
   *Draws an empty map of Colorado
   */
  @Test
  public void drawColorado(){
    try{
      d = new DrawMap(path + "TestDrawColo.svg", baseFile);
      d.drawColorado();
      d.write();
    }
    catch (RuntimeException e){
      assertFalse("Write Failed to run", true);
    }
    assertTrue(new File(path+ "TestDrawColo.svg").exists());
  }

  /**
   * Draws a path from Fort Collins - Denver - Limon - Fort Collins
   */
  @Test
  public void addEdge(){
    try{
      d = new DrawMap(path + "TestDrawPath.svg", baseFile);
      d.addEdge("40°33′33″N", "105°4′41″W", "39°45′43″N", "104°52′52″W"); //Fort Collins-Denver
      d.addEdge("39°45′43″N", "104°52′52″W", "39°15′50″N", "103°41′32″W"); //Denver-Limon
      d.addEdge("39°15′50″N", "103°41′32″W", "40°33′33″N", "105°4′41″W"); //Limon-FC
      d.write();
    }
    catch (RuntimeException e){
      assertFalse("Write Failed to run", true);
    }
    assertTrue(new File(path+ "TestDrawPath.svg").exists());
  }

  /**
   * Draws a path straight across
   */
  @Test
  public void addEdgeHorizonatal() {
    try{
      d = new DrawMap(path + "TestDrawPathHoriz.svg", baseFile);
      d.addEdge("40°33′33″N", "105°4′41″W","40°33′33″N", "102°4′41″W"); //Fort Collins
      d.write();
    }
    catch (RuntimeException e){
      assertFalse("Write Failed to run", true);
    }
    assertTrue(new File(path+ "TestDrawPath.svg").exists());
  }

  /**
   * Draws a path straight down
   */
  @Test
  public void addEdgeVertical(){
    try{
      d = new DrawMap(path + "TestDrawPathVert.svg", baseFile);
      d.addEdge("40°33′33″N", "105°4′41″W","30°33′33″N", "105°4′41″W");
      d.write();
    }
    catch (RuntimeException e){
      assertFalse("Write Failed to run", true);
    }
    assertTrue(new File(path+ "TestDrawPath.svg").exists());
  }

  /**
   * Tests to see if empty svg file is made
   */
  @Test
  public void write(){
    try{
      d.write();
    }
    catch (RuntimeException e){
      assertFalse("Write Failed to run", true);
    }

    assertTrue(new File(path+"test.svg").exists());
  }

  @Test
  public void mapString(){
    d.addEdge("40°33′33″N", "105°4′41″W", "39°45′43″N", "104°52′52″W"); //Fort Collins-Denver
    d.addEdge("39°45′43″N", "104°52′52″W", "39°15′50″N", "103°41′32″W"); //Denver-Limon
    d.addEdge("39°15′50″N", "103°41′32″W", "40°33′33″N", "105°4′41″W");//Limon-FC
    String map = d.mapString();
    assertNotNull(map);
  }

  @Test
  public void close() throws Exception {
    try{
      d.write();
      worked =true;
    }
    catch (Exception e){
      //
    }
    assertTrue(worked);
  }
}
