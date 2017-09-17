package edu.csu2017fa314.T15;

import edu.csu2017fa314.T15.Model.Model;
import edu.csu2017fa314.T15.Model.CalculateDistance;
import edu.csu2017fa314.T15.Model.Brewery;
import edu.csu2017fa314.T15.View.JSONWriter;

import java.io.FileNotFoundException;
import java.io.File;
public class TripCo
{

	private static Model m;
	private static CalculateDistance calc;
	private static JSONWriter jw;
	
	
   public static void main(String[] args) throws FileNotFoundException {
	   m = new Model();
	   jw = new JSONWriter("." + File.separator + "data" + File.separator + "BreweryItinerary.json");
	   m.readFile(args[0]);
	   
	   Brewery first = new Brewery();
	   Brewery second = new Brewery();
	   long distance;
	   
	   for(int i = 0; i < m.getSize()-1; i++) {
		   if(i == 0)
			   first = m.getNextBrewery();
		   else
			   first = second;
		   second = m.getNextBrewery();
		   distance = calc.findDistanceBetween(first, second);
		   jw.add(first.getId(), second.getId(), distance);
	   }
	   
	   jw.write(); jw.close();
   }

}
