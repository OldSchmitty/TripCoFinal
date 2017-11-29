package edu.csu2017fa314.T15.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Table {
    private long[][] distanceTable;
    ArrayList<Integer> keys;

    /**
     * Constructor builds a map of all possible edges, in both directions
     * @param map
     */
    public Table(Destination[] map){

        //keys = new ArrayList<Integer>(map.keySet());

        // initialize distance tables
        int n = map.length;
        distanceTable = new long[n][n];

        // Assign each Destinationtifier
        // Populate 2D distance table - using unique identifier for index
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map.length; j++){
                distanceTable[i][j] =
                        CalculateDistance.findDistanceBetween(map[i], map[j]);
                distanceTable[j][i] =
                        distanceTable[i][j];
            }
        }
    }

    public long getDistance(Integer id1, Integer id2){
        return distanceTable[id1][id2];
    }
}
