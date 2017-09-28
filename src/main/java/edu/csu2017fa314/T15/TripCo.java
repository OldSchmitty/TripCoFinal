package edu.csu2017fa314.T15;

import edu.csu2017fa314.T15.Model.Edge;
import edu.csu2017fa314.T15.Model.Itinerary;
import edu.csu2017fa314.T15.Model.Model;
import edu.csu2017fa314.T15.Model.CalculateDistance;
import edu.csu2017fa314.T15.Model.Destination;
import edu.csu2017fa314.T15.View.JSONWriter;

import edu.csu2017fa314.T15.View.View;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
import org.omg.IOP.CodecPackage.InvalidTypeForEncoding;

public class TripCo
{

	private static Model m;
	private static CalculateDistance calc;
	private static JSONWriter jw;
	private static String path;
	private static String baseMap;

	/**
	 * Driver for project
	 * @param args <p>0 - CVG to read</p>
	 * <p>1 - Map file to make path on</p>
	 * <p>2 - path to build files in</p>
	 * @throws FileNotFoundException One of the files to open did not exist
	 */
   public static void main(String[] args) throws FileNotFoundException {

   	if(args.length < 2){
   		throw new RuntimeException("\nNot enough arguments\n Expected 2 or 3\n Received: " +args.length);
		}
		if(args.length > 2){
   		path = args[2];
   	}
		else {
			path = "";
		}
   	baseMap = args[1];

	   m = new Model(args[0]);

		 Itinerary i = new Itinerary(m.getMap());
		 ArrayList<Edge> edges = i.getShortestPath();
		 View v = new View(path, baseMap);
		 v.makeItinerary(edges);
		 v.makeDestination(m.getMap());
		 v.drawMap(m.getMap(), edges);
   }

}
