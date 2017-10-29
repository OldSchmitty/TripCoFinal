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

	private ArrayList<Destination> dests; //list of all the destinations
	public  String[] keys;				//A string array of all available data points
    private String firstItem;           //first destination in the list

	public Model() {
	}

	public Model(String fileName){
	    dests = new ArrayList<Destination>();
		readIntoMap(fileName);
	}

	public void readIntoMap(String fileName) {       //new function to read in data
        String[] values;
        String nextLine;
        Destination current;


        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            keys = reader.readLine().replaceAll("\\s+","").toLowerCase().split(",");
            int count = 0;
            if((nextLine=reader.readLine()) != null){
                values = nextLine.split(",", -1);
                for (int i = 0; i < values.length; i++){
                	values[i]= values[i].trim();
				}
                current = new Destination(keys, values);
                current.setIdentifier(count);
                count++;
                firstItem = current.getId();
                dests.add(current);
            }
            while ((nextLine=reader.readLine()) != null){
                values = nextLine.split(",", -1);
				for (int i = 0; i < values.length; i++){
					values[i]= values[i].trim();
				}
                current = new Destination(keys, values);
                current.setIdentifier(count);
                count++;
                dests.add(current);
            }
            reader.close();
        }
        catch (java.io.IOException e){
            System.out.println(e.getMessage());
        }
    }

	public int getSize() {
		return dests.size();
	}
	public ArrayList<Destination> getMap(){ return dests; }
	public Destination getDestination(Integer id){
	    return dests.get(id);
    }
    public String getFirstItem(){ return firstItem; }
}

