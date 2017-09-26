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
	private static String path = "." + File.separator + "data" + File.separator;
	
   public static void main(String[] args) throws FileNotFoundException {
	   m = new Model(args[0]);

		 Itinerary i = new Itinerary(m.getMap());
		 ArrayList<Edge> edges = i.getShortestPath();
		 View v = new View(path);
		 v.makeItinerary(edges);
		 v.makeDestination(m.getMap());
		 v.drawMap(m.getMap(), edges);
   }

}
