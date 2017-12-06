package edu.csu2017fa314.T15.View;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import spark.Request;
import spark.Response;
import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

import edu.csu2017fa314.T15.Model.Destination;
import edu.csu2017fa314.T15.Model.Edge;
import edu.csu2017fa314.T15.Model.Itinerary;
import edu.csu2017fa314.T15.Model.CalculateDistance;

import static edu.csu2017fa314.T15.Model.CalculateDistance.setKilometers;
import static edu.csu2017fa314.T15.Model.CalculateDistance.setMiles;
import static spark.Spark.post;

/**
 * Created by sswensen on 10/1/17 modified by James DePoyster for use in TripCO.
 */

public class Server {

    private String buildPath = System.getProperty("user.dir") + File.separator + "web" + File.separator + "images"
            +File.separator;

    public void serve() {
        Gson g = new Gson();
        post("/receive", this::receive, g::toJson); //Create new listener
    }

    private Object receive(Request rec, Response res) throws SQLException{
        //Set the return headers
        setHeaders(res);

        //Init json parser
        JsonParser parser = new JsonParser();

        //Grab the json body from POST
        JsonElement elm = parser.parse(rec.body());

        //Create new Gson (a Google library for creating a JSON representation of a java class)
        Gson gson = new Gson();

        //Create new Object from received JsonElement elm
        ServerRequest sRec = gson.fromJson(elm, ServerRequest.class);

        //The object generated by the frontend should match whatever class you are reading into.
        //Notice how DataClass has name and ID and how the frontend is generating an object with name and ID.

        if (sRec.getdoWhat().equals("query")) {
            System.out.println("Searching for: " + sRec.toString());
            sRec.searchDatabase();

            //Create object with svg file path and list to return to server
            ServerResponse sRes = new ServerResponse(sRec.getDests());

            //System.out.println("Sending \"" + sRes.toString() + "\" to server.");

            /* What to return to the server.
             * In this example, the "ServerResponse" object sRes is converted into a JSON representation
            * using the GSON library.  If you'd like to see what this JSON looks like, it is logged to the console in
            * the web client.
            */

            return gson.toJson(sRes, ServerResponse.class);


        }
        else if(sRec.getdoWhat().equals("plan")){
            if(sRec.getUnits().equals("Miles")) {
                setMiles();
            }
            else if(sRec.getUnits().equals("Kilometers")) {
                setKilometers();
            }

            Destination[] trip;
            trip = sRec.planTrip();

            Itinerary i = new Itinerary(trip, sRec.getOpt());
            ArrayList<Edge> edges = i.getShortestPath();
            View view = new View(buildPath);
            String svg = "";//view.drawMapString(trip,edges);
            ServerPlanTrip servP = new ServerPlanTrip(svg,trip,edges);
            return gson.toJson(servP, ServerPlanTrip.class);
        }
        else if(sRec.getdoWhat().equals("upload")){
            Destination[] trip = sRec.planTrip(); // look for the airports by code
            ServerResponse sRes = new ServerResponse(trip);
            return gson.toJson(sRes, ServerResponse.class);

        }
        return null;
    }

    private void setHeaders(Response res) {
        // Declares returning type json
        res.header("Content-Type", "application/json");

        // Ok for browser to call even if different host host
        res.header("Access-Control-Allow-Origin", "*");
        res.header("Access-Control-Allow-Headers", "*");
    }
}