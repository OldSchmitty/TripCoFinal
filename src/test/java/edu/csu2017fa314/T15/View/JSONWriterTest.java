package edu.csu2017fa314.T15.View;

import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;


public class JSONWriterTest {

    private String path = "."+File.separator+"data" + File.separator+ "test_output" + File.separator;
    private String samplePath = "."+File.separator+"data" + File.separator+ "resources" + File.separator;


    @Test
    public void add() throws Exception {
        JSONWriter writer = new JSONWriter(path + "JSONWriterHashMapTest.json");
        HashMap<String, String> input1= new HashMap<>();
        input1.put("latitude", "40°33\u203233\u2033N");
        input1.put("name", "Fort Collins");
        input1.put("id", "1");
        input1.put("longitude", "105°4\u203241\u2033W");
        writer.add(input1);

        HashMap<String, String> input2= new HashMap<>();
        input2.put("latitude", "39°45\u203243\u2033N");
        input2.put("name", "Denver");
        input2.put("id", "2");
        input2.put("longitude", "104°52\u203252\u2033W");
        writer.add(input2);

        HashMap<String, String> input3= new HashMap<>();
        input3.put("latitude", "39°15\u203250\u2033N");
        input3.put("name", "Limon");
        input3.put("id", "3");
        input3.put("longitude", "103°41\u203232\u2033W");
        writer.add(input3);

        writer.write();
        writer.close();

        File file1 = new File(path + "JSONWriterHashMapTest.json");
        File file2 = new File(samplePath + "DestinationsSample.json");

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
        writer.add(1, 2, 10000);
        writer.add(2, 3, 20000);
        writer.add(3, 4, 30000);
        writer.add(4, 4, 40000);
        writer.write();
        writer.close();

        File file1 = new File(path + "JSONWriterTest.json");
        File file2 = new File(samplePath + "Sample.json");
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
