package edu.csu2017fa314.T15.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Table {

    private HashMap<String, Edge> table = new HashMap<String, Edge>();

    /**
     * Constructor builds a map of all possible edges, in both directions
     * @param map
     */
    public Table(HashMap<String, Destination> map){

        List<String> keys = new ArrayList<String>(map.keySet());
        CalculateDistance cd = new CalculateDistance();

        for (int i = 0; i < keys.size(); i++){
            String key1 = keys.get(i);

            // create an edge in either direction between all remaining destinations
            for (int j = (i+1); j < keys.size(); j++){
                String key2 = keys.get(j);
                long distance = cd.findDistanceBetween(map.get(key1), map.get(key2));

                Edge e1 = new Edge(key1, key2, distance);
                table.put(key1+key2, e1);

                Edge e2 = new Edge(key2, key1, distance);
                table.put(key2+key1, e2);
            }
        }
    }

    /**
     * return the Edge between two destination ids
     * @param id1
     * @param id2
     * @return
     */
    public Edge getEdge(String id1, String id2){
        return table.get(id1+id2);
    }
}
