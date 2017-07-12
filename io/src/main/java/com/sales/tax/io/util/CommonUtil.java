package com.sales.tax.io.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Nilesh on 03-07-2017.
 */
public class CommonUtil {

    public static final String appNames = "app.name.property";

    public static final String configDir = "app.settings.directory";

    public static boolean isNullOrEmpty(String str){
        return str == null || "".equals(str);
    }

    public static boolean isNullOrEmpty(Collection c){
        return c == null || c.isEmpty();
    }

    public static List<String> toCSV(String str){

        if(isNullOrEmpty(str))
            return null;

        StringTokenizer tokenizer = new StringTokenizer(str, ",");

        List<String> list = new ArrayList<>();

        while(tokenizer.hasMoreElements())
            list.add(tokenizer.nextToken());

        return list;
    }

}
