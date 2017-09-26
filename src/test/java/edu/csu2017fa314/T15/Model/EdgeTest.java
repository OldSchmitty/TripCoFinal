package edu.csu2017fa314.T15.Model;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EdgeTest {


    private Edge e;
    @Before
    public void setup() throws Exception {
        e = new Edge ("1111", "2222", 1000);
    }

    /**
     * Tests getters
     * JUnit tests
     */
    @Test
    public void testsgetDestinationID(){
        assertEquals(e.getDestinationID(), "2222");
    }

    @Test
    public void testsgetSourceID(){
        assertEquals(e.getSourceID(), "1111");
    }

    @Test
    public void testsgetDistance(){
        assertEquals(e.getDistance(), 1000);
    }
}
