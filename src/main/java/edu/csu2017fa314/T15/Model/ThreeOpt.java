package edu.csu2017fa314.T15.Model;

public class ThreeOpt {

    private Table distanceTable;    // table for calculating distances between two destinations
    private Integer[] route;        // current, optimized route. Changes are made in place
    private boolean testMode;       // flag for testing 3-Opt
    private int optCase;            // flags which testcase will be used for testing

    /**
     * Constructor
     */
    ThreeOpt(Table distance, Integer[] path) {
        this.distanceTable = distance;
        this.route = path;
        this.testMode = false;
    }

    /**
     *      Optimizes Integer[] route by performing 3-Opt optimization. 3-Opt looks at three edges of a route and
     * determines if a shorter route (read optimized) can be obtained by deleting the three selected edges and adding
     * three new edges such that the route remains intact and complete, with a shorter total distance. It does this by
     * comparing the sum of the distances of the three edges with the sum of the distances of three new edges to see if
     * there is a shorter path. There are 7 cases it will check against the current edges to exhaust all possibilities.
     *      This implementation accomplishes this by comparing the sum of the edges for the 7 cases, as well as the
     * base case and determining which case to use by selecting the one with the lowest total distance of the three
     * edges. It does not physically delete and re-add edges, but rather inverts and swaps sections of the route to
     * 'redraw' the edges in place.
     *
     *              edge 1       edge 2        edge 3           new edges:
     * case 0:  original     i   i+1 --> j     j+1 --> k    k+1          (i,i+1)     (j,j+1)     (k,k+1)
     * case 1:  2-opt        i   j   <-- i+1   j+1 --> k    k+1          (i,j)       (i+1,j+1)   (k,k+1)
     * case 2:  2-opt        i   i+1 --> j     k   <-- j+1  k+1          (i,i+1)     (j,k)       (j+1,k+1)
     * case 3:  2-opt        i   k   <-- j+1   j   <-- i+1  k+1          (i,k)       (j+1,j)     (i+1,k+1)
     * case 4:  3-opt        i   j   <-- i+1   k   <-- j+1  k+1          (i,j)       (i+1,k)     (j+1,k+1)
     * case 5:  3-opt        i   k   <-- j+1   i+1 --> j    k+1          (i,k)       (j+1,i+1)   (j,k+1)
     * case 6:  3-opt        i   j+1 --> k     j   <-- i+1  k+1          (i,j+1)     (k,j)       (i+1,k+1)
     * case 7:  3-opt        i   j+1 --> k     i+1 --> j    k+1          (i,j+1)     (k,i+1)     (j,k+1)
     *          distances    (i, ?)       (?, ?)        (?, k+1)
     */
    private void threeOpt(){

        int n = route.length-1; // index of the last destination in the route

        for (int i=0; i<=n-3; i++){
            for (int j=i+1; j<=n-2; j++){
                for (int k=j+1; k<=n-1; k++){
                    switch(chooseOption(i,j,k)) {   // determines which 3 edges will replace (i,i+1), (j,j+1), (k,k+1)
                        case 0:
                            // No action taken, use edges (i,i+1), (k,k+1), (j,j+1)
                            break;
                        case 1:
                            // replace edges (i,i+1), (j,j+1), (k,k+1) with (i,j), (i+1,j+1), (k,k+1)
                            break;
                        case 2:
                            // replace edges (i,i+1), (j,j+1), (k,k+1) with (i,i+1), (j,k), (j+1,k+1)
                            break;
                        case 3:
                            // replace edges (i,i+1), (j,j+1), (k,k+1) with (i,k), (j+1,j), (i+1,k+1)
                            break;
                        case 4:
                            // replace edges (i,i+1), (j,j+1), (k,k+1) with (i,j), (i+1,k), (j+1,k+1)
                            break;
                        case 5:
                            // replace edges (i,i+1), (j,j+1), (k,k+1) with (i,k), (j+1,i+1), (j,k+1)
                            break;
                        case 6:
                            // replace edges (i,i+1), (j,j+1), (k,k+1) with (i,j+1), (k,j), (i+1,k+1)
                            break;
                        case 7:
                            // replace edges (i,i+1), (j,j+1), (k,k+1) with (i,j+1), (k,i+1), (j,k+1)
                            break;
                    }
                }
            }
        }
    }

    /**
     * Determines which 3-Opt case to use by comparing the change in distance of the edges that may be changed.
     * Each case tests if the sum of distances of three edges is less than the sum of the distances of edges
     * (i,i+1), (j,j+1), (k,k+1).
     * @param i index of destination i
     * @param j index of destination j
     * @param k index of destination k
     * @returns 0-7, indicating which 3-opt case should be used - 0 is the current edges, 1-7 indicates there are three
     * edges that produce a shorter path
     */
    private int chooseOption(int i, int j, int k){

        // if testMode is on, use the 3-opt case that is being tested
        if ( testMode ){
            return optCase;
        }

        return 0;
    }


    /**
     * Calculates the distance between two destinations
     * @param i index of first destination
     * @param j index of second destination
     * @return  distance between two destinations
     */
    private long dist(int i, int j){
        return distanceTable.getDistance(route[i], route[j]);
    }

    /**
     * Prints the route in its current state, it could be partially optimized, fully optimized, or un-optimized.
     * For Testing Purposes
     */
    private void printRoute(){
        System.out.print("Route: ");
        for (int i=0; i<route.length; i++){
            System.out.print(route[i] + " ");
        }
        System.out.println();
    }

    /**
     * Enables test mode, allowing specific cases to be selected.
     * @params optCase the 3-Opt case to be tested
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
