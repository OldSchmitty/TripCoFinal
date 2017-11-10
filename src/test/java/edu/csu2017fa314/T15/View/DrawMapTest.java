package edu.csu2017fa314.T15.View;

import static org.junit.Assert.*;

import java.io.File;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class DrawMapTest {

    private String path = "."+ File.separator+"data" + File.separator + "test_output" + File.separator;
    private DrawMap d;
    private boolean worked;

    @Before
    public void initialize(){
        d = new DrawMap(path+"test.svg");
        worked = false;
    }

    /**
     * Draws a path from Fort Collins - Denver - Limon - Fort Collins
     */
    @Test
    public void addEdge(){
        try{
            d = new DrawMap(path + "TestDrawPath.svg");
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
            d = new DrawMap(path + "TestDrawPathHoriz.svg");
            d.addEdge("40°33′33″N", "105°4′41″W","40°33′33″N", "102°4′41″W"); //Fort Collins
            d.write();
        }
        catch (RuntimeException e){
            assertFalse("Write Failed to run", true);
        }
        assertTrue(new File(path+ "TestDrawPathHoriz.svg").exists());
    }

    /**
     * Draws a path straight down
     */
    @Test
    public void addEdgeVertical(){
        try{
            d = new DrawMap(path + "TestDrawPathVert.svg");
            d.addEdge("40°33′33″N", "105°4′41″W","30°33′33″N", "105°4′41″W");
            d.write();
        }
        catch (RuntimeException e){
            assertFalse("Write Failed to run", true);
        }
        assertTrue(new File(path+ "TestDrawPathVert.svg").exists());
    }

    /**
     * Draws the prime miridean, equator and diagonals
     */
    @Test
    public void drawBoundary(){
        try{
            d = new DrawMap(path + "TestDrawBoundaries.svg");
            // prime meridian
            d.addEdge("90°N", "0°W","90°S", "0°W");
            // equator
            d.addEdge("0°N", "0°E","0°S", "180°W");
            d.addEdge("0°N", "180°E","0°S", "0°W");

            // diagonals
            d.addEdge("90°N", "180°W","0°S", "0°E");
            d.addEdge("0°N", "0°W","90°S", "180°E");
            d.addEdge("90°N", "180°E","0", "0");
            d.addEdge("0", "0","90°S", "180°W");

            d.write();
        }
        catch (RuntimeException e){
            System.err.println(e.getMessage());
            assertFalse("Write Failed to run", true);
        }
        assertTrue(new File(path+ "TestDrawBoundaries.svg").exists());
    }

    /**
     * Draws boundary crosses w/o slope
     */
    @Test
    public void crossNoSlope(){
        try{
            d = new DrawMap(path + "TestDrawCrossNoSlope.svg");
            // straight across 0,0
            d.addEdge("0","160W","0","160E");
            // straight across north hemisphere
            d.addEdge("80N","160W","80N","160E");
            // straight across south hemisphere
            d.addEdge("80S","160W","80S","160E");
            d.write();
        }
        catch (RuntimeException e){
            assertFalse("Write Failed to run", true);
        }
        assertTrue(new File(path+ "TestDrawCrossNoSlope.svg").exists());
    }

    /**
     * Tests boundary crosses w/ slope
     */
    @Test
    public void crossSlope(){
        try{
            d = new DrawMap(path + "TestDrawCrossSlope.svg");
            // equator and prime maridian

            // equator to north
            d.addEdge("20N","110W","0","110E");
            // equator to south
            d.addEdge("0","140W","20S","140E");
            // south to south
            d.addEdge("80S","140W","70S","140E");
            // north to north
            d.addEdge("80N","140W","70N","140E");
            // south to north
            d.addEdge("40N","110W","40S","110E");

            d.write();
        }
        catch (RuntimeException e){
            assertFalse("Write Failed to run", true);
        }
        assertTrue(new File(path+ "TestDrawCrossSlope.svg").exists());
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

    /**
     * Test to see if offset works on world map
     */
    @Test
    public void atEquator(){
        try{
            d = new DrawMap(path + "TestDrawPathEquator.svg");
            d.addEdge("0", "0","1", "1"); //Need to be larger then a pixel to work
            d.write();
        }
        catch (RuntimeException e){
            assertFalse("Write Failed to run", true);
        }
        assertTrue(new File(path+ "TestDrawPathEquator.svg").exists());
    }

}
