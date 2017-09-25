package edu.csu2017fa314.T15.Model;


/**
 * Class to hold relevent information for edges between nodes
 * @version 1 - 9/19/2017 initial
 */
public class Edge {

    private String sourceID, destinationID;
    private long distance;

    public Edge(String sourceID, String destinationID, long distance){
        this.sourceID = sourceID;
        this.destinationID = destinationID;
        this.distance = distance;
    }

    public Edge() {}

    public String getDestinationID(){
        return destinationID;
    }

    public String getSourceID(){
        return sourceID;
    }

    public double getDistance(){
        return distance;
    }

}
