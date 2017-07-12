package com.sales.tax.test.io;

import com.sales.tax.io.InputParser;
import com.sales.tax.io.registry.Value;
import com.sales.tax.io.util.CommonUtil;
import com.sales.tax.io.util.IOUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Nilesh on 02-07-2017.
 */
public class TestInputParser {

    private static final Function<Value, Map<String, String>> fun = (v) -> {
        if(v != null){
            Map<String, String> values = (Map)v.getValue();
            return values;
        }
        return null;
    };

    private Person person;

    @Before
    public void init(){
        System.setProperty(CommonUtil.appNames, "test");
        this.person = new Person("Nilesh", 27);
    }

    @Test
    public void testInputParserWithStringInput(){
        try {
            String testStr = "1 imported bottle of perfume at 47.50";
            Map<String, String> expected = new HashMap<String, String>();
            expected.put("unit", "bottle");
            expected.put("quantity", "1");
            expected.put("price", "47.50");
            expected.put("imported", "imported");
            expected.put("name", "perfume");
            InputParser parser = IOUtil.getInputParser("test");

            Map<String, String> extractedValues = parser.parse(testStr, fun);


            System.out.println(extractedValues);

            for(Map.Entry<String, String> entry : expected.entrySet()){
                assert(entry.getValue().equals(expected.get(entry.getKey())));
            }

        }catch (Exception e){
            e.printStackTrace();
            assert(false);
        }
    }




}
