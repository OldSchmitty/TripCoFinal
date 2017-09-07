package edu.csu2017fa314.T15.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Model{
	
   private String[] inputData;   
   private ArrayList<Brewery> breweries;
   
   /* fields will allow the order and position of the relevant field columns to be determined dynamically, and will be used to extract the necassary data, 
    i.e. fields[0] will represent ID, and might be in position 2, thus fields[0] = 2; */  
   private int[] fields;
   // global fields size, changeable if we require more than the 6 fields
   private static final int numberFields = 6;
   private int nextBreweryIndex = 0;
   
   public enum Data {
	    ID, NAME, CITY, LATITUDE, LONGITUDE, ELEVATION
	}


   public Model()
   {
	   breweries = new ArrayList<Brewery>(0);
   }

   public void readFile(String filename) throws FileNotFoundException{
	   
	   fields = new int[numberFields];
	   Scanner read = new Scanner(new File(filename));

	   // get the first line that details the columns and process it, searching for the positions of the relevant fields and storing them in fields array
	   String firstLine = read.nextLine();
	   inputData = firstLine.split(",");
	   
	   // extracting the location of the relevant fields, used toUpperCase so that all scans are global as contains cannot ignore case
	   for(int i = 0; i < inputData.length; i++) {
		   if(inputData[i].toUpperCase().contains("ID"))
			   fields[Data.ID.ordinal()] = i;
		   if(inputData[i].toUpperCase().contains("NAME"))
			   fields[Data.NAME.ordinal()] = i;
		   if(inputData[i].toUpperCase().contains("CITY"))
			   fields[Data.CITY.ordinal()] = i;
		   if(inputData[i].toUpperCase().contains("LATITUDE"))
			   fields[Data.LATITUDE.ordinal()] = i;
		   if(inputData[i].toUpperCase().contains("LONGITUDE"))
			   fields[Data.LONGITUDE.ordinal()] = i;
		   if(inputData[i].toUpperCase().contains("ELEVATION"))
			   fields[Data.ELEVATION.ordinal()] = i;
	   }
	   
	   
	   // get each line of the file and store the information as a new Brewery using storeInfo() function
	   while(read.hasNextLine()) {
		   inputData = read.nextLine().split(",");
		   if(inputData.length != 1) {

				 for (int i = 0; i < inputData.length; i++) {
					 inputData[i] = inputData[i].trim();
				 }
				 storeInfo();
			 }
	   }
	   read.close();	   
   }

private void storeInfo(){
	   Brewery temp;
	   
	   // extract the required info from a line of data in the specific index corresponding to a particular field
	   temp = new Brewery(inputData[fields[Data.ID.ordinal()]], inputData[fields[Data.NAME.ordinal()]], 
			inputData[fields[Data.CITY.ordinal()]], inputData[fields[Data.LATITUDE.ordinal()]], 
			inputData[fields[Data.LONGITUDE.ordinal()]], inputData[fields[Data.ELEVATION.ordinal()]]);
	   breweries.add(temp);
   }
   
   public Brewery getNextBrewery(){
	   if(nextBreweryIndex < breweries.size()) {
		   Brewery brew = breweries.get(nextBreweryIndex);
		   nextBreweryIndex += 1;
		   return brew;
	   }   
	   return null;
   }
   
   public int getSize() {
	   return breweries.size();
   }
}

