package edu.csu2017fa314.T15.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Model {

	private String[] inputData;
	private ArrayList<Destination> breweries;

	public  String[] keys;				//A string array of all available data points
    private String firstItem;
    private HashMap<String, Destination> map;

	public Model() {
	}

	public Model(String fileName){
	    map = new HashMap<String, Destination>();
		readIntoMap(fileName);
	}

	public void readIntoMap(String fileName) {       //new function to read in data
        String[] values;
        String nextLine;
        Destination current;


        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            keys = reader.readLine().replaceAll("\\s+","").toLowerCase().split(",");

            if((nextLine=reader.readLine()) != null){
                values = nextLine.split(",", -1);
                for (int i = 0; i < values.length; i++){
                	values[i]= values[i].trim();
				}
                current = new Destination(keys, values);
                firstItem = current.getId();
                map.put(current.getId(),current);
            }
            while ((nextLine=reader.readLine()) != null){
                values = nextLine.split(",", -1);
				for (int i = 0; i < values.length; i++){
					values[i]= values[i].trim();
				}
                current = new Destination(keys, values);
                map.put(current.getId(), current);
            }
            reader.close();
        }
        catch (java.io.IOException e){
            System.out.println(e.getMessage());
        }
    }

	public int getSize() {
		return breweries.size();
	}
	public HashMap<String, Destination> getMap(){ return map; }
	public Destination getDestination(String id){
	    return map.get(id);
    }
    public String getFirstItem(){ return firstItem; }
}

