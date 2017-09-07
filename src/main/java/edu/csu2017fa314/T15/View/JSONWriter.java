package edu.csu2017fa314.T15.View;
import org.json.*;
import java.nio.charset.StandardCharsets;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;


public class JSONWriter {

    public JSONWriter (String path)
    {
        try {
            writer = new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8);
        }
        catch (java.io.IOException e){
            System.out.println("Error: Could not create writer");
        }
        list = new JSONArray();
    }

    public void add(String id1, String id2, long distance)
    {
        JSONObject newItem = new JSONObject();
        newItem.put("start", id1);
        newItem.put("end", id2);
        newItem.put("distance", distance);
        list.put(newItem);
    }

    public void write()
    {
        try  {
            writer.write(list.toString(2));
        }
        catch  (java.io.IOException e) {
            System.out.println("Error: Could not write to file!");
        }
    }

    public void close() {
        try {
            writer.close();
        }
        catch (java.io.IOException e){
            System.out.println("Error: Could not close writer");
        }
    }


    private OutputStreamWriter writer;
    private JSONArray list;
}
