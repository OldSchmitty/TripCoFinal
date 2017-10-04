package edu.csu2017fa314.T15.Model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ItineraryTest {

    private HashMap<String, Destination> map;
    private Itinerary itinerary;
    private ArrayList<Edge> path;

    @Before
    public void setup() throws Exception {
        map  = new HashMap<String, Destination>();
        Destination b1 = new Destination("0", "Destination 1", "Test City 1", "02", "00", "1000");
        Destination b2 = new Destination("1", "Destination 2", "Test City 2", "04", "00", "1000");
        Destination b4 = new Destination("3", "Destination 4", "Test City 4", "01", "00", "1000");
        Destination b5 = new Destination("4", "Destination 5", "Test City 5", "08", "00", "1000");

        map.put("0", b1);
        map.put("1", b2);
        map.put("3", b4);
        map.put("4", b5);

        itinerary = new Itinerary(map);
        path = itinerary.getShortestPath();
    }

    @Test
    public void test1() {
        String[] answers = {"0", "3", "1", "4"};
        for (int i=0;  i < path.size(); i++){
            assertEquals(path.get(i).getSourceID(), answers[i]);
        }
    }

    @Test
    public void test2() {
        String path =System.getProperty("user.dir") + "/data/input/COrand50.csv";
        Model m = new Model(path);
        Itinerary i = new Itinerary(m.getMap());
        ArrayList<Edge> a = i.getShortestPath();
        assertEquals(i.getDistance(), 1834);
    }

    @Test
    public void test3() {
        String path =System.getProperty("user.dir") + "/data/input/COrand75.csv";
        Model m = new Model(path);
        Itinerary i = new Itinerary(m.getMap());
        ArrayList<Edge> a = i.getShortestPath();
        assertEquals(i.getDistance(), 2454);
    }

    @Test
    public void test4() {
        String path =System.getProperty("user.dir") + "/data/input/CO14ers.csv";
        Model m = new Model(path);
        Itinerary i = new Itinerary(m.getMap());
        ArrayList<Edge> a = i.getShortestPath();
        assertEquals(i.getDistance(), 817);
    }

    @Test
    public void test5() {
        String path =System.getProperty("user.dir") + "/data/input/ski.csv";
        Model m = new Model(path);
        Itinerary i = new Itinerary(m.getMap());
        ArrayList<Edge> a = i.getShortestPath();
        assertEquals(i.getDistance(), 668);
    }
}
