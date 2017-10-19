package edu.csu2017fa314.T15.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Table {

    //private long[][] distanceTable;
    private HashMap<String, Destination> map;
    private long[][] distanceTable;
    ArrayList<String> keys;

    /**
     * Constructor builds a map of all possible edges, in both directions
     * @param map
     */
    public Table(HashMap<String, Destination> map){

        this.map = map;
        keys = new ArrayList<String>(map.keySet());
        CalculateDistance cd = new CalculateDistance();

        // initialize distance tables
        int n = keys.size();
        distanceTable = new long[n][n];

        // Assign each Destinationtifier
        int counter = 0;
        for (String key: map.keySet()){
            map.get(key).setIdentifier(counter);
            counter++;
        }

        // Populate 2D distance table - using unique identifier for index
        for (String key: map.keySet()){
            for (String id: map.keySet()){
                distanceTable[map.get(key).getIdentifier()][map.get(id).getIdentifier()] =
                        cd.findDistanceBetween(map.get(key), map.get(id));
                distanceTable[map.get(id).getIdentifier()][map.get(key).getIdentifier()] =
                        cd.findDistanceBetween(map.get(key), map.get(id));
            }
        }

/*
        //loop through all destinations
        for (int i=0; i< keys.size(); i++){
            String key1 = keys.get(i);

            // populate distanceTable with distances between each remaining destination
            for (int j=i; j < keys.size(); j++){
                String key2 = keys.get(j);
                long distance = cd.findDistanceBetween(map.get(key1), map.get(key2));

                // populate the distance table
                distanceTable.put(key1 + " " + key2, distance);
                distanceTable.put(key2 + " " + key1, distance);
            }
        }
*/
    }

    private int idxOfKey(String key){
        return keys.indexOf(key);
    }

    public long getDistance(String id1, String id2){
        return distanceTable[map.get(id1).getIdentifier()][map.get(id2).getIdentifier()];
    }
}
