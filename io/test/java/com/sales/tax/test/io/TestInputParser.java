package com.sales.tax.test.io;

import com.sales.tax.io.InputParser;
import com.sales.tax.io.registry.Value;
import com.sales.tax.io.util.CommonUtil;
import com.sales.tax.io.util.IOUtil;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.function.Function;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Nilesh on 02-07-2017.
 */
public class TestInputParser {

    private static final Function<Value, Person> fun = (v) -> {
        if(v != null){
            Map<String, String> values = (Map)v.getValue();
            return new Person(values.get("name"), Integer.parseInt(values.get("age")));
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
            String testStr = "My name is Nilesh. My age is 27.";
            InputParser parser = IOUtil.getInputParser("test");

            Person parsedPerson = parser.parse(testStr, fun);
            assert(person.equals(parsedPerson));

        }catch (Exception e){
            e.printStackTrace();
            assert(false);
        }
    }


    @Test
    public void testInputParserWithFileput(){

    }


}
