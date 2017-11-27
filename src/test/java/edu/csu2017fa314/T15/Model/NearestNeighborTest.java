package edu.csu2017fa314.T15.Model;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class NearestNeighborTest {

    private static Destination[] map;
    private static Itinerary itinerary;


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