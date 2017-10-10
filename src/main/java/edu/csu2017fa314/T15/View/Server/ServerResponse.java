package edu.csu2017fa314.T15.View.Server;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Created by sswensen on 10/1/17 modified by James DePoyster for use in TripCO.
 */

public class ServerResponse {
    private String svg = "";
    private ArrayList<String> items;


    public ServerResponse(String svg, ArrayList<String> items) {
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
