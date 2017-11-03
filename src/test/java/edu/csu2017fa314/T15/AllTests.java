package edu.csu2017fa314.T15;
import edu.csu2017fa314.T15.Model.*;
import edu.csu2017fa314.T15.View.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import junit.framework.JUnit4TestAdapter;

// This section declares all of the test classes in your program.
@RunWith(Suite.class)
@Suite.SuiteClasses({
   TestTripCo.class,  // Add test classes here.
   TestModel.class,
   TestView.class, 
   DestinationTest.class,
   CalculateDistanceTest.class,
   JSONWriterTest.class,
    DrawMapTest.class,
    TableTest.class,
    SearchSQLDatabaseTest.class,
    ItineraryTest.class,
    EdgeTest.class,
    ServerTest.class,
    TwoOptTest.class,
    NearestNeighborTest.class
})

public class AllTests
{
	//This can be empty if you are using an IDE that includes support for JUnit
	//such as Eclipse.  However, if you are using Java on the command line or
	//with a simpler IDE like JGrasp or jCreator, the following main() and suite()
	//might be helpful.

    // Execution begins at main().  In this test class, we will execute
    // a text test runner that will tell you if any of your tests fail.
    public static void main (String[] args)
    {
       junit.textui.TestRunner.run (suite());
    }

    // The suite() method is helpful when using JUnit 3 Test Runners or Ant.
    public static junit.framework.Test suite()
    {
       return new JUnit4TestAdapter(AllTests.class);
    }

}
