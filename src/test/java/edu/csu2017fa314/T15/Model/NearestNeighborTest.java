package edu.csu2017fa314.T15.Model;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class NearestNeighborTest {

    private static Destination[] map;
    private static Itinerary itinerary;

    @BeforeClass
    public static void setup() throws Exception{
        Destination b1 = new Destination("0", "Destination 1", "Test City 1", "02", "00", "1000");
        b1.setIdentifier(0);
        Destination b2 = new Destination("1", "Destination 2", "Test City 2", "04", "00", "1000");
        b2.setIdentifier(1);
        Destination b4 = new Destination("3", "Destination 4", "Test City 4", "01", "00", "1000");
        b4.setIdentifier(2);
        Destination b5 = new Destination("4", "Destination 5", "Test City 5", "08", "00", "1000");
        b5.setIdentifier(3);

        map =  new Destination[] {b1,b2,b4,b5};
        itinerary = new Itinerary(map, "Nearest Neighbor");
        itinerary.getShortestPath();
    }

    private long testItinerary(String path){

        Model testM = new Model(path);
        itinerary = new Itinerary(testM.getMap().toArray(new Destination[testM.getMap().size()]),
                "Nearest Neighbor");
        itinerary.getShortestPath();
        return itinerary.getDistance();
    }

    @Test
    public void test1(){
        CalculateDistance.setMiles();
        String path = System.getProperty("user.dir") + "/data/input/COrand50.csv";
        assertEquals(testItinerary(path), 1834);
    }

    @Test
    public void test2() {
        CalculateDistance.setMiles();
        String path =System.getProperty("user.dir") + "/data/input/COrand75.csv";
        assertEquals(testItinerary(path), 2454);
    }

    @Test
    public void test3() {
        CalculateDistance.setKilometers();
        String path = System.getProperty("user.dir") + "/data/input/CO14ers.csv";
        assertEquals(testItinerary(path), 1322);
    }

    @Test
    public void test4() {
        CalculateDistance.setKilometers();
        String path = System.getProperty("user.dir") + "/data/input/ski.csv";
        assertEquals(testItinerary(path), 1076);
    }



}