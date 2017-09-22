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
   */
  public void makeItinerary(ArrayList<Edge> route){

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
