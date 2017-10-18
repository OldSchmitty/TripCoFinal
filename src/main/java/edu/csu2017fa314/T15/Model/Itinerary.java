package edu.csu2017fa314.T15.Model;

import java.util.ArrayList;
import java.util.HashMap;


public class Itinerary {

    private ArrayList<String> path;
    private long pathDistance;
    private ArrayList<String> keys;
    private Table distanceTable;
    private boolean test;

    /**
     * build a table of edges
     * @param map
     */
    public Itinerary(HashMap<String, Destination> map){
        keys = new ArrayList<String>(map.keySet());
        distanceTable = new Table(map);
        path = new ArrayList<String>();
        pathDistance = -1;
        test = false;
    }

    /**
     * Returns a String to the nearest neighbor
     * @param id
     * @return
     */
    private String nearestNeighbor(String id, ArrayList<String> remainingKeys){
        String nearest = remainingKeys.get(0);
        long nearDistance = distanceTable.getDistance(id, nearest);

        for (int i=0; i < remainingKeys.size(); i++){
            String id2 = remainingKeys.get(i);
            long newDistance = distanceTable.getDistance(id, id2);
            if (newDistance < nearDistance){
                nearest = remainingKeys.get(i);
                nearDistance = newDistance;
            }
        }
        return nearest;
    }

    /**
     * Determine the shortest path to all destinations
     */
    private void shortestPath(){

        ArrayList<String> currentPath = new ArrayList<String>();

        // try all possible destinations as the starting point
        for (int i=0; i<keys.size(); i++){
            currentPath.clear();

            if (test) {
                System.out.println("start: " + keys.get(i));
            }

            // remaining destinations to check distance
            ArrayList<String> remainingKeys = (ArrayList<String>) keys.clone();

            // pop current from remaining keys
            String currentID = keys.get(i);
            remainingKeys.remove(remainingKeys.indexOf(currentID));
            currentPath.add(currentID);

            // loop remaining keys to find shortest path
            while (!remainingKeys.isEmpty()){
                String destinationID = nearestNeighbor(currentID, remainingKeys);
                // add destination to path and remove from remaining
                currentPath.add(destinationID);
                remainingKeys.remove(remainingKeys.indexOf(destinationID));

                currentID = destinationID;
            }

            // Add the start point to the end of the path to complete the loop
            String startID = currentPath.get(0);
            currentPath.add(startID);

            isShorter(currentPath);
        }
    }

    /**
     * Checks if the current path is shorter
     * @param currentPath
     */
    private void isShorter(ArrayList<String> currentPath){
        long distance = pathDistance(currentPath);

        if (pathDistance < 0 || distance < pathDistance) {

            if (test) {
                System.out.println("pathDistance: " + pathDistance);
                System.out.println("Distance: " + distance);
                System.out.println("Found shorter Path." + pathDistance);
            }

            path.clear();
            path.addAll(currentPath);
            pathDistance = distance;
        }
    }

    /**
     * Calculates the total distance of a path
     * @param currentPath
     * @return
     */
    private long pathDistance(ArrayList<String> currentPath){
        long distance = 0;

        for (int i = 0; i < currentPath.size()-1; i++) {
            distance += distanceTable.getDistance(currentPath.get(i), currentPath.get(i+1));
        }
        return distance;
    }

    /**
     * Return the shortestPath
     * @return
     */
    public ArrayList<Edge> getShortestPath(){
        ArrayList<Edge> shortPath = new ArrayList<Edge>();
        shortestPath();

        for (int i=0; i<path.size()-1; i++){
            String id1 = path.get(i);
            String id2 = path.get(i);
            shortPath.add(new Edge(id1, id2, distanceTable.getDistance(id1, id2)));
        }

        return shortPath;
    }

    public long getDistance(){
        return pathDistance;
    }
}
