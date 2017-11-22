package edu.csu2017fa314.T15.View;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import edu.csu2017fa314.T15.Model.Destination;

import static org.junit.Assert.assertEquals;

public class ServerTest {
  private Server s;
  String[] test = {"Ranch"};
  Destination[] testMap;

  @Before
  public void initialize(){
    s = new Server();
  }

  @Test
  public void testStart(){
    s.serve();
  }

  @Test
  public void testServerRequestQuery() throws SQLException{
    ServerRequest sRec = new ServerRequest(test, "query", "Miles", "2-Opt");

    assertEquals(sRec.getUnits(), "Miles");
    assertEquals(sRec.getOpt(), "2-Opt");
    assertEquals(sRec.getdoWhat(), "query");
    sRec.toString();

    Destination[] dests;
    sRec.searchDatabase();
    dests = sRec.getDests();
    assertEquals(dests.length, 10);
    }

  @Test
  public void testServerRequestPlan() throws SQLException{
    ServerRequest sRec = new ServerRequest(test, "plan", "Kilometers", "3-Opt");

    assertEquals(sRec.getUnits(), "Kilometers");
    assertEquals(sRec.getOpt(), "3-Opt");
    assertEquals(sRec.getdoWhat(), "plan");
    sRec.toString();

    Destination[] dests;
    dests = sRec.planTrip();
    assertEquals(dests.length, 10 );
  }

  @Test
  public void testServerResponse(){
    ServerResponse sResp = new ServerResponse(testMap);
    sResp.toString();
  }

}
