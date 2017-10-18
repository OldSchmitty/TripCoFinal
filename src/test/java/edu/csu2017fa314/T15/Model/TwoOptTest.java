package edu.csu2017fa314.T15.Model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class TwoOptTest {


    @Test
    public void test1(){
        HashMap<String, Destination> map  = new HashMap<String, Destination>();
        Destination b1 = new Destination("A", "Destination 1", "Test City 1", "0", "15", "1000");
        Destination b2 = new Destination("B", "Destination 2", "Test City 2", "11", "6", "1000");
        Destination b3 = new Destination("C", "Destination 5", "Test City 5", "12", "7", "1000");
        Destination b4 = new Destination("D", "Destination 4", "Test City 4", "13", "0", "1000");
        Destination b5 = new Destination("E", "Destination 5", "Test City 5", "9", "4", "1000");

        map.put("A", b1);
        map.put("B", b2);
        map.put("D", b4);
        map.put("E", b5);
        map.put("C", b3);

        Itinerary itinerary = new Itinerary(map);
        ArrayList<Edge> path = itinerary.getShortestPath();

        for (int i=0; i<path.size(); i++){
            System.out.print(path.get(i).getSourceID() + ", ");
        }
        System.out.println();

        /* Two Opt Test */
        Table distanceTable = itinerary.getTable();
        ArrayList<String> route = itinerary.getPath();
        TwoOpt TO = new TwoOpt(distanceTable, route);
        route = TO.getTwoOpt();

        for (int i=0; i<path.size(); i++){
            System.out.print(route.get(i) + ", ");
        }
    }


    /*
    @Test
    public void test2(){
        String path =System.getProperty("user.dir") + "/data/input/COrand75.csv";
        Model m = new Model(path);
        Itinerary i = new Itinerary(m.getMap());
        ArrayList<Edge> a = i.getShortestPath();
        Table distanceTable = i.getTable();
        ArrayList<String> route = i.getPath();
        TwoOpt TO = new TwoOpt(distanceTable, route);
        route = TO.getTwoOpt();
        assertEquals(2219, TO.getDistance());
    }
    */

}
