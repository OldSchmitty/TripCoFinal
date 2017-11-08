package edu.csu2017fa314.T15.Model;

import org.junit.Test;

public class ThreeOptTest {

    /**
     * Tests if ThreeOpt was imported correctly
     * @throws Exception - A problem importing ThreeOpt
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


}
