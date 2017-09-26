package edu.csu2017fa314.T15.View;

import edu.csu2017fa314.T15.Model.Destination;
import edu.csu2017fa314.T15.Model.Edge;
import java.util.ArrayList;
import java.util.HashMap;

public class View
{
  private String path;                    // Dir to make the files

  /**
   * <p>Set path dir to build the destination and edges JSONs and map SVG.</p>
   *
   * @param path Dir where files will be made
   */
  public View (final String path){
    this.path = path;
  }

  public View(){
  }

  /**
   * <p>Runs the logic to make Itinerary and Destination JSON and draw the map for sprint 2</p>
   */
  private void run(){

  }

  /**
   * <p>Draws the map using the Destinations map and Edges list</p>
   */
  private void drawMap(){

  }

  /**
   * <p>Makes the Itinerary JSON file using Edge list provided</p>
   * @param route - A list in order of the route
   */
  public void makeItinerary(ArrayList<Edge> route){
    if(path == null)
      throw new RuntimeException("View path not set");

    JSONWriter jw = new JSONWriter(path + "Itinerary.json");
    for (Edge e: route) {
      jw.add(e.getSourceID(),e.getDestinationID(),e.getDistance());
    }
    jw.write();
    jw.close();
  }

  /**
   * <p>Makes the Destination JSON file with the Destination map</p>
   * @param des All the destinations in the route
   */
  public void makeDestination(HashMap<String, Destination> des){
    if(path == null)
      throw new RuntimeException("View path not set");

    JSONWriter jw = new JSONWriter(path + "Destinations.json");
    for (String key: des.keySet()) {
      jw.add(des.get(key).getMap());
    }
    jw.write();
    jw.close();

  }

  private int totalDistance;

  public void setTotalDistance(int distance)
  {
     totalDistance = distance;
  }

  public int getTotalDistance()
  {
    return totalDistance;
  }

}
