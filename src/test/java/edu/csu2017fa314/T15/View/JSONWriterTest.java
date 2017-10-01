package edu.csu2017fa314.T15.View;

import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;


public class JSONWriterTest {

    private String path = "."+File.separator+"data" + File.separator+ "test_output" + File.separator;


    @Test
    public void add() throws Exception {
        JSONWriter writer = new JSONWriter(path + "JSONWriterTest.json");
        HashMap<String, String> input1= new HashMap<>();
        input1.put("start", "start name 1");
        input1.put("end", "end name 2");
        input1.put("distance", "10000");
        writer.add(input1);

        HashMap<String, String> input2= new HashMap<>();
        input2.put("start", "start name 2");
        input2.put("end", "end name 3");
        input2.put("distance", "20000");
        writer.add(input2);

        HashMap<String, String> input3= new HashMap<>();
        input3.put("start", "start name 3");
        input3.put("end", "end name 4");
        input3.put("distance", "30000");
        writer.add(input3);

        HashMap<String, String> input4= new HashMap<>();
        input4.put("start", "start name 4");
        input4.put("end", "end name 4");
        input4.put("distance", "40000");
        writer.add(input4);
        writer.write();
        writer.close();

        File file1 = new File(path + "JSONWriterTest.json");
        File file2 = new File(path + "Sample.json");

        boolean isEqual;
        try {
            isEqual = FileUtils.contentEquals(file1, file2);
        }
        catch (IOException e) {
            isEqual = false;
        }
        assertTrue(isEqual);
    }

    @Test
    public void writeJSONFile() {
        JSONWriter writer = new JSONWriter(path + "JSONWriterTest.json");
        writer.add("start name 1", "end name 2", 10000);
        writer.add("start name 2", "end name 3", 20000);
        writer.add("start name 3", "end name 4", 30000);
        writer.add("start name 4", "end name 4", 40000);
        writer.write();
        writer.close();

        File file1 = new File(path + "JSONWriterTest.json");
        File file2 = new File(path + "Sample.json");
        boolean isEqual;
        try {
            isEqual = FileUtils.contentEquals(file1, file2);
        }
        catch (IOException e) {
            isEqual = false;
        }
        assert(isEqual);
    }
}
