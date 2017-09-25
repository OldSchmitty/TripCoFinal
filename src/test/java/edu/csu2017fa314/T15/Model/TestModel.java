package edu.csu2017fa314.T15.Model;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.io.File;


public class TestModel
{
    private Model m;

    @Before
    public void setUp() throws Exception
    {
        m = new Model();
    }
	
    @Test
    public void testReadAndStore() throws FileNotFoundException {
    	
    	/* test file has 3 brewery entries, with 2 extra columns to test if model ignors irrelevant columns, gets the    relevant columns and the information from the rows is correct, and that it correctly only gets 3 entries, and getNextBrewery() returns null when no more breweries exist
    	 */
    	
    	m.readFile("."+File.separator+"data"+File.separator+"testmodel.csv");
    	assertEquals(m.getSize(), 3);

    	String test0 = "ID: abee" + '\n'
				+ "Name: Two22 Brew" + '\n'
				+ "City: Centennial" + '\n'
				+ "Latitude: 39°38'07\" N" + '\n'
				+ "Longitude: 104°45'32\" W" + '\n'
				+ "Elevation: 5872";
    	String test1 = "ID: acwatson" + '\n'
				+ "Name: Equinox Brewing" + '\n'
				+ "City: Fort Collins" + '\n'
				+ "Latitude: 40°35'17\" N" + '\n'
				+ "Longitude: 105°4'26\" W" + '\n'
				+ "Elevation: 4988";
    	assertEquals(m.getNextBrewery().toString(), test0);
    	m.getNextBrewery();
    	assertEquals(m.getNextBrewery().toString(), test1);
    	assertEquals(m.getNextBrewery(), null);
    }

    @Test
	public void testReadIntoMap(){
		m = new Model("."+File.separator+"data"+File.separator+"testmodel.csv");
		Destination test =m.getDestination(m.getFirstItem());
		assertEquals(test.getId(), "abee");
		assertEquals("39°38'07\"N", test.getLatitude());
		assertEquals("104°45'32\"W", test.getLongitude());
		assertEquals(68, CalculateDistance.findDistanceBetween(m.getDestination("abee"),m.getDestination("acwatson")));
    }
}
