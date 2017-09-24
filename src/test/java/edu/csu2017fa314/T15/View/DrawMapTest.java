package edu.csu2017fa314.T15.View;

import static org.junit.Assert.*;

import java.io.File;
import org.junit.Before;
import org.junit.Test;

public class DrawMapTest {

  private String path = "."+ File.separator+"src"+File.separator+"main"+
      File.separator+"resources"+File.separator;
  private DrawMap d;
  private boolean worked;

  @Before
  public void initialize(){
    d = new DrawMap(path+"test.svg");
    worked = false;
  }


  /**
   *Draws an empty map of Colorado
   */
  @Test
  public void drawColorado(){
    try{
      d = new DrawMap(path + "TestDrawColo.svg");
      d.drawColorado();
      d.write();
    }
    catch (RuntimeException e){
      assertFalse("Write Failed to run", true);
    }
    assertTrue(new File(path+ "TestDrawColo.svg").exists());
  }

  @Test
  public void addPathPoint() throws Exception {
    try{
      d = new DrawMap(path + "TestDrawPath.svg");
      d.addPathPoint("40°33′33″N", "105°4′41″W"); //Fort Collins
      d.addPathPoint("39°45′43″N", "104°52′52″W"); //Denver
      d.addPathPoint("39°15′50″N", "103°41′32″W"); //Limon
      d.addPathPoint("40°33′33″N", "105°4′41″W"); //Fort Collins
      d.write();
    }
    catch (RuntimeException e){
      assertFalse("Write Failed to run", true);
    }
    assertTrue(new File(path+ "TestDrawPath.svg").exists());
  }

  @Test
  public void addPoint1() throws Exception {
    try{
      d.addPoint("39.76185", "-104.881105", "Denver");
      worked = true;
    }
    catch (Exception e){
      //
    }
    assertTrue(worked);
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