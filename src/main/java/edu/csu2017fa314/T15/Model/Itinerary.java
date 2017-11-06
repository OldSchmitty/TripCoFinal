package edu.csu2017fa314.T15.Model;

import java.util.ArrayList;

public class Itinerary {

    private Integer[] path; // the trip path to be planned later
    private long pathDistance; // the absolute total distance of the current planned trip
    private ArrayList<Integer> keys; // list of the destination codes for accessing information in the distances table
    private final Table distanceTable; // a lookup table that stores the distances between each destination
    private final boolean test; // sets if you get debug information
    private long currentDistance; // the latest total distance calculated to be compared with the path distance
    private final String opt; // the selected optimization that will be performed when making a path

    /**
     * Called when no test argument is given, which is set to false by default
     * @param map - array of destinations in trip
     * @param opt - optimization to be performed
     */

    public Itinerary(Destination[] map, String opt) {
        this(map, opt, false);
    }

    /**
     * Populates the keys ArrayList for indexing into the table with Destination codes, makes the table with the
     * destinations array, initializes path to be an array of length one more than the number of destinations, sets
     * pathDistance to -1 and the currentDistance to 0 to start accumulating distance
     * @param map - array of destinations in trip
     * @param opt - optimization to be performed
     * @param test - determines if in debug mode
     */

    public Itinerary(Destination[] map, String opt, Boolean test){
        this.opt = opt; this.test = test;
        keys = new ArrayList<>();
        for (Destination des : map){
            keys.add(des.getIdentifier());
        }
        distanceTable = new Table(map);
        path = new Integer[keys.size()+1];
        pathDistance = -1;
        currentDistance = 0;
    }

    /**
     * Determine the shortest path possible with a given algorithm to all destinations, or just adds everything in
     * order if no algorithm is selected.  Possible algorithms are Nearest Neighbor, 2-Opt, or 3-Opt.  For both 2-Opt
     * and 3-Opt, Nearest Neighbor is run on all with all remaining destinations beforehand.
     */
    private void shortestPath(){

        NearestNeighbor nn = new NearestNeighbor(distanceTable, currentDistance);
        Integer[] currentPath = new Integer[keys.size()+1];

        // If no opt is chosen, build the path and pathDistance by just adding everything in the order they came in
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


        // try all possible destinations as the starting point to determine the best one to start from
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

            // loop remaining keys to find shortest path with Nearest Neighbor
            int count = 1;
            while (!remainingKeys.isEmpty()){

                // run Nearest Neighbor starting from the current destination
                Integer destinationID = nn.nearestNeighbor(currentID, remainingKeys);
                // update currentDistance with calculation in Nearest Neighbor class
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
                // Run 2Opt on each path if chosen
                TwoOpt tOpt = new TwoOpt(distanceTable, currentPath);
                currentPath = tOpt.getTwoOpt();

                // update currentDistance with calculation in 2-Opt
                currentDistance = tOpt.getDistance();
            }

            if(this.opt.equals("3-Opt")){
                // do nothing for now
            }


            // check if the newly calculated path is shorter than the current path and update accordingly
            isShorter(currentPath);
            // reset the distance for the next loop iteration
            currentDistance = 0;
        }
    }

    /**
     * Checks if the current calculated path is shorter than the current known path, and if so will change path to
     * the latest calculation
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
     * Plan the trip by calculating the shortest path among destinations and then adds each destination in the path
     * as an Edge to be displayed in the Itinerary and on the map
     * @return - ArrayList<Edge>: collection of all legs of the trip after selected optimization is used
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

    /**
     * Returns the calculated distance of the entire path through all destinations
     * @return
     */

    public long getDistance(){
        return pathDistance;
    }

    /**
     * Returns the table that was populated by the destination array sent in to the constructor
     * @return
     */

    public Table getTable(){
        return distanceTable;
    }

    /**
     * Returns the path that was assembled through all destinations with a chosen opt
     * @return
     */

    public Integer[] getPath(){
        return path;
    }
}
