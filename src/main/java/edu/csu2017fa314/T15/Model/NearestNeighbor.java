package edu.csu2017fa314.T15.Model;

import java.util.ArrayList;

public class NearestNeighbor {

    private Table distanceTable;
    private long currentDistance;


    public NearestNeighbor(Table distanceTable, long currentDistance){
        this.distanceTable = distanceTable;
        this.currentDistance = currentDistance;
    }

    public Integer nearestNeighbor(Integer id, ArrayList<Integer> remainingKeys){
        Integer nearest = -1;
        long nearDist = -1;
        // loop through remaining keys
        for (Integer key : remainingKeys){
            long newDist = distanceTable.getDistance(id, key);
            if (nearDist < 0 || newDist < nearDist){
                nearest = key;
                nearDist = newDist;
            }

        }
        currentDistance+=nearDist;
        return nearest;
    }

}
