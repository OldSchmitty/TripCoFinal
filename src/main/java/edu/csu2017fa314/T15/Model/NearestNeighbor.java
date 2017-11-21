package edu.csu2017fa314.T15.Model;

import java.util.ArrayList;

public class NearestNeighbor {

    // reference to the global distanceTable and the currentDistance of our optimized path
    private Table distanceTable;
    private long currentDistance;


    /**
     * initialize the distanceTable and currentDistance
     *
     * @param distanceTable
     */
    public NearestNeighbor(Table distanceTable) {
        this.distanceTable = distanceTable;
    }


    /**
     * compute the nearestNeighbor of the current node from the set of all other nodes yet to be used
     *
     * @param id
     * @param remainingKeys
     */
    public Integer nearestNeighbor(Integer id, ArrayList<Integer> remainingKeys) {
        Integer nearest = -1;
        long nearDist = -1;
        // loop through remaining keys
        for (Integer key : remainingKeys) {
            long newDist = distanceTable.getDistance(id, key);
            if (nearDist < 0 || newDist < nearDist) {
                nearest = key;
                nearDist = newDist;
            }

        }
        currentDistance += nearDist;
        return nearest;
    }


    public long getCurrentDistance() {
        return currentDistance;
    }
    public void setCurrentDistance(long currentDistance){
        this.currentDistance = currentDistance;
    }
}
