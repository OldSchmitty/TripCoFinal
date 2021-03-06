package edu.csu2017fa314.T15.Model;

import java.util.Arrays;

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
     * Optimizes Integer[] route by performing 3-Opt optimization. 3-Opt looks at three edges of a
     * route and determines if a shorter route (read optimized) can be obtained by deleting the
     * three selected edges and adding three new edges such that the route remains intact and
     * complete, with a shorter total distance. It does this by comparing the sum of the distances
     * of the three edges with the sum of the distances of three new edges to see if there is a
     * shorter path. There are 7 cases it will check against the current edges to exhaust all
     * possibilities. This implementation accomplishes this by comparing the sum of the edges for
     * the 7 cases, as well as the base case and determining which case to use by selecting the one
     * with the lowest total distance of the three edges. It does not physically delete and re-add
     * edges, but rather inverts and swaps sections of the route to 'redraw' the edges in place.
     *
     * <p>
     * edge 1       edge 2        edge 3           new edges:
     * case 0:  original     i   i+1 --> j     j+1 --> k    k+1   (i,i+1)     (j,j+1)     (k,k+1)
     * case 1:  2-opt        i   j   <-- i+1   j+1 --> k    k+1   (i,j)       (i+1,j+1)   (k,k+1)
     * case 2:  2-opt        i   i+1 --> j     k   <-- j+1  k+1   (i,i+1)     (j,k)       (j+1,k+1)
     * case 3:  2-opt        i   k   <-- j+1   j   <-- i+1  k+1   (i,k)       (j+1,j)     (i+1,k+1)
     * case 4:  3-opt        i   j   <-- i+1   k   <-- j+1  k+1   (i,j)       (i+1,k)     (j+1,k+1)
     * case 5:  3-opt        i   k   <-- j+1   i+1 --> j    k+1   (i,k)       (j+1,i+1)   (j,k+1)
     * case 6:  3-opt        i   j+1 --> k     j   <-- i+1  k+1   (i,j+1)     (k,j)       (i+1,k+1)
     * case 7:  3-opt        i   j+1 --> k     i+1 --> j    k+1   (i,j+1)     (k,i+1)     (j,k+1)
     * distances    (i, ?)       (?, ?)        (?, k+1) </p>
     */
    private void threeOpt() {

        int n = route.length - 1; // index of the last destination in the route

        for (int i = 0; i <= n - 3; i++) {
            for (int j = i + 1; j <= n - 2; j++) {
                for (int k = j + 1; k <= n - 1; k++) {
                    switch (chooseOption(i, j, k)) {
                        // determines which 3 edges will replace (i,i+1), (j,j+1), (k,k+1)
                        case 0:
                            // No action taken, use edges (i,i+1), (k,k+1), (j,j+1)
                            break;
                        case 1:
                            // replace edges (i,i+1), (j,j+1), (k,k+1) with (i,j),
                            // (i+1,j+1), (k,k+1)
                            invert(i + 1, j);
                            break;
                        case 2:
                            // replace edges (i,i+1), (j,j+1), (k,k+1) with (i,i+1),
                            // (j,k), (j+1,k+1)
                            invert(j + 1, k);
                            break;
                        case 3:
                            // replace edges (i,i+1), (j,j+1), (k,k+1) with (i,k),
                            // (j+1,j), (i+1,k+1)
                            invert(i + 1, k);
                            break;
                        case 4:
                            // replace edges (i,i+1), (j,j+1), (k,k+1) with (i,j),
                            // (i+1,k), (j+1,k+1)
                            invert(i+1,j);
                            invert(j+1,k);
                            break;
                        case 5:
                            // replace edges (i,i+1), (j,j+1), (k,k+1) with (i,k),
                            // (j+1,i+1), (j,k+1)
                            invert(j+1,k);
                            swapSections(i+1,j,j+1,k);
                            break;
                        case 6:
                            // replace edges (i,i+1), (j,j+1), (k,k+1) with (i,j+1),
                            // (k,j), (i+1,k+1)
                            invert(i+1,j);
                            swapSections(i+1,j,j+1,k);
                            break;
                        case 7:
                            // replace edges (i,i+1), (j,j+1), (k,k+1) with (i,j+1),
                            // (k,i+1), (j,k+1)
                            swapSections(i+1,j,j+1,k);
                            break;
                    }
                }
            }
        }
    }

    /**
     * Determines which 3-Opt case to use by comparing the change in distance of the edges that may
     * be changed.  Each case tests if the sum of distances of three edges is less than the sum of
     * the distances of edges (i,i+1), (j,j+1), (k,k+1).
     *
     * @param i index of destination i
     * @param j index of destination j
     * @param k index of destination k
     * @returns 0-7, indicating which 3-opt case should be used - 0 is the current edges, 1-7
     *          indicates there are three edges that produce a shorter path
     */
    private int chooseOption(int i, int j, int k) {

        // if testMode is on, use the 3-opt case that is being tested
        if (testMode) {
            return optCase;
        }

        // calculate all option distances
        long[] options = new long[8]; // (1)original + (3)2-Opt + (4)3-Opt
        options[0] = dist(i,i+1) + dist(j,j+1) + dist(k,k+1);  // original
        options[1] = dist(i,j) + dist(i+1,j+1) + dist(k,k+1);  // 2-opt 1
        options[2] = dist(i,i+1) + dist(j,k) + dist(j+1,k+1);  // 2-opt 2
        options[3] = dist(i,k) + dist(j+1,j) + dist(i+1,k+1);  // 2-opt 3
        options[4] = dist(i,j) + dist(i+1,k) + dist(j+1,k+1);  // 3-opt 1
        options[5] = dist(i,k) + dist(j+1,i+1) + dist(j,k+1);  // 3-opt 2
        options[6] = dist(i,j+1) + dist(k,j) + dist(i+1,k+1);  // 3-opt 3
        options[7] = dist(i,j+1) + dist(k,i+1) + dist(j,k+1);  // 3-opt 4

        long minimum = options[0];
        int minIdx = 0;

        // choose the lowest
        for (int idx=0; idx<options.length; idx++){
            if (options[idx] < minimum){
                minIdx = idx;
            }
        }

        return minIdx;
    }

    /**
     * Swaps 2 sections of destinations in a path.
     * If path is 0,1,...section1,...,section2,...,0
     * if becomes 0,1,...section2,...,section1,...,0
     *
     * @param start1 start index of first section
     * @param end1   end index of first section
     * @param start2 start index of second section
     * @param end2   end index of second section
     */
    private void swapSections(int start1, int end1, int start2, int end2) {

        // section that precedes the first section to be swapped
        Integer[] source = Arrays.copyOfRange(route, 0, start1);
        //first section to be swapped
        Integer[] section1 = Arrays.copyOfRange(route, start1, end1 + 1);
        // section between two sections to be swapped
        Integer[] midsection = Arrays.copyOfRange(route, end1 + 1, start2);
        // second section to be swapped
        Integer[] section2 = Arrays.copyOfRange(route, start2, end2 + 1);
        // section that follows the second section to be swapped
        Integer[] terminal = Arrays.copyOfRange(route, end2 + 1, route.length);

        // Concatenates the array segments, swapping the position of the two sections being swapped
        this.route = (Integer[]) joinArrays(source, section2, midsection, section1, terminal);
    }

    /**
     * Concatenates multiple arrays into a single array.
     *
     * @param params arrays to concatenate
     * @return array of concatenated arrays
     */
    private Integer[] joinArrays(Integer[]... params) {

        // size of the new array should be the same size as the route
        Integer[] result = new Integer[route.length];
        int j = 0; // write position in new array

        for (Integer[] array : params) {  // loop the array segments
            for (Integer s : array) {   // add each element of each segment
                result[j++] = s;
            }
        }

        return result;
    }

    /**
     * Inverts the order of destinations from the start index to the end index of the destinations
     * in the route array. Useful for 'deleting' and 'adding' new edges by
     * inverting sections of the route in place.
     *
     * @param i index of destination to start at
     * @param j index of destination to end at
     */
    private void invert(int i, int j) {

        // Simple in-place swap that starts on boundaries and works towards the center of a segment
        while (i < j) {
            Integer temp = route[i];
            route[i] = route[j];
            route[j] = temp;
            i++;
            j--;   // move from outer elements towards the center to swap the next pair
        }
    }

    /**
     * Calculates the distance between two destinations
     *
     * @param i index of first destination
     * @param j index of second destination
     * @return distance between two destinations
     */
    private long dist(int i, int j) {
        return distanceTable.getDistance(route[i], route[j]);
    }

    /**
     * Prints the route in its current state, it could be partially optimized, fully optimized, or
     * un-optimized.
     * For Testing Purposes
     */
    private void printRoute() {
        System.out.print("Route: ");
        for (int i = 0; i < route.length; i++) {
            System.out.print(route[i] + " ");
        }
        System.out.println();
    }

    /**
     * Enables test mode, allowing specific cases to be selected.
     *
     * @params optCase the 3-Opt case to be tested
     */
    public void enableTestMode(int optCase) {
        this.optCase = optCase;
        this.testMode = true;
    }

    /**
     * Disables test mode, reverting it back to default so all 3-Opt cases are used
     */
    public void disableTestMode() {
        this.testMode = false;
    }

    /**
     * Allows users to run ThreeOpt from other classes
     *
     * @returns the optimized route
     */
    public Integer[] getThreeOpt() {
        threeOpt();
        return route;
    }

    /**
     * Allows users to get the current distance of the current route
     *
     * @returns the distance of the route in its current state
     */
    public long getDistance() {

        long distance = 0;

        // calculate route distance
        for (int i = 0; i < route.length - 1; i++) {
            distance += distanceTable.getDistance(route[i], route[i + 1]);
        }

        return distance;
    }

}
