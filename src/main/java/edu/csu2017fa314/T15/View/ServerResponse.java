package edu.csu2017fa314.T15.View;

import java.util.HashMap;
import edu.csu2017fa314.T15.Model.Destination;

/**
 * Created by sswensen on 10/1/17 modified by James DePoyster for use in TripCO.
 */

public class ServerResponse {
    private String svg = "";
    private HashMap<String, Destination> items;


    public ServerResponse(String svg, HashMap<String, Destination> items) {
        this.svg = svg;
        this.items = items;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "svg='" + svg + '\'' +
                ", aList=" + items.toString() +
                '}';
    }
}
