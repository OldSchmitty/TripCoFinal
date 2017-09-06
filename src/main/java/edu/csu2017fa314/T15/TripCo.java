package edu.csu2017fa314.T15;

public class TripCo
{

	private static Model m;
	private static CalculateDistance calc;
	//private static JSONWriter jw;
	
	
   public static void main(String[] args) {
	   m = new Model();
	   m.readFile(args[0]);
	   
	   Brewery first, second;
	   int distance;
	   
	   for(int i = 0; i < m.getSize(); i++) {
		   if(i == 0)
			   first = m.getNextBrewery();
		   else
			   first = second;
		   second = m.getNextBrewery();
		   distance = findDistanceBetween(first, second);
		   //jw.add(first.getId(), second.getId(), distance);
	   }
	   
	   //jw.write(); jw.close();
   }

}
