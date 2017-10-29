package edu.csu2017fa314.T15.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Itinerary {

    private Destination[] mapCopy;
    private Integer[] path;
    private long pathDistance;
    private ArrayList<Integer> keys;
    private Table distanceTable;
    private boolean test;
    private long currentDistance;

    /**
     * build a table of edges
     * @param map
     */
    public Itinerary(ArrayList<Destination> map){
        mapCopy = new Destination[map.size()];
        mapCopy = map.toArray(mapCopy);
        keys = new ArrayList<Integer>();
        for (Destination des : map){
            keys.add(des.getIdentifier());
        }
        distanceTable = new Table(mapCopy);
        path = new Integer[mapCopy.length+1];
        pathDistance = -1;
        test = false;
        currentDistance = 0;
    }

    /**
     * build a table of edges
     * @param map
     */
    public Itinerary(Destination[] map){
        mapCopy = map;
        keys = new ArrayList<Integer>();
        for (Destination des : map){
            keys.add(des.getIdentifier());
        }
        distanceTable = new Table(mapCopy);
        path = new Integer[mapCopy.length+1];
        pathDistance = -1;
        test = false;
        currentDistance = 0;
    }

    /**
     * Returns a String to the nearest neighbor
     * @param id
     * @return
     */
    private Integer nearestNeighbor(Integer id, ArrayList<Integer> remainingKeys){
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

    /**
     * Determine the shortest path to all destinations
     */
    private void shortestPath(){

        Integer[] currentPath = new Integer[mapCopy.length+1];

        // try all possible destinations as the starting point
        for (int i=0; i<keys.size(); i++){

            if (test) {
                System.out.println("start: " + keys.get(i));
            }
            
            // remaining destinations to check distance
            ArrayList<Integer> remainingKeys = new ArrayList<Integer>(keys);

            // pop current from remaining keys
            Integer currentID = keys.get(i);
            remainingKeys.remove(currentID);     // O(n)
            currentPath[0] = currentID;

            // loop remaining keys to find shortest path
            int count = 1;
            while (!remainingKeys.isEmpty()){
                Integer destinationID = nearestNeighbor(currentID, remainingKeys);
                // add destination to path and remove from remaining
                currentPath[count]=destinationID;
                count++;
                remainingKeys.remove(destinationID);
                currentID = destinationID;
            }


            // Add the start point to the end of the path to complete the loop
            Integer startID = currentPath[0];
            currentPath[currentPath.length-1]=startID;
            currentDistance += distanceTable.getDistance(
                    currentPath[currentPath.length-2],currentPath[0]);

            // Run 2Opt on each path
            TwoOpt tOpt = new TwoOpt(distanceTable, currentPath);
            currentPath = tOpt.getTwoOpt();

            // Check if the new path is shorter
            currentDistance = tOpt.getDistance();
            isShorter(currentPath);
            currentDistance = 0;
        }
    }

    /**
     * Checks if the current path is shorter
     * @param currentPath
     */
    private void isShorter(Integer[] currentPath){

        if (pathDistance < 0 || currentDistance < pathDistance) {

            if (test) {
                System.out.println("pathDistance: " + pathDistance);
                System.out.println("Distance: " + currentDistance);
                System.out.println("Found shorter Path." + pathDistance);
            }

            path = currentPath.clone();
            pathDistance = currentDistance;
        }
    }

    /**
     * Return the shortestPath
     * @return
     */
    public ArrayList<Edge> getShortestPath(){
        ArrayList<Edge> shortPath = new ArrayList<Edge>();
        shortestPath();

        for (int i=0; i<path.length-1; i++){
            Integer id1 = path[i];
            Integer id2 = path[i+1];
            shortPath.add(new Edge(id1, id2, distanceTable.getDistance(path[i], path[i+1])));
        }

        return shortPath;
    }

    public long getDistance(){
        return pathDistance;
    }

    /**
     * Testing Pursposes
     * @return
     */
    public Table getTable(){
        return distanceTable;
    }

    /**
     * Testing Purposes
     * @return
     */
    public Integer[] getPath(){
        return path;
    }
}
