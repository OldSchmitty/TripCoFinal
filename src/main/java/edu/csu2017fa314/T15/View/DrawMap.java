package edu.csu2017fa314.T15.View;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DrawMap {

  private String path; // Dir to make file
  private ArrayList<String> elements; // What to write


  /**
   * <p>Initializes elements to draw SVG</p>
   * @param path - where to build file
   */
  public DrawMap(final String path){
    this.path = path;
    this.elements = new ArrayList<>();
    svgHeader();
  }

  private void svgHeader(){
    String head = new String("<?xml version=\"1.0\"?>\n"
        + "<svg width=\"1066.6073\" height=\"783.0824\" xmlns=\"http://www.w3.org/2000/svg\">\n");
    elements.add(head);
  }
  /**
   * Draws the boundaries of Colorado
   */
  public void drawColorado(){

  }

  /**
   * Adds a destination on the route on the map
   * @param lat - Latitude of destination
   * @param lon - Longitude of destination
   */
  public void addPoint(final String lat, final String lon){

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
      writer.write(elements.get(0));
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
