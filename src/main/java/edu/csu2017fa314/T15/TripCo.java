package edu.csu2017fa314.T15;

import edu.csu2017fa314.T15.Model.Edge;
import edu.csu2017fa314.T15.Model.Itinerary;
import edu.csu2017fa314.T15.Model.Model;
import edu.csu2017fa314.T15.Model.CalculateDistance;
import edu.csu2017fa314.T15.View.JSONWriter;
import edu.csu2017fa314.T15.View.Server;

import edu.csu2017fa314.T15.View.View;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TripCo
{
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
	   Server s = new Server();
	   s.serve();
   }

}
