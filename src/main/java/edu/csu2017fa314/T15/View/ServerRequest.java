package edu.csu2017fa314.T15.View;

import java.sql.SQLException;
import java.util.Arrays;

import edu.csu2017fa314.T15.Model.Destination;
import edu.csu2017fa314.T15.Model.SearchSQLDatabase;

/**
 * Created by sswensen on 10/1/17 modified by James DePoyster for use in TripCO.
 */

public class ServerRequest {
    private String[] queries;
    private String doWhat = "";
    private Destination[] dests;
    private String units = "";
    private String opt;
    static private String[] login = {"jdepoiii", "829840334"};

    public ServerRequest(String[] queries, String doWhat, String units, String opt){
        this.queries = queries;
        this.doWhat = doWhat;
        this.units = units;
        this.opt = opt;
    }

    public void searchDatabase() throws SQLException{
        SearchSQLDatabase sql = new SearchSQLDatabase(login);
        dests = sql.query(queries);
    }

    public Destination[] planTrip() throws SQLException{
        String[] columns = {"CODE"};
        SearchSQLDatabase sql = new SearchSQLDatabase(login);
        return sql.queryInOrder(queries, columns[0]);
    }

    public Destination[] getDests(){ return this.dests; }

    @Override
    public String toString() {
        String ret = "";
        for (String s: queries){
            ret += s + ", ";
        }
        return ret.substring(0, ret.length()-2);
    }

    public String getdoWhat(){ return this.doWhat; }
    public String getUnits(){ return this.units; }
    public String getOpt(){ return this.opt; }
}
