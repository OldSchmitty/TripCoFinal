package edu.csu2017fa314.T15.View;

import java.util.HashMap;
import edu.csu2017fa314.T15.Model.Destination;
import edu.csu2017fa314.T15.Model.Edge;
import java.util.ArrayList;

/**
 * Created by sswensen on 10/1/17 modified by James DePoyster for use in TripCO.
 */

public class ServerResponse {
    private Destination[] items;

    public ServerResponse(Destination[] items) {
        this.items = items;
    }


    @Override
    public String toString() {
        return "ServerResponse{" + '\'' +
                ", aList=" + items.toString() +
                '}';
    }
}
