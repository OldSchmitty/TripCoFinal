package edu.csu2017fa314.T15.View;

import edu.csu2017fa314.T15.Model.CalculateDistance;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Scanner;

public class DrawMap {

  private final String path; // Dir to make file
  private ArrayList<String> elements; // What to write
  private int edgeLoc = 0; //What element[i] the edges are at
  private final double offSetX = (1024.0)/(-360);
  private final double offSetY = (512.0)/(180);

  /**
   * <p>Initializes elements to draw SVG</p>
   * @param path - where to build file
   */
  public DrawMap(final String path){
    this.path = path;
    this.elements = new ArrayList<>();
    addFromFile();
  }

  /**
   * Adds a edge on the route on the map
   * @param latStart  Latitude of start destination
   * @param longStart  Longitude of start destination
   * @param latEnd  Latitude of start destination
   * @param longEnd  Longitude of start destination
   */
  public void addEdge(final String latStart, final String longStart, final String latEnd, final String longEnd){
    // SVG Code template from https://svg-edit.github.io/svgedit
    if(edgeLoc == 0) // add start info for path
    {
      edgeLoc = elements.size();
      final String pathSetup = "  <title>RoutePath</title>\n  <!-- Draw The path -->\n";
      elements.add(edgeLoc, pathSetup);
    }

    String add = elements.get(edgeLoc);

    // converts all latitudes and longitudes to doubles
    final double x1 = CalculateDistance.stringToDoubleForCoordinate(longStart);
    final double y1 = CalculateDistance.stringToDoubleForCoordinate(latStart);
    final double x2 = CalculateDistance.stringToDoubleForCoordinate(longEnd);
    final double y2 = CalculateDistance.stringToDoubleForCoordinate(latEnd);

    if (sameHemisphere(x1,x2)) { // same hemisphere, line does not cross
      add += edgeString(Double.toString(x1), Double.toString(y1),
              Double.toString(x2), Double.toString(y2));
    } else {
      if (crossesBoundary(x1, y1, x2, y2)){ // line crosses, draw 2 lines
        // draw two lines using slope
        if ((x1 == -180 && x2 == 180) || ( x1 == 180 && x2 == -180)){
          // verticle line
          add += edgeString(Double.toString(x1), Double.toString(y1),
                  Double.toString(-1*x2), Double.toString(y2));
        } else {
          double slope = crossSlope(x1, y1, x2, y2);
          // calculate the cross point for east = -west
          double crossY = 0; // Y coordinate where line crosses east border
          if (x1 > x2) { // x1 east, x2 west
            crossY = slope * (180 - x1) + y1;
            // draw line from destination 1 to (180, crossY)
            add += edgeString(Double.toString(x1), Double.toString(y1),
                    Double.toString(180), Double.toString(crossY));
            // draw line from destination 2 to (-180, crossY)
            add += edgeString(Double.toString(x2), Double.toString(y2),
                    Double.toString(-180), Double.toString(crossY));
          } else { // x1 west, x2 east
            crossY = slope * (180 - x2) + y2;
            // draw line from desination 2 to (180, crossY)
            add += edgeString(Double.toString(x2), Double.toString(y2),
                    Double.toString(180), Double.toString(crossY));
            // draw line from destination 1 to (-180, crossY)
            add += edgeString(Double.toString(x1), Double.toString(y1),
                    Double.toString(-180), Double.toString(crossY));
          }
        }
      } else { // line does not cross
        add += edgeString(Double.toString(x1), Double.toString(y1),
                Double.toString(x2), Double.toString(y2));
        //add += edgeString("0", "90", "0", "-90");
      }

    }
    elements.set(edgeLoc,add);
  }

  /**
   * Returns a string which is used to draw a line on the map between two destinations.
   * @param x1  longitude of destination 1
   * @param y1  latitude of destination 1
   * @param x2  longitude of destination 2
   * @param y2  latitude of destination 2
   * @return    string to draw line
   */
  private String edgeString(final String x1, final String y1, final String x2, final String y2){
    final String output = "\n  <line x1=\"" +
        convertLongToX(x1) +
        "\" y1=\"" +
        convertLatToY(y1) +
        "\" x2=\"" +
        convertLongToX(x2) +
        "\" y2=\"" +
        convertLatToY(y2) +
        "\" stroke-width=\"3\" stroke=\"#ff69b4\"/>";

    return output;
  }

  /**
   * calculates the slope of the line that will cross the boundary
   * @param x1 longitude of destination 1
   * @param y1 latitude of destination 1
   * @param x2 longitude of destination 2
   * @param y2 latitude of destination 2
   * @return   the slope of the line that will be drawn
   */
  private double crossSlope(final double x1, final double y1, final double x2, final double y2){
    double slope = 0; // slope will remain 0 if y's are the same
    final double rise = latChange(y1, y2);      // always positive
    final double run = crossLongChange(x1, x2); // always positive

    if ( x1 < 0 ) { // destination 1 is in western hemisphere
      if (y1 > y2) { // slope positive
        slope = rise / run;
      } else if (y2 > y1) { // slope negative
        slope = -1 * rise / run;
      }
    } else { // destination 2 is in western hemisphere
      if ( y1 > y2) { // slope negative
        slope = -1 * rise / run;
      } else if (y2 > y1) { // slope positive
        slope = rise / run;
      }
    }

    return slope;
  }

  /**
   * calculates the change in longitude across E/W border.
   * this method is only called if the edge will be drawn across the border
   * one longitude will be positive, the other negative
   * @param x1 longitude of the first destination
   * @param x2 longitude of the second destination
   * @return  the change in longitute across the border
   */
  private double crossLongChange(final double x1, final double x2) {
    final double run;

    if (x1 < 0){ // x1 west, x2 east
      run = Math.abs(-180 - x1) + (180 - x2);
    } else {  // x1 east, x2 west
      run = Math.abs(-180 - x2) + (180 - x1);
    }

    return run;
  }

  /**
   * calculates the change in latitude or longitude between two destinations.
   * change in y.
   * @param y1    latitude of the first destination
   * @param y2    latitude of the second destination
   * @return    returns the change in latitude as a double
   */
  private double latChange(final double y1, final double y2){
    final double rise;
    if (sameHemisphere(y1, y2)){  // subtract smaller magnitude from larger
      if (y1 > y2){
        rise = Math.abs(y1) - Math.abs(y2);
      } else {
        rise = Math.abs(y2) - Math.abs(y1);
      }
    } else {                                // sum the two magnitudes
      rise = Math.abs(y1) + Math.abs(y2);
    }
    return rise;
  }

  /**
   * determines if the shortest path between the two points crosses the
   * east/west boundary of the map
   * @param x1 latitude of start destination
   * @param y1 longitude of start destination
   * @param x2 latitude of start destination
   * @param y2 longitude of start destination
   * @return  true if shorter path crosses E/W border, false otherwise
   */
  private boolean crossesBoundary(double x1, double y1, double x2, double y2){

    final double runC;  // change in longitude that crosses east/west border
    final double runNoC; // change in longitude w/o crossing east/west border

    if (x1 >x2){  // x1 east, x2 west
      runC = (180-x1) + Math.abs(-180-x2);
      runNoC = Math.abs(x1) + Math.abs(x2);
    } else {      // x1 west, x2 east
      runC = (180-x2) + Math.abs(-180-x1);
      runNoC = Math.abs(x2) + Math.abs(x1);
    }

    if (runNoC > runC){  // crossing border is shorter
      return true;
    }

    // crossing border is longer
    return false;
  }

  /**
   * Determines if two destinations are in the same hemisphere (N/S or E/W)
   * @param start   long or lat of destination 1
   * @param end     long or lat of destination 2
   * @return    true if destinations are in same hemisphere, false otherwise
   */
  private boolean sameHemisphere(final double start, final double end){

    if (start > 0 && end < 0){  // start east, end west
      return false;
    } else if (start < 0 && end > 0){  // start west, end east
      return false;
    } else if (start == 0 || end == 0){
      return false;
    }

    // start and end in same hemisphere (east,west) or both on Prime Meridian
    return true;
  }

  /**
   * Converts a latitude to a Y coordinate
   * @param lat Latitude to convert
   * @return The Y coordinate
   */
  private int convertLatToY(final String lat){
    return (int)Math.round((90 - CalculateDistance.stringToDoubleForCoordinate(lat)) * offSetY);
  }

  /**
   * Converts a longitude to a X coordinate
   * @param lon Longitude to convert
   * @return The Y coordinate
   */
  private int convertLongToX(final String lon){
    return (int)Math.round((-180 - CalculateDistance.stringToDoubleForCoordinate(lon)) *offSetX) ;
  }

  public String mapString(){
    String rt = elements.get(0);
    for (int i = 1; i < elements.size(); i++){
      rt = rt.concat( " <g>\n"+elements.get(i) + "\n </g>\n");
    }

    rt = rt.concat("\n</svg>");
    return rt;
  }

  private void addFromFile(){
    final InputStream baseFile = getClass().getClassLoader().
        getResourceAsStream("images/world.svg");

    String baseMap= "";
    String line;

    try {

      BufferedReader reader = new BufferedReader(new InputStreamReader(
              baseFile, "UTF-8"));

      line = reader.readLine();
      baseMap= baseMap.concat(line +"\n");
      baseMap = baseMap.concat("<!-- Following code provided by CS314 Instructors -->\n");

      line = reader.readLine();
      while(!line.contains("</svg>")) //stop at last line
      {
        baseMap = baseMap.concat(line + "\n");
        line = reader.readLine();

      }

      baseMap = baseMap.concat(line.replaceAll("</svg>", "") + "\n");
      baseMap = baseMap.concat("<!-- T15 Code -->\n");


      reader.close();
    } catch (java.io.IOException e) {
      throw new RuntimeException("Error opening file " + baseFile + ".", e);
    }
    elements.add(baseMap);
  }

  /**
   * Writes the data to the svg file
   */
  public void write(){

    BufferedWriter writer;
    final Charset charset = Charset.forName("US-ASCII");

    try
    {
      writer = Files.newBufferedWriter( Paths.get(path),
          charset);

      writer.write(mapString());
      writer.close();

    } catch (java.io.IOException e ) {
      throw new RuntimeException("Error in DrawMap.write() opening file", e);
    }
  }

}
