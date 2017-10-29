package edu.csu2017fa314.T15.Model;


/**
 * Class to hold relevent information for edges between nodes
 * @version 1 - 9/19/2017 initial
 */
public class Edge {

    private Integer sourceID, destinationID;
    private long distance;

    public Edge(Integer sourceID, Integer destinationID, long distance){
        this.sourceID = sourceID;
        this.destinationID = destinationID;
        this.distance = distance;
    }

    public Edge() {}

    public Integer getDestinationID(){
        return destinationID;
    }

    public Integer getSourceID(){
        return sourceID;
    }

    public long getDistance(){
        return distance;
    }

}
