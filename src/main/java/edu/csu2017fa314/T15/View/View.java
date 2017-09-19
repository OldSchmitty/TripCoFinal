package edu.csu2017fa314.T15.View;

import edu.csu2017fa314.T15.Model.Brewery;
import edu.csu2017fa314.T15.Model.Edge;
import java.util.ArrayList;
import java.util.HashMap;

public class View
{
  private HashMap<String, Brewery> dest;  // All the destinations
  private ArrayList<Edge> edge;           // The current route
  private String path;                    // Dir to make the files

  /**
   * <p>Constructor to build the destination and edges JSONs and map SVG automatically.</p>
   * @param dest Map of all the destinations on the trip
   * @param edge List of the path to take
   * @param path Dir where files will be made
   */
  public View (final HashMap<String, Brewery> dest, final ArrayList<Edge> edge, final String path){
    this.dest = dest;
    this.edge = edge;
    this.path = path;
    run();
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
   */
  private void makeItinerary(){

  }

  /**
   * <p>Makes the Destination JSON file with the Destination map</p>
   */
  private void makeDestination(){

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
