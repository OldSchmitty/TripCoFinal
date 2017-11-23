package edu.csu2017fa314.T15.View;

import org.junit.Before;
import org.junit.Test;

import edu.csu2017fa314.T15.Model.Destination;

public class ServerTest {
  private Server s;
  String[] test = {"Ranch"};
  Destination testDest = new Destination();
  Destination[] testMap = {testDest};

  @Before
  public void initialize(){
    s = new Server();
  }


  @Test
  public void testServerResponse(){
    ServerResponse sResp = new ServerResponse(testMap);
    sResp.toString();
  }

  @Test
  public void testStart(){
    s.serve();
  }

}
