package edu.csu2017fa314.T15.Model;

import java.util.ArrayList;
import java.util.Arrays;

public class TwoOpt {

    private Table distanceTable;
    private String[] route;
    private long routeDistance;

    /**
     * Constructor for the 2-opt class
     * @param distance - is a table of distances between destinations
     * @param path - the path to be 2-opted
     */
    public TwoOpt(Table distance, ArrayList<String> path){
        this.distanceTable = distance;
        this.route = path.toArray(new String[path.size()]);
        this.routeDistance = 0;
    }

    /**
     * Reverses the segment of an array by swapping route in place
     * @param i1 - start index
     * @param k - end index
     */
    private void swap(int i1, int k) {
        while (i1 < k) {
            String temp = route[i1];
            route[i1] = route[k];
            route[k] = temp;
            i1++; k--;
        }
    }

    /**
     * @param start - start index
     * @param end - end index
     * @return - total distance between route[start] and route[end]
     */
    private long dist(int start, int end){
        return distanceTable.getDistance(route[start], route[end]);
    }

    /**
     * performes 2-opt on route
     */
    private void twoOpt(){
        int n = route.length-1;
        boolean improvement = true;
        while (improvement) {
            improvement = false;
            if (n < 4){
            } else {
                for (int i=0; i <= n-3; i++){
                    for (int k= i+2; k <= n-1; k++){
                        long delta = dist(i,k) + dist(i+1, k+1) - dist(i, i+1) - dist(k, k+1);
                        if (delta < 0) { //improvement?
                            swap(i + 1, k);
                            improvement = true;
                        }
                    }
                }
            }
        }
    }

    public ArrayList<String> getTwoOpt(){
        twoOpt(); // run 2-Opt
        ArrayList<String> path = new ArrayList<String>(Arrays.asList(route));
        routeDistance = dist(0,route.length-1);
        return path;
    }

    public long getDistance(){
        long distance = 0;
        for (int i=0; i<route.length-1; i++){
            distance += distanceTable.getDistance(route[i], route[i+1]);
        }
        return distance;
    }
}
