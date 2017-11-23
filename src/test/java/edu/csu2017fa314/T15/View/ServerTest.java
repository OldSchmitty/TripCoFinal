package edu.csu2017fa314.T15.View;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import edu.csu2017fa314.T15.Model.Destination;

import static org.junit.Assert.assertEquals;

public class ServerTest {
  private Server s;
  String[] test = {"Ranch"};
  Destination destination = new Destination();
  Destination[] testMap = {destination};

  @Before
  public void initialize(){
    s = new Server();
  }

  @Test
  public void testServerRequestQuery() throws SQLException{
    ServerRequest serverRequest = new ServerRequest(test, "query", "Miles", "2-Opt");

    assertEquals(serverRequest.getUnits(), "Miles");
    assertEquals(serverRequest.getOpt(), "2-Opt");
    assertEquals(serverRequest.getdoWhat(), "query");
    serverRequest.toString();
    }

  @Test
  public void testServerRequestPlan() throws SQLException{
    ServerRequest serverRequest = new ServerRequest(test, "plan", "Kilometers", "3-Opt");

    assertEquals(serverRequest.getUnits(), "Kilometers");
    assertEquals(serverRequest.getOpt(), "3-Opt");
    assertEquals(serverRequest.getdoWhat(), "plan");
    serverRequest.toString();
  }

  @Test
  public void testServerResponse(){
    ServerResponse sResp = new ServerResponse(testMap);
    sResp.toString();
  }

}
