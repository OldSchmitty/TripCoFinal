package edu.csu2017fa314.T15.View;

import edu.csu2017fa314.T15.Model.CalculateDistance;
import java.io.BufferedWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class DrawMap {

  private String path; // Dir to make file
  private ArrayList<String> elements; // What to write
  private int edgeLoc = 0; //What element[i] the edges are at

  /**
   * <p>Initializes elements to draw SVG</p>
   * @param path - where to build file
   */
  public DrawMap(final String path){
    this.path = path;
    this.elements = new ArrayList<>();
    svgHeader();
  }

  /**
   * Sets the SVG header
   */
  private void svgHeader(){
    String header ="<?xml version=\"1.0\"?>\n<svg width=\"1066.6073\" "
        + "height=\"783.0824\" xmlns=\"http://www.w3.org/2000/svg\">\n";
    elements.add(header);
  }
  /**
   * Draws the boundaries of Colorado
   */
  public void drawColorado(){
    String mapElem = "  <title>ColoradoBorders</title>\n  <!-- Draw Colorado -->\n";
    //All SVG Code from https://svg-edit.github.io/svgedit
    mapElem += "  <rect id=\"Colorado\" height=\"708.68515\" width=\"991.4014\" y=\"37.4016\" "
        + "x=\"37.52397\" stroke-linecap=\"null\" stroke-linejoin=\"null\" "
        + "stroke-dasharray=\"null\" stroke-width=\"5\" stroke=\"#000000\" fill=\"none\"/>";
    elements.add(mapElem);
  }

  /**
   * Adds a destination on the route on the map
   * @param lat - Latitude of destination
   * @param lon - Longitude of destination
   */
  public void addPathPoint(final String lat, final String lon){
    //All SVG Code from https://svg-edit.github.io/svgedit
    if(edgeLoc == 0) // add start of path logic
    {
      edgeLoc = elements.size();
      String pathSetup = "  <title>RoutePath</title>\n  <!-- Draw The path -->\n";
      pathSetup += "  <path id=\"svg_3\" fill=\"none\" stroke=\"#00007f\" stroke-width=\"3\" "
          + "stroke-dasharray=\"null\" stroke-linejoin=\"null\" stroke-linecap=\"null\" d=\"";
      elements.add(edgeLoc, pathSetup);
    }
    String add = elements.get(edgeLoc);
    add += "l"+CalculateDistance.stringToDoubleForCoordinate(lat) + ","
        + CalculateDistance.stringToDoubleForCoordinate(lon);
    elements.set(edgeLoc,add);
  }

  private void finishPath(){
    if (edgeLoc !=0){
      elements.set(edgeLoc, elements.get(edgeLoc) +"\"/>");
    }
  }

  /**
   * Adds a destination on the route and its name to the map
   * @param lat - Latitude of destination
   * @param lon - Longitude of destination
   * @param name - Name of point
   */
  public void addPoint(final String lat, final String lon, final String name){

  }

  /**
   * Writes the data to the svg file
   */
  public void write(){

    Charset charset = Charset.forName("US-ASCII");

    try
    {
      BufferedWriter writer = Files.newBufferedWriter( Paths.get(path),
          charset);

      finishPath();

      writer.write(elements.get(0)); // Header file
      // Write all elements
      for (int i = 1; i < elements.size(); i++){
        writer.write( " <g>\n");
        writer.write(elements.get(i));
        writer.write("\n </g>\n");
      }

      writer.write("\n</svg>");
      writer.close();

    } catch (java.io.IOException e ) {
      throw new RuntimeException("Error in DrawMap.write() opening file", e);
    }
  }

  /**
   * Closes the SVG file
   */
  public void close(){

  }
}
