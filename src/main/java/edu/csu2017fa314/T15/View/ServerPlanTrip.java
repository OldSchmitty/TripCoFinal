package edu.csu2017fa314.T15.View;

import edu.csu2017fa314.T15.Model.Destination;
import edu.csu2017fa314.T15.Model.Edge;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerPlanTrip {

    private String svg;
    private Destination[] items;
    ArrayList<Edge> itinerary;


    public ServerPlanTrip(String svg, Destination[] items, ArrayList<Edge> itinerary) {
        this.svg = svg;
        this.items = items;
        this.itinerary = itinerary;
        System.out.println("Keys in the destinations\n" +items[0].getKeys());
    }

}