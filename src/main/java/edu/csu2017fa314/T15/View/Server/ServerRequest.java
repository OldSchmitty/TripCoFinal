package edu.csu2017fa314.T15.View.Server;

import java.util.ArrayList;

/**
 * Created by sswensen on 10/1/17 modified by James DePoyster for use in TripCO.
 */

public class ServerRequest {
    private String query = "";
    private ArrayList<String> items;

    public ServerRequest(String query) {
        this.query = query;
        items = new ArrayList<>();
    }

    public void searchDatabase(){
        // Send query item to SQL server and then store returned result as items
        System.out.println("Looking for all Destinations with '" + query + "' in their name.");
    }

    public ArrayList<String> getItems(){ return this.items; }

    @Override
    public String toString() {
        return "Request{" +
                "query='" + query + '\'' +
                '}';
    }
}
