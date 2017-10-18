package edu.csu2017fa314.T15.View;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Arrays;

import edu.csu2017fa314.T15.Model.Destination;

public class ServerTest {
  private Server s;
  String[] test = {"Ranch"};
  HashMap<String, Destination> testMap;

  @Before
  public void initialize(){
    s = new Server();
    testMap = new HashMap<String, Destination>();
  }

  @Test
  public void testStart(){
    s.serve();
  }

  @Test
  public void testServerRequest(){
      ServerRequest sRec = new ServerRequest(test, "query");
    }

  @Test
  public void testServerResponse(){
    ServerResponse sResp = new ServerResponse(testMap);
  }

}
