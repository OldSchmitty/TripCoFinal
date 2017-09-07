package edu.csu2017fa314.T15.View;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;


public class JSONWriterTest {

    @Test
    public void writeJSONFile() {
        JSONWriter writer = new JSONWriter("."+File.separator+"web"+File.separator+"JSONWriterTest.json");
        writer.add("start name 1", "end name 2", 10000);
        writer.add("start name 2", "end name 3", 20000);
        writer.add("start name 3", "end name 4", 30000);
        writer.add("start name 4", "end name 4", 40000);
        writer.write();
        writer.close();

        File file1 = new File("."+File.separator+"web"+File.separator+"JSONWriterTest.json");
        File file2 = new File("."+File.separator+"web"+File.separator+"Sample1.json");
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