package com.sales.tax.io;

import com.sales.tax.io.registry.Criteria;
import com.sales.tax.io.registry.Value;
import com.sales.tax.io.util.CommonUtil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by Nilesh on 03-07-2017.
 */
public class InputParser {

    private String id;
    private final List<Criteria> criteriaList;

    public InputParser(String id, List<Criteria> criteriaList){
        if(CommonUtil.isNullOrEmpty(criteriaList))
            throw new NullPointerException("");
        this.criteriaList = criteriaList;
        this.id = id;
    }

    public <R> R parse(String str, Function<Value, R> parseFunction) throws Exception{
        R result = null;
        for(Criteria criteria : criteriaList){
            Value value = criteria.apply(str);
            if(value != null){
                result = parseFunction.apply(value);
                break;
            }
        }

        return result;
    }

    public <R> List<R> parse(File file, Function<Value, R> parseFunction) throws Exception{

        List<R> result = new ArrayList<R>();

        try (Stream<String> stream = Files.lines(Paths.get(file.getAbsolutePath())) ){

            stream.forEach( str -> {
                try{
                    result.add(parse(str, parseFunction));
                }catch (Exception e){
                    throw new RuntimeException(e);
                }
            } );
        }
        return result;
    }

    public String getId() {
        return id;
    }
}
