package edu.csu2017fa314.T15.View;

public class ServerPlanTrip {
    private String itenPath;
    private String destPath;

    public ServerPlanTrip(String path){
        this.itenPath = path + "Itinerary.json";
        this.destPath = path + "Destinations.json";
    }
}