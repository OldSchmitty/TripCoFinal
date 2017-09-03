package edu.csu2017fa314.T15.Model;

/**
 * Class that holds all the info on a brewery
 * @version 1 - 9/2/17 initial
 */
 
public class Brewery { 

	private String id, brewery, city, latitude, longitude, elevation;

   Brewery(String id, String brewery, String city, String latitude, String longitude, String elevation){
    
       this.id = id;
       this.brewery = brewery;
       this.city = city;
       this.latitude = latitude;
       this.longitude = longitude;
       this.elevation = elevation;

	/*
      * TODO LATITIUDE- 40&deg; 35' 6.92" N, LONGITUDE - 105&deg; 5' 3.9084" W, ELVEVATION - 5007,
      * TODO destination -"", distance - 0;
      */

   }
   
   public String getid(){
        return id;   
    }

    public String getbrewery(){
        return brewery;   
    }

    public String getcity(){
        return city;   
    }

    public String getlatitude(){
        return latitude;   
    }

    public String getlongitude(){
        return longitude;   
    }

    public String getelevation(){
        return elevation;   
    }
	
	public String toString(){
		return "ID: " + id + '\n'
			+ "Brewery: " + brewery + '\n'
			+ "City: " + city + '\n'
			+ "Latitude: " + latitude + '\n'
			+ "Longitude: " + longitude + '\n'
			+ "Elevation: " + elevation;
	}
}