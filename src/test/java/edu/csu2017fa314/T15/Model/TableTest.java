package edu.csu2017fa314.T15.Model;

import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import static org.junit.Assert.*;

public class TableTest {

    private HashMap<String, Destination> map;
    private Table table;

    @Before
    public void setup() throws Exception{
        Destination b1 = new Destination("a", "Two22 Brew", "Centennial", "39°38'07\" N",
                "104°45'32\" W", "5872");
        Destination b2 = new Destination("b", "Equinox Brewing", "Fort Collins", "40°35'17\" N",
                "105°4'26\" W", "4988");
        Destination b3 = new Destination("c", "Mad Jacks Mountain Brewing", "Bailey", "39°24'05\" N",
                "105°28'37\" W", "9580");

        map = new HashMap<String, Destination>();
        map.put("a", b1);
        map.put("b", b2);
        map.put("c", b3);

        table = new Table(map);
    }

    /**
     * A few tests to assert that the Table constructor builds properly and the getEdge method works
     */
    @Test
    public void test1() { assertEquals(table.getDistance("a", "b"), 68);}

    @Test
    public void test2() { assertEquals(table.getDistance("b", "a"), 68);}
}
