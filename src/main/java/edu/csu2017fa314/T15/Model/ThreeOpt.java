package edu.csu2017fa314.T15.Model;

public class ThreeOpt {

    private Table distanceTable;    // table for calculating distances between two destinations
    private Integer[] route;        // current, optimized route. Changes are made in place

    /**
     * Constructor
     */
    ThreeOpt(Table distance, Integer[] path) {
        this.distanceTable = distance;
        this.route = path;
    }
}
