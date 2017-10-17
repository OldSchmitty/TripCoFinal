package edu.csu2017fa314.T15.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Table {

    private long[][] distanceTable;
    List<String> keys;

    /**
     * Constructor builds a map of all possible edges, in both directions
     * @param map
     */
    public Table(HashMap<String, Destination> map){

        keys = new ArrayList<String>(map.keySet());
        CalculateDistance cd = new CalculateDistance();
        distanceTable = new long[keys.size()][keys.size()];

        //loop through all destinations
        for (int i=0; i< keys.size(); i++){
            String key1 = keys.get(i);

            // populate distanceTable with distances between each remaining destination
            for (int j=(i+1); j < keys.size(); j++){
                String key2 = keys.get(j);
                long distance = cd.findDistanceBetween(map.get(key1), map.get(key2));

                // populate the distance table
                distanceTable[idxOfKey(key1)][idxOfKey(key2)] = distance;
                distanceTable[idxOfKey(key2)][idxOfKey(key1)] = distance;
            }
        }

        // populate the diagonal with 0's, distance to own location
        for (int i=0; i<keys.size(); i++){
            distanceTable[i][i] = 0;
        }
    }

    private int idxOfKey(String key){
        return keys.indexOf(key);
    }

    public long getDistance(String id1, String id2){
        return distanceTable[idxOfKey(id1)][idxOfKey(id2)];
    }
}
