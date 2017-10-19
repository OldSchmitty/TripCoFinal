package edu.csu2017fa314.T15.View;

import edu.csu2017fa314.T15.Model.CalculateDistance;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class DrawMap {

  private String path; // Dir to make file
  private ArrayList<String> elements; // What to write
  private int edgeLoc = 0; //What element[i] the edges are at
  private double xOffSet = (991.4014 - 37.52397)/(-109 +102);
  private double yOffSet = (708.0824 - 37.4016)/(41 -37);
  private InputStream baseFile = DrawMap.class.getResourceAsStream("colorado.svg");
  private BufferedWriter writer;

  /**
   * <p>Initializes elements to draw SVG</p>
   * @param path - where to build file
   */
  public DrawMap(final String path, String baseFile){
    this.path = path;
    this.elements = new ArrayList<>();
    svgHeader();
    addFromFile();
  }

  public DrawMap(){

  }

  /**
   * Returns the map base path
   * @return The current file to used as a base map
   */
  public final InputStream getBaseFile(){
    return baseFile;
  }
  /**
   * Sets the SVG header
   */
  private void svgHeader(){
    //SVG Code template from https://svg-edit.github.io/svgedit
    String header ="<?xml version=\"1.0\"?>\n<svg width=\"1066.6073\" "
        + "height=\"783.0824\" xmlns=\"http://www.w3.org/2000/svg\">\n";
    elements.add(header);
  }
  /**
   * Draws the boundaries of Colorado
   */
  public void drawColorado(){
    String mapElem = "  <title>ColoradoBorders</title>\n  <!-- Draw Colorado -->\n";
    //SVG Code template from https://svg-edit.github.io/svgedit
    mapElem += "  <rect id=\"Colorado\" height=\"708.68515\" width=\"991.4014\" y=\"37.4016\" "
        + "x=\"37.52397\" stroke-linecap=\"null\" stroke-linejoin=\"null\" "
        + "stroke-dasharray=\"null\" stroke-width=\"5\" stroke=\"#000000\" fill=\"none\"/>";
    elements.add(mapElem);
  }

  /**
   * Adds a edge on the route on the map
   * @param latStart  Latitude of start destination
   * @param longStart  Longitude of start destination
   * @param latEnd  Latitude of start destination
   * @param longEnd  Longitude of start destination
   */
  public void addEdge(final String latStart, final String longStart,
      final String latEnd, final String longEnd){
    // SVG Code template from https://svg-edit.github.io/svgedit
    if(edgeLoc == 0) // add start info for path
    {
      edgeLoc = elements.size();
      String pathSetup = "  <title>RoutePath</title>\n  <!-- Draw The path -->\n";
      elements.add(edgeLoc, pathSetup);
    }

    String add = elements.get(edgeLoc);
    add += "\n  <line x1=\"" +convertLongToX(longStart) + "\" y1=\"" + convertLatToY(latStart) +
        "\" x2=\""+ convertLongToX(longEnd) +"\" y2=\""+ convertLatToY(latEnd) +
        "\" stroke-width=\"3\" stroke=\"#00007f\"/>";
    elements.set(edgeLoc,add);
  }

  /**
   * Converts a latitude to a Y coordinate
   * @param lat Latitude to convert
   * @return The Y coordinate
   */
  private int convertLatToY(final String lat){
    return (int)Math.round((41 - CalculateDistance.stringToDoubleForCoordinate(lat)) * yOffSet) +37;
  }

  /**
   * Converts a longitude to a X coordinate
   * @param lon Longitude to convert
   * @return The Y coordinate
   */
  private int convertLongToX(final String lon){
    return (int)Math.round((-109 - CalculateDistance.stringToDoubleForCoordinate(lon)) * xOffSet) +37;
  }

  /**
   * Adds a destination on the route and its name to the map
   * @param lat - Latitude of destination
   * @param lon - Longitude of destination
   * @param name - Name of point
   */
  public void addPoint(final String lat, final String lon, final String name){

  }

  public String mapString(){
    String rt = "";
    rt = rt.concat(elements.get(0) + elements.get(1));
    for (int i = 2; i < elements.size(); i++){
      rt = rt.concat( " <g>\n"+elements.get(i) + "\n </g>\n");
    }

    rt = rt.concat("\n</svg>");
    return rt;
  }

  public void addFromFile() {
    String baseMap= "";
    String line;
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(
          baseFile, "UTF-8"));
      for (int i = 0; i < 9; i++) {
        reader.readLine(); // Skip Header info
      }
      baseMap = baseMap.concat("<!-- Following code provided by CS314 Instructors -->\n");
      baseMap = baseMap.concat("<!-- Created with Inkscape (http://www.inkscape.org/) -->\n");
      line=reader.readLine();
      while(!line.equalsIgnoreCase("</svg>")) //stop at last line
      {
        baseMap = baseMap.concat(line + "\n");
        line=reader.readLine();

      }
      baseMap = baseMap.concat("<!-- T15 code -->\n");
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

    Charset charset = Charset.forName("US-ASCII");

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

  /**
   * Closes the SVG file
   */
  public void close(){

  }
}
