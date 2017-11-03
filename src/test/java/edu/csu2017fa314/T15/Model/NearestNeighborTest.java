package edu.csu2017fa314.T15.Model;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class NearestNeighborTest {

    private static Destination[] map;
    private static Itinerary itinerary;
    private static ArrayList<Edge> path;

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
        path = itinerary.getShortestPath();
    }

    @Test
    public void test1(){
        CalculateDistance.setMiles();
        String path =System.getProperty("user.dir") + "/data/input/COrand50.csv";
        Model m = new Model(path);
        Itinerary i = new Itinerary(m.getMap(),"Nearest Neighbor");
        this.path = i.getShortestPath();
        assertEquals(i.getDistance(), 2078);
    }

    @Test
    public void test2() {
        CalculateDistance.setMiles();
        String path =System.getProperty("user.dir") + "/data/input/COrand75.csv";
        Model m = new Model(path);
        Itinerary i = new Itinerary(m.getMap(), "Nearest Neighbor");
        this.path = i.getShortestPath();
        assertEquals(i.getDistance(), 2591);
    }

    @Test
    public void test3() {
        CalculateDistance.setKilometers();
        String path =System.getProperty("user.dir") + "/data/input/CO14ers.csv";
        Model m = new Model(path);
        Itinerary i = new Itinerary(m.getMap(), "Nearest Neighbor");
        this.path = i.getShortestPath();
        assertEquals(i.getDistance(), 1348);
    }

    @Test
    public void test4() {
        CalculateDistance.setKilometers();
        String path =System.getProperty("user.dir") + "/data/input/ski.csv";
        Model m = new Model(path);
        Itinerary i = new Itinerary(m.getMap(), "Nearest Neighbor");
        this.path = i.getShortestPath();
        assertEquals(i.getDistance(), 1207);
    }



}