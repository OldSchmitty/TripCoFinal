package edu.csu2017fa314.T15.Model;

import java.util.ArrayList;

public class Model
{
   private String[] inputdata;
   private  ArrayList<Brewery> breweries;
   
   /* fields will allow the order and position of the relevant field columns to be determined dynamically, and will be used to extract the necassary data, 
    i.e. fields[0] will represent ID, and might be in position 2, thus fields[0] = 2; */  
   private int[] fields;
   
   public enum Data {
	    ID, NAME, CITY, LATITUDE, LONGITUDE, ELEVATION
	}

   public Model () 
   {
     
   }

   public void readFile(String filename){

   }

   private void storeInfo(){
	   
   }
   
   public Brewery getBrewery(String name){
	return null;
   }
}

