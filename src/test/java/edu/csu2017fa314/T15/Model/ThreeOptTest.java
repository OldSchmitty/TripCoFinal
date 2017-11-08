package edu.csu2017fa314.T15.Model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ThreeOptTest {

    /**
     * Tests if ThreeOpt was imported correctly
     */
    @Test
    public void testConstructor(){

        CalculateDistance.setMiles();

        // Create map of destinations
        Destination[] map = new Destination[2];

        // Create destinations
        Destination b1 = new Destination("A", "Destination 1", "Test City 1",
                "0", "0","1000");
        Destination b2 = new Destination("B", "Destination 2", "Test City 2", "-30",
                "0", "1000");

        // Add destinations to map
        b1.setIdentifier(0); map[0] = b1;
        b2.setIdentifier(1); map[1] = b2;

        // Create a distance Table
        Table distances = new Table(map);

        // Get a path of destinations
        Itinerary itin = new Itinerary(map, "None");
        Integer[] path = itin.getPath();

        // Test Case
        ThreeOpt tOpt = new ThreeOpt(distances, path);
    }


    /**
     * Tests the first of seven options of 3-opt. The four destinations make an hourglass before optimization.
     * Optimization using this method should uncross them so the path is square.
     */
    @Test
    public void testOption3(){

        CalculateDistance.setMiles(); // set distance calculations to miles

        // Creates a map of 4 destinations
        Destination[] map = new Destination[4];
        Destination b1 = new Destination("A", "Destination 1", "Test City 1", "-30", "0", "1000");
        Destination b2 = new Destination("B", "Destination 2", "Test City 2", "-30", "40", "1000");
        Destination b3 = new Destination("C", "Destination 3", "Test City 3", "30", "0", "1000");
        Destination b4 = new Destination("D", "Destination 4", "Test City 4", "30", "40", "1000");
        b1.setIdentifier(0); b2.setIdentifier(1); b3.setIdentifier(2); b4.setIdentifier(3);
        map[0] = b1; map[1] = b2; map[2] = b3; map[3] = b4;

        // Build a distance table and path
        Itinerary itinerary = new Itinerary(map, "None");
        itinerary.getShortestPath();
        Integer[] path = itinerary.getPath();
        Table distances = new Table(map);

        // Optimize the path - option 1
        ThreeOpt tOpt = new ThreeOpt(distances, path);
        tOpt.enableTestMode(3);
        path = tOpt.getThreeOpt();

        // Check that the path is {0,1,3,2,0}
        Integer[] answers = {0,1,3,2,0};
        for (int i=0; i<path.length; i++){
            assertEquals(path[i], answers[i]);
        }
    }

    /*
    Prints a path for testing
     */
    public void printPath(Integer[] path){
        for (int i=0; i<path.length; i++){
            System.out.print(path[i] + " ");
        }
        System.out.println();
    }

}
