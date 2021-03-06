package com.hadoop.ss.mapper;

import com.hadoop.ss.entity.PersonEntity;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SecondarySortMapperTest {

    MapDriver<LongWritable, Text, PersonEntity, Text> mapDriver;

    /**
     *
     */
    @Before
    public void setUp() {
        SecondarySortMapper mapper = new SecondarySortMapper();
        mapDriver = MapDriver.newMapDriver();
        mapDriver.setMapper(mapper);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testMapper() throws Exception {
        List<String> testList;
        try (Stream<String> stream = Files.lines(Paths.get("src/test/resources/nameTest.csv"))) {
            testList = stream.collect(Collectors.toList());
        } catch (IOException e) {
            throw new Exception();
        }
        for (String list : testList) {
            String[] listArr = list.split(",");
            mapDriver.withInput(new LongWritable(0), new Text(list));
            mapDriver.addOutput(new PersonEntity(listArr[1], listArr[0]), new Text(listArr[1]));
            mapDriver.runTest();
        }
    }
}
