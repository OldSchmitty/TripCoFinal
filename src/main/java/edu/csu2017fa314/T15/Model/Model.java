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

	/* fields will allow the order and position of the relevant field columns to be determined dynamically, and will be used to extract the necassary data,
     i.e. fields[0] will represent ID, and might be in position 2, thus fields[0] = 2; */
	private int[] fields;
	// global fields size, changeable if we require more than the 6 fields
	private static final int numberFields = 6;
	private int nextBreweryIndex = 0;
	public  String[] keys;				//A string array of all available data points
    private String firstItem;
    private HashMap<String, Destination> map;

	public enum Data {
		ID, NAME, CITY, LATITUDE, LONGITUDE, ELEVATION
	}


	public Model() {

		breweries = new ArrayList<Destination>(0);
	}

	public Model(String fileName){
	    map = new HashMap<String, Destination>();
		readIntoMap(fileName);
	}


	public void readFile(String filename) throws FileNotFoundException {

		fields = new int[numberFields];
		Scanner read = new Scanner(new File(filename));

		// get the first line that details the columns and process it, searching for the positions of the relevant fields and storing them in fields array
		String firstLine = read.nextLine();
		inputData = firstLine.split(",");

		// extracting the location of the relevant fields, used toUpperCase so that all scans are global as contains cannot ignore case
		for (int i = 0; i < inputData.length; i++) {
			if (inputData[i].toUpperCase().contains("ID"))
				fields[Data.ID.ordinal()] = i;
			if (inputData[i].toUpperCase().contains("NAME"))
				fields[Data.NAME.ordinal()] = i;
			if (inputData[i].toUpperCase().contains("CITY"))
				fields[Data.CITY.ordinal()] = i;
			if (inputData[i].toUpperCase().contains("LATITUDE"))
				fields[Data.LATITUDE.ordinal()] = i;
			if (inputData[i].toUpperCase().contains("LONGITUDE"))
				fields[Data.LONGITUDE.ordinal()] = i;
			if (inputData[i].toUpperCase().contains("ELEVATION"))
				fields[Data.ELEVATION.ordinal()] = i;
		}


		// get each line of the file and store the information as a new Destination using storeInfo() function
		while (read.hasNextLine()) {
			inputData = read.nextLine().split(",");
			if (inputData.length != 1) {

				for (int i = 0; i < inputData.length; i++) {
					inputData[i] = inputData[i].trim();
				}
				storeInfo();
			}
		}
		read.close();
	}

	public void readIntoMap(String fileName) {       //new function to read in data
        String[] values;
        String nextLine;
        Destination current;


        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            keys = reader.readLine().replaceAll("\\s+","").toLowerCase().split(",");

            if((nextLine=reader.readLine()) != null){
                values = nextLine.split(",");
                current = new Destination(keys, values);
                firstItem = current.getId();
                map.put(current.getId(),current);
            }
            while ((nextLine=reader.readLine()) != null){
                values = nextLine.split(",");
                current = new Destination(keys, values);
                map.put(current.getId(), current);
            }
            reader.close();
        }
        catch (java.io.IOException e){
            System.out.println(e.getMessage());
        }
    }

	private void storeInfo() {
		Destination temp;

		// extract the required info from a line of data in the specific index corresponding to a particular field
		temp = new Destination(inputData[fields[Data.ID.ordinal()]], inputData[fields[Data.NAME.ordinal()]],
				inputData[fields[Data.CITY.ordinal()]], inputData[fields[Data.LATITUDE.ordinal()]],
				inputData[fields[Data.LONGITUDE.ordinal()]], inputData[fields[Data.ELEVATION.ordinal()]]);
		breweries.add(temp);
	}

	public Destination getNextBrewery() {
		if (nextBreweryIndex < breweries.size()) {
			Destination brew = breweries.get(nextBreweryIndex);
			nextBreweryIndex += 1;
			return brew;
		}
		return null;
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

