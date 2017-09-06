package edu.csu2017fa314.T15.Model;

/**
 * Class that holds all the info on a brewery
 * @version 1 - 9/2/17 initial
 */
 
public class Brewery { 

	private String id, name, city, latitude, longitude, elevation;

   Brewery(String id, String name, String city, String latitude, String longitude, String elevation){
    
       this.id = id;
       this.name = name;
       this.city = city;
       this.latitude = latitude;
       this.longitude = longitude;
       this.elevation = elevation;
   }
   
   public Brewery(){
   }
   
   public String getId(){
        return id;   
    }

    public String getName(){
        return name;   
    }

    public String getCity(){
        return city;   
    }

    public String getLatitude(){
        return latitude;   
    }

    public String getLongitude(){
        return longitude;   
    }

    public String getElevation(){
        return elevation;   
    }
	
	public String toString(){
		return "ID: " + id + '\n'
			+ "Name: " + name + '\n'
			+ "City: " + city + '\n'
			+ "Latitude: " + latitude + '\n'
			+ "Longitude: " + longitude + '\n'
			+ "Elevation: " + elevation;
	}
}
