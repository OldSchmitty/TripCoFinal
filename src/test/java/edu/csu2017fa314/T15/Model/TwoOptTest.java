package edu.csu2017fa314.T15.Model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TwoOptTest {

    @Test
    public void test1(){
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
}
