package edu.csu2017fa314.T15.Model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.HashMap;
import static org.junit.Assert.*;

public class TableTest {

    static private Destination[] map;
    private Table table;

    @BeforeClass
    static public void destinationsSetup()
    {
        Destination b1 = new Destination("a", "Two22 Brew", "Centennial", "39°38'07\" N",
            "104°45'32\" W", "5872");
        b1.setIdentifier(0);
        Destination b2 = new Destination("b", "Equinox Brewing", "Fort Collins", "40°35'17\" N",
            "105°4'26\" W", "4988");
        b2.setIdentifier(1);
        Destination b3 = new Destination("c", "Mad Jacks Mountain Brewing", "Bailey", "39°24'05\" N",
            "105°28'37\" W", "9580");
        b3.setIdentifier(2);

        map = new Destination[3];
        map[0] = b1;
        map[1] = b2;
        map[2] = b3;
    }

    /**
     * A few tests to assert that the Table constructor builds properly and the getEdge method works
     */
    @Test
    public void test1() {
        CalculateDistance.setMiles();
        table = new Table(map);
        assertEquals(table.getDistance(0, 1), 68);
    }

    @Test
    public void test2() {
        CalculateDistance.setKilometers();
        table = new Table(map);
        assertEquals(table.getDistance(1, 0), 109);
    }
}
