package edu.csu2017fa314.T15.Model;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ThreeOptTest {

    // This might be wrong - but i think it is right
    /**
     * Tests the first of seven options of 3-opt. The four destinations make an hourglass
     * before optimization.  Optimization using this method should uncross one side, so .
     */
    @Test
    public void testOption123(){

        CalculateDistance.setMiles(); // set distance calculations to miles

        // Creates a map of 4 destinations
        Destination[] map = new Destination[4];
        Destination b1 = new Destination("A", "Destination 1", "Test City 1",
                "-30", "0", "1000");
        Destination b2 = new Destination("B", "Destination 2", "Test City 2",
                "-30", "40", "1000");
        Destination b3 = new Destination("C", "Destination 3", "Test City 3",
                "30", "0", "1000");
        Destination b4 = new Destination("D", "Destination 4", "Test City 4",
                "30", "40", "1000");

        b1.setIdentifier(0);
        b2.setIdentifier(1);
        b3.setIdentifier(2);
        b4.setIdentifier(3);

        map[0] = b1;
        map[1] = b2;
        map[2] = b3;
        map[3] = b4;

        // Build a distance table and path
        Itinerary itinerary = new Itinerary(map, "None");
        itinerary.getShortestPath();
        Integer[] path = itinerary.getPath();
        Table distances = new Table(map);

        // Optimize the path - option 1
        ThreeOpt threeOpt = new ThreeOpt(distances, path);
        threeOpt.enableTestMode(1);
        path = threeOpt.getThreeOpt();

        // Check that the path is {0,1,3,2,0}
        Integer[] answers = {0,2,1,3,0};
        for (int i=0; i<path.length; i++){
            assertEquals(path[i], answers[i]);
        }
    }

    /**
     * Tests the second of seven options of 3-opt. The four destinations make an hourglass before
     * optimization.  Optimization using this method should uncross them so the path is square.
     */
    @Test
    public void testOption2(){

        CalculateDistance.setMiles(); // set distance calculations to miles

        // Creates a map of 4 destinations
        Destination[] map = new Destination[4];
        Destination b1 = new Destination("A", "Destination 1", "Test City 1",
                "-30", "0", "1000");
        Destination b2 = new Destination("B", "Destination 2", "Test City 2",
                "-30", "40", "1000");
        Destination b3 = new Destination("C", "Destination 3", "Test City 3",
                "30", "0", "1000");
        Destination b4 = new Destination("D", "Destination 4", "Test City 4",
                "30", "40", "1000");

        b1.setIdentifier(0);
        b2.setIdentifier(1);
        b3.setIdentifier(2);
        b4.setIdentifier(3);

        map[0] = b1;
        map[1] = b2;
        map[2] = b3;
        map[3] = b4;

        // Build a distance table and path
        Itinerary itinerary = new Itinerary(map, "None");
        itinerary.getShortestPath();
        Integer[] path = itinerary.getPath();
        Table distances = new Table(map);

        // Optimize the path - option 2
        ThreeOpt threeOpt = new ThreeOpt(distances, path);
        threeOpt.enableTestMode(2);
        path = threeOpt.getThreeOpt();

        // Check that the path is {0,1,3,2,0}
        Integer[] answers = {0,1,3,2,0};
        for (int i=0; i<path.length; i++){
            assertEquals(path[i], answers[i]);
        }
    }

    /**
     * Tests the first of seven options of 3-opt. The four destinations make an hourglass before
     * optimization.  Optimization using this method should uncross them so the path is square.
     */
    @Test
    public void testOption3(){

        CalculateDistance.setMiles(); // set distance calculations to miles

        // Creates a map of 4 destinations
        Destination[] map = new Destination[4];
        Destination b1 = new Destination("A", "Destination 1", "Test City 1",
                "-30", "0", "1000");
        Destination b2 = new Destination("B", "Destination 2", "Test City 2",
                "-30", "40", "1000");
        Destination b3 = new Destination("C", "Destination 3", "Test City 3",
                "30", "0", "1000");
        Destination b4 = new Destination("D", "Destination 4", "Test City 4",
                "30", "40", "1000");

        b1.setIdentifier(0);
        b2.setIdentifier(1);
        b3.setIdentifier(2);
        b4.setIdentifier(3);

        map[0] = b1;
        map[1] = b2;
        map[2] = b3;
        map[3] = b4;

        // Build a distance table and path
        Itinerary itinerary = new Itinerary(map, "None");
        itinerary.getShortestPath();
        Integer[] path = itinerary.getPath();
        Table distances = new Table(map);

        // Optimize the path - option 1
        ThreeOpt threeOpt = new ThreeOpt(distances, path);
        threeOpt.enableTestMode(3);
        path = threeOpt.getThreeOpt();

        // Check that the path is {0,1,3,2,0}
        Integer[] answers = {0,2,3,1,0};
        for (int i=0; i<path.length; i++){
            assertEquals(path[i], answers[i]);
        }
    }

    /**
     * Full test of 3-opt. Destinations are arranged in a hexagon, but order is scrambled. 3-opt
     * should unscramble them so the path follows the outline of the hexagon.
     */
    @Test
    public void fullTest(){
        CalculateDistance.setMiles(); // set distance calculations to miles

        // Creates a map of 4 destinations
        Destination[] map = new Destination[6];
        Destination b1 = new Destination("A", "Destination 1", "Test City 1",
                "-30", "-20", "1000");//
        Destination b2 = new Destination("B", "Destination 2", "Test City 2",
                "0", "-40", "1000");//
        Destination b3 = new Destination("C", "Destination 3", "Test City 3",
                "30", "20", "1000");//
        Destination b4 = new Destination("D", "Destination 4", "Test City 4",
                "-30", "20", "1000");//
        Destination b5 = new Destination("E", "Destination 5", "Test City 5",
                "30", "-20", "1000");//
        Destination b6 = new Destination("F", "Destination 6", "Test City 6",
                "0", "40", "1000");//

        b1.setIdentifier(0);
        b2.setIdentifier(1);
        b3.setIdentifier(2);
        b4.setIdentifier(3);
        b5.setIdentifier(4);
        b6.setIdentifier(5);

        map[0] = b1;
        map[1] = b2;
        map[2] = b3;
        map[3] = b4;
        map[4] = b5;
        map[5] = b6;

        // Build a distance table and path
        Itinerary itinerary = new Itinerary(map, "3-Opt Test");
        itinerary.getShortestPath();
        Integer[] path = itinerary.getPath();

        // Check that the path is {5,2,4,1,0,3,5}
        Integer[] answers = {5,2,4,1,0,3,5};
        for (int i=0; i<path.length; i++){
            assertEquals(path[i], answers[i]);
        }
    }

    /**
    Prints a path for testing
     */
    public void printPath(Integer[] path){
        for (int i=0; i<path.length; i++){
            System.out.print(path[i] + " ");
        }
        System.out.println();
    }

}
