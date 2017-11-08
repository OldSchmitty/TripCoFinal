package edu.csu2017fa314.T15.Model;

public class ThreeOpt {

    private Table distanceTable;    // table for calculating distances between two destinations
    private Integer[] route;        // current, optimized route. Changes are made in place
    private boolean testMode;       // flag for testing 3-Opt
    private int optCase;            //

    /**
     * Constructor
     */
    ThreeOpt(Table distance, Integer[] path) {
        this.distanceTable = distance;
        this.route = path;
        this.testMode = false;
    }




    /**
     * Enables test mode, allowing specific cases to be selected.
     * optCase is the 3-Opt case to be tested
     */
    public void enableTestMode(int optCase){
        this.optCase = optCase;
        this.testMode = true;
    }

    /**
     * Disables test mode, reverting it back to default so all 3-Opt cases are used
     */
    public void disableTestMode(){
        this.testMode = false;
    }



}
