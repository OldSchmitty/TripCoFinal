package edu.csu2017fa314.T15.Model;
import java.util.HashMap;
import java.util.Set;
/**
 * Class that holds all the info on a destination
 * @version 1 - 9/2/17 initial
 */
 
public class Destination {

    private HashMap<String, String> map;
    private int tableIdentifier;

    Destination(String id, String name, String city, String latitude, String longitude, String elevation){
       map = new HashMap<String, String>();
       map.put("code", id);
       map.put("name", name);
       map.put("city", city);
       map.put("latitude", latitude);
       map.put("longitude", longitude);
       map.put("elevation", elevation);
    }
    public Destination(){
       map = new HashMap<String,String>();
    }

    public Destination(String[] keys, String[] values){
        map = new HashMap<String,String>();
        for (int i =0; i<keys.length; i++ )
        {
            map.put(keys[i], values[i]);
        }
    }

    public void setValue(String key, String value){ map.put(key,value); }

    public String get(String key){ return map.get(key); }

    public String getId(){ return map.get("code"); }

    public String getName(){ return map.get("name"); }

    public String getCity(){ return map.get("city"); }

    public String getLatitude(){ return map.get("latitude"); }

    public String getLongitude(){ return map.get("longitude"); }

    public String getElevation(){ return map.get("elevation"); }

    public HashMap<String, String> getMap(){ return map; }

    public Set<String> getKeys(){ return map.keySet(); }

	
    public String toString(){
        return "ID: " + getId() + '\n'
            + "Name: " + getName() + '\n'
            + "City: " + getCity() + '\n'
            + "Latitude: " + getLatitude() + '\n'
            + "Longitude: " + getLongitude() + '\n'
            + "Elevation: " + getElevation();
    }

    public void setIdentifier(int id) {
        tableIdentifier = id;
    }

    public int getIdentifier(){
        return tableIdentifier;
    }
}
