package edu.csu2017fa314.T15.Model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ItineraryTest {

    private static Destination[] map;
    private static Itinerary itinerary;
    private static ArrayList<Edge> path;

    @BeforeClass
    public static void setup() throws Exception {
        map  = new Destination[4];
        Destination b1 = new Destination("0", "Destination 1", "Test City 1", "02", "00", "1000");
        b1.setIdentifier(0);
        Destination b2 = new Destination("1", "Destination 2", "Test City 2", "04", "00", "1000");
        b2.setIdentifier(1);
        Destination b4 = new Destination("3", "Destination 4", "Test City 4", "01", "00", "1000");
        b4.setIdentifier(2);
        Destination b5 = new Destination("4", "Destination 5", "Test City 5", "08", "00", "1000");
        b5.setIdentifier(3);
        map[0]= b1;
        map[1]= b2;
        map[2]= b4;
        map[3]= b5;

        itinerary = new Itinerary(map);
        path = itinerary.getShortestPath();
    }

    @Test
    public void test1() {
        Integer[] answers = {3, 1, 0, 2};
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
        assertEquals(i.getDistance(), 1746);
    }

    @Test
    public void test3() {
        String path =System.getProperty("user.dir") + "/data/input/COrand75.csv";
        Model m = new Model(path);
        Itinerary i = new Itinerary(m.getMap());
        ArrayList<Edge> a = i.getShortestPath();
        assertEquals(i.getDistance(), 2219);
    }

    @Test
    public void test4() {
        String path =System.getProperty("user.dir") + "/data/input/CO14ers.csv";
        Model m = new Model(path);
        Itinerary i = new Itinerary(m.getMap());
        ArrayList<Edge> a = i.getShortestPath();
        assertEquals(i.getDistance(), 774);
    }

    @Test
    public void test5() {
        String path =System.getProperty("user.dir") + "/data/input/ski.csv";
        Model m = new Model(path);
        Itinerary i = new Itinerary(m.getMap());
        ArrayList<Edge> a = i.getShortestPath();
        assertEquals(i.getDistance(), 650);
    }
}
