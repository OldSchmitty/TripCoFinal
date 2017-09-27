package edu.csu2017fa314.T15.Model;

import java.util.ArrayList;
import java.util.HashMap;


public class Itinerary {

    private ArrayList<Edge> path;
    private long pathDistance;
    private ArrayList<String> keys;
    private Table edgeTable;
    private boolean test;

    /**
     * build a table of edges
     * @param map
     */
    public Itinerary(HashMap<String, Destination> map){
        keys = new ArrayList<String>(map.keySet());
        edgeTable = new Table(map);
        path = new ArrayList<Edge>();
        pathDistance = -1;
        test = false;
    }

    /**
     * Returns an Edge from the current destination to its nearest neighbor
     * @param id
     * @return
     */
    private Edge nearestNeighbor(String id, ArrayList<String> remainingKeys){
        Edge nearest = edgeTable.getEdge(id, remainingKeys.get(0));
        for (int i=0; i < remainingKeys.size(); i++){
            Edge check = edgeTable.getEdge(id, remainingKeys.get(i));
            if (check.getDistance() < nearest.getDistance()) {
                nearest = check;
            }
        }
        return nearest;
    }

    /**
     * Determine the shortest path to all destinations
     */
    private void shortestPath(){

        ArrayList<Edge> currentPath = new ArrayList<Edge>();

        // try all possible destinations as the starting point
        for (int i=0; i<keys.size(); i++){
            currentPath.clear();

            if (test) {
                System.out.println("start: " + keys.get(i));
            }

            ArrayList<String> remainingKeys = (ArrayList<String>) keys.clone(); // remaining destinations to check distance

            // pop current from remaining keys
            String currentID = keys.get(i);
            remainingKeys.remove(remainingKeys.indexOf(currentID));

            while (!remainingKeys.isEmpty()){
                Edge currentEdge = nearestNeighbor (currentID,remainingKeys);
                currentID = currentEdge.getDestinationID();

                // pop the next destination from remaing
                currentPath.add(currentEdge);
                remainingKeys.remove(remainingKeys.indexOf(currentID));
            }

            // Edge from last destination back to start, completing the roundtrip
            String startID = currentPath.get(0).getSourceID();
            String endID = currentPath.get(currentPath.size()-1).getDestinationID();
            currentPath.add(edgeTable.getEdge(endID, startID));

            isShorter(currentPath);
        }
    }

    /**
     * Checks if the current path is shorter
     * @param currentPath
     */
    private void isShorter(ArrayList<Edge> currentPath){
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
    private long pathDistance(ArrayList<Edge> currentPath){
        long distance = 0;

        for (int i = 0; i < currentPath.size(); i++) {
            if (test) {
                System.out.println("source: " + currentPath.get(i).getSourceID() + ", destination: " + currentPath.get(i).getDestinationID() + ", distance: " +
                            currentPath.get(i).getDistance());
            }
            distance += currentPath.get(i).getDistance();
        }
        return distance;
    }

    /**
     * Return the shortestPath
     * @return
     */
    public ArrayList<Edge> getShortestPath(){
        shortestPath();
        if (test) {
            for (int i = 0; i < path.size(); i++) {
                System.out.println("source: " + path.get(i).getSourceID() + ", destination: " + path.get(i).getDestinationID() + ", distance: " +
                        path.get(i).getDistance());
            }
        }
        return path;
    }

    public long getDistance(){
        return pathDistance;
    }
}
