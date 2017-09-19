package edu.csu2017fa314.T15.View;

import edu.csu2017fa314.T15.Model.Brewery;
import edu.csu2017fa314.T15.Model.Edge;
import java.util.ArrayList;
import java.util.HashMap;

public class View
{
  private HashMap<String, Brewery> dest;
  private ArrayList<Edge> edge;

  public View (final HashMap<String, Brewery> dest, final ArrayList<Edge> edge, final String path){
    this.dest = dest;
    this.edge = edge;
    run();
  }

  public View(){
  }
  private void run(){

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
