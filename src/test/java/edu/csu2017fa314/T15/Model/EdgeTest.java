package edu.csu2017fa314.T15.Model;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EdgeTest {


    private Edge e;
    @Before
    public void setup() throws Exception {
        e = new Edge();
    }

    /**
     * Tests if Edge was imported correctly
     * @throws Exception - A problem
     */

    @Test
    public void testsImport() throws Exception {
        e = new Edge();
    }

    /**
     * Tests getters
     * JUnit tests
     */
    @Test
    public void testsgetDestinationID(){
        e = new Edge ("1111", "2222", 1000);
        assertEquals(e.getDestinationID(), "2222");
    }

    @Test
    public void testsgetSourceID(){
        e = new Edge ("1111", "2222", 1000);
        assertEquals(e.getSourceID(), "1111");
    }

    @Test
    public void testsgetDistance(){
        e = new Edge ("1111", "2222", 1000);
        assertEquals(e.getDistance(), 1000);
    }
}
