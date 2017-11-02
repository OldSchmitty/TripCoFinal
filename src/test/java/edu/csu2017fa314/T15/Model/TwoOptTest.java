package edu.csu2017fa314.T15.Model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class TwoOptTest {


    @Test
    public void test1(){
        Destination[] map  = new Destination[5];
        Destination b1 = new Destination("A", "Destination 1", "Test City 1", "0", "15", "1000");
        b1.setIdentifier(0);
        Destination b2 = new Destination("B", "Destination 2", "Test City 2", "11", "6", "1000");
        b2.setIdentifier(1);
        Destination b3 = new Destination("C", "Destination 5", "Test City 5", "12", "7", "1000");
        b3.setIdentifier(2);
        Destination b4 = new Destination("D", "Destination 4", "Test City 4", "13", "0", "1000");
        b4.setIdentifier(3);
        Destination b5 = new Destination("E", "Destination 5", "Test City 5", "9", "4", "1000");
        b5.setIdentifier(4);
        map[0] = b1;
        map[1] = b2;
        map[2] = b4;
        map[3] = b5;
        map[4] = b3;

        Itinerary itinerary = new Itinerary(map, "2-Opt");
        ArrayList<Edge> path = itinerary.getShortestPath();

        assertEquals(itinerary.getDistance(), 2886);
    }

    @Test
    public void test2(){
        String path =System.getProperty("user.dir") + "/data/input/COrand75.csv";
        Model m = new Model(path);
        Itinerary i = new Itinerary(m.getMap(), "2-Opt");
        ArrayList<Edge> a = i.getShortestPath();
        assertEquals(2219, i.getDistance());
    }


    @Test
    public void test3(){
        String path =System.getProperty("user.dir") + "/data/input/airport.csv";
        Model m = new Model(path);
        Itinerary i = new Itinerary(m.getMap(), "2-Opt");
        ArrayList<Edge> a = i.getShortestPath();
    }

    @Test
    public void test4(){
        String path =System.getProperty("user.dir") + "/data/input/sprint2heliport.csv";
        Model m = new Model(path);
        Itinerary i = new Itinerary(m.getMap(), "3-Opt");
        ArrayList<Edge> a = i.getShortestPath();
    }
}
