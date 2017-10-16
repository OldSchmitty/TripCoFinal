package edu.csu2017fa314.T15.View;

import java.util.HashMap;
import edu.csu2017fa314.T15.Model.Destination;
import edu.csu2017fa314.T15.Model.SearchSQLDatabase;

/**
 * Created by sswensen on 10/1/17 modified by James DePoyster for use in TripCO.
 */

public class ServerRequest {
    private String[] queries;
    private String doWhat = "";
    private HashMap<String, Destination> dests;
    private String[] login = {"jdepoiii", "829840334"};


    public ServerRequest(String[] queries, String doWhat) {
        this.queries = queries;
        this.doWhat = doWhat;
        dests = new HashMap<>();
    }

    public void searchDatabase(){
        SearchSQLDatabase sql = new SearchSQLDatabase(login);
        dests = sql.query(queries);
    }

    public void planTrip(){
        String[] columns = {"ID"};
        SearchSQLDatabase sql = new SearchSQLDatabase(login);
        dests = sql.query(queries, columns);
    }

    public HashMap<String, Destination> getDests(){ return this.dests; }

    @Override
    public String toString() {
        return "Request{" +
                "queries='" + queries + '\'' +
                '}';
    }

    public String getdoWhat(){ return this.doWhat; }
}
