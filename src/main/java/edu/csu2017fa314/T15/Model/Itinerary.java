package edu.csu2017fa314.T15.Model;

import java.util.ArrayList;

public class Itinerary {

    private final int mapLength;
    private Integer[] path;
    private long pathDistance;
    private ArrayList<Integer> keys;
    private final Table distanceTable;
    private final boolean test;
    private long currentDistance;
    private final String opt;

    /**
     * build a table of edges
     * @param map
     */
    public Itinerary(Destination[] map, String opt) {
        this(map, opt, false);
    }

    public Itinerary(Destination[] map, String opt, Boolean test){
        this.opt = opt; this.test = test;
        mapLength = map.length;
        keys = new ArrayList<>();
        for (Destination des : map){
            keys.add(des.getIdentifier());
        }
        distanceTable = new Table(map);
        path = new Integer[mapLength+1];
        pathDistance = -1;
        currentDistance = 0;
    }

    /**
     * Returns a String to the nearest neighbor
     * @param id
     * @return
     */


    /**
     * Determine the shortest path to all destinations
     */
    private void shortestPath(){

        NearestNeighbor nn = new NearestNeighbor(distanceTable, currentDistance);
        Integer[] currentPath = new Integer[mapLength+1];

        if(this.opt.equals("None")){
            for(int i = 0; i < keys.size(); i++){
                currentPath[i] = keys.get(i);
            }

            currentPath[currentPath.length-1]=keys.get(0);
            for(int i = 0; i < currentPath.length-1; i++){
                pathDistance += distanceTable.getDistance(currentPath[i], currentPath[i+1]);
            }
            pathDistance += distanceTable.getDistance(currentPath[currentPath.length-1],
                    currentPath[0]) + 1;
            currentPath[currentPath.length-1]=keys.get(0);
            path = currentPath.clone();
            return;
        }


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
                Integer destinationID = nn.nearestNeighbor(currentID, remainingKeys);
                currentDistance=nn.getCurrentDistance();
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

            if(this.opt.equals("2-Opt")) {
                // Run 2Opt on each path
                TwoOpt tOpt = new TwoOpt(distanceTable, currentPath);
                currentPath = tOpt.getTwoOpt();

                // Check if the new path is shorter
                currentDistance = tOpt.getDistance();
            }

            if(this.opt.equals("3-Opt")){
                // do nothing for now
            }

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
