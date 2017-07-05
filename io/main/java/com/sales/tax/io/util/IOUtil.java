package com.sales.tax.io.util;

import com.sales.tax.cache.CacheManager;
import com.sales.tax.cache.impl.CacheType;
import com.sales.tax.io.InputParser;
import com.sales.tax.io.criteria.SimpleRegexCriteria;
import com.sales.tax.io.registry.Criteria;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Nilesh on 03-07-2017.
 */
public class IOUtil {

    private static boolean isRefresh = true;
    private static final String FILE_SUFFIX = "-input-parser-config.xml";

    public static InputParser getInputParser(String id) throws Exception{

        CacheManager manger = CacheManager.getInstance(CacheType.LOCAL_CACHE);
        InputParser parser = (InputParser)manger.get(ParserCacheIdentifier.INPUT_PARSER_CACHE, id);

        if(parser == null){
            loadMeta();
        }

        return parser = (InputParser)manger.get(ParserCacheIdentifier.INPUT_PARSER_CACHE, id);
    }

    private static void loadMeta() throws Exception{

        if(!isRefresh){
            return;
        }

        isRefresh = false;

        String apps = System.getProperty(CommonUtil.appNames);
        StringTokenizer tokenizer = new StringTokenizer(apps, ",");
        while(tokenizer.hasMoreElements()){
            InputStream stream = IOUtil.class.getClass().getResourceAsStream("/io/"+tokenizer.nextToken()+FILE_SUFFIX);
            if(stream != null){
                loadMeta(stream);
            }
        }
    }

    private static void loadMeta(InputStream inputStream) throws Exception{

        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(inputStream);
        CacheManager manager = CacheManager.getInstance(CacheType.LOCAL_CACHE);
        List<Element> configElements = document.getRootElement().getChildren("input-parser");

        for(Element configElement : configElements){
            InputParser parser = parseInputConfig(configElement);
            manager.put(ParserCacheIdentifier.INPUT_PARSER_CACHE, parser.getId(), parser);
        }

    }

    private static InputParser parseInputConfig(Element inputConfigElement) throws Exception {

        String id = inputConfigElement.getChildText("id");
        Element criteriasElement = inputConfigElement.getChild("criteria-list");

        if(criteriasElement == null)
            throw new Exception("no criteria present for input parser config : "+id);

        List<Element> criteriaElementList = criteriasElement.getChildren("criteria");

        if(CommonUtil.isNullOrEmpty(criteriaElementList))
            throw new Exception("no criteria present for input parser config : "+id);

        List<Criteria> criteriaList = parseCriteria(criteriaElementList);
        return new InputParser(id, criteriaList);
    }

    private static List<Criteria> parseCriteria(List<Element> criteriaElementList) throws Exception {

        List<Criteria> criteriaList = new ArrayList<Criteria>();

        for(Element criteriaElement : criteriaElementList){

            String key = criteriaElement.getChildText("key");
            String regex = criteriaElement.getChildText("regex");
            List<String> groupKeys = CommonUtil.toCSV(criteriaElement.getChildTextNormalize("group-keys"));
            boolean strict = CommonUtil.isNullOrEmpty(criteriaElement.getAttributeValue("strict")) ? "TRUE".equalsIgnoreCase(criteriaElement.getAttributeValue("strict")) : false;
            String type = criteriaElement.getAttributeValue("type");
            Criteria criteria = null;
            if(CommonUtil.isNullOrEmpty(type) || "regex".equalsIgnoreCase(type)){
                criteria = new SimpleRegexCriteria(regex, key, groupKeys);

            }else if("custom".equalsIgnoreCase(type)){
                String className = criteriaElement.getAttributeValue("class");
                if(CommonUtil.isNullOrEmpty(className))
                    throw new Exception("class not present for custom criteria. key :"+ key);

                Class<? extends Criteria> clazz = (Class<? extends Criteria>) Class.forName(className);
                criteria = clazz.newInstance();
            }
            if(strict)
                criteria.isStrict();

            criteriaList.add(criteria);
        }
        return criteriaList;
    }

    public static void refresh(){
        isRefresh = true;
    }

}
