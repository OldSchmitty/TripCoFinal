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
  public void addPoint() throws Exception {
    try{
      d.addPoint("39.76185", "-104.881105");
      worked = true;
    }
    catch (Exception e){
      //
    }
    assertTrue(worked);
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