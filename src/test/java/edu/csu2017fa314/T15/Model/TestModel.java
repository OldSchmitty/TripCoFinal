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
	public void testReadIntoMap(){
		m = new Model("."+File.separator+"data"+File.separator+ "test_input" + File.separator + "testmodel.csv");
		Destination test =m.getDestination(0);
		assertEquals(test.getId(), "abee");
		assertEquals("39°38'07\" N", test.getLatitude());
		assertEquals("104°45'32\" W", test.getLongitude());
		assertEquals(68, CalculateDistance.findDistanceBetween(m.getDestination(0),m.getDestination(2)));
    }
}
