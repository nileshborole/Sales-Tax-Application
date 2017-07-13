package com.sales.tax.io.criteria;

import com.sales.tax.io.exceptions.CommonExceptionMessage;
import com.sales.tax.io.exceptions.CriteriaEvaluationException;
import com.sales.tax.io.registry.Criteria;
import com.sales.tax.io.registry.Value;
import com.sales.tax.io.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Nilesh on 02-07-2017.
 */
public class SimpleRegexCriteria implements Criteria<String> {

    private static final Logger logger = LoggerFactory.getLogger(SimpleRegexCriteria.class);
    private Pattern pattern;
    private String key;
    private Matcher matcher;
    private List<String> groupKeys;
    private boolean strict = false;

    public SimpleRegexCriteria(String regex, String key, List<String> groupKeys){
        if(CommonUtil.isNullOrEmpty(regex))
            throw new IllegalArgumentException("Invalid regex expression : "+ regex);

        this.pattern = Pattern.compile(regex.trim());
        this.key = key;
        this.groupKeys = groupKeys;

    }

    public SimpleRegexCriteria(String regex, String key){
        this(regex, key, null);
    }

    @Override
    public void strict(){
        this.strict = true;
    }

    public boolean isStrict(){
        return this.strict;
    }

    @Override
    public boolean test(String str) {

        if(!CommonUtil.isNullOrEmpty(str)){
            this.matcher = this.pattern.matcher(str);
            return this.matcher.matches();
        }
        return false;
    }

    @Override
    public Value apply(String str) {

        if(test(str)){
            int count = this.matcher.groupCount();

            if(CommonUtil.isNullOrEmpty(this.groupKeys)){
                List<String> list = new ArrayList<String>();

                for(int i = 1; i <= count; i++){
                    list.add(this.matcher.group(i));
                }

                return new Value(key, list);

            }else{
                if(this.strict && count-1 != this.groupKeys.size())
                    throw new CriteriaEvaluationException(CommonExceptionMessage.REGEX_NOT_MATCHED, "Wrong regular expression. Regex group count "+count + " should be equal to group keys :"+this.groupKeys);

                Map<String, String> extractedValues = new HashMap<String, String>();
                for(String groupKey : this.groupKeys){

                    String temp = null;
                    try {
                        temp = this.matcher.group(groupKey);
                    }catch (Exception e){
                        if(this.strict)
                            throw new CriteriaEvaluationException(CommonExceptionMessage.REGEX_NOT_MATCHED, e);
                        logger.warn("Group not found :"+groupKey, e);
                    }

                    if(CommonUtil.isNullOrEmpty(temp) && this.strict)
                        throw new CriteriaEvaluationException(CommonExceptionMessage.REGEX_NOT_MATCHED,"Group '"+key+"' not found in input string :"+str);

                    extractedValues.put(groupKey, temp);
                }

                return new Value(key, extractedValues);
            }
        }

        return null;
    }


}
