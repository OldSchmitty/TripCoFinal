package edu.csu2017fa314.T15.View;

import static org.junit.Assert.*;

import java.io.File;
import org.junit.Before;
import org.junit.Test;

public class DrawMapTest {

  private String path = "."+ File.separator+"data" + File.separator + "colorado.svg";
  private DrawMap d;
  private boolean worked;

  @Before
  public void initialize(){
    d = new DrawMap(path);
    worked = false;
  }



  @Test
  public void drawColorado() throws Exception {
    try{
      d.drawColorado();
      worked = true;
    }
    catch (Exception e){
      //
    }
    assertTrue(worked);
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

  @Test
  public void write() throws Exception {
    try{
      d.write();
      worked =true;
    }
    catch (Exception e){
      //
    }
    assertTrue(worked);
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
