package com.sales.tax.util;

import com.sales.tax.cache.CacheManager;
import com.sales.tax.cache.impl.CacheType;
import com.sales.tax.entity.Product;
import com.sales.tax.io.util.CommonUtil;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.InputStream;
import java.util.StringTokenizer;


/**
 * Created by Nilesh on 05-07-2017.
 */
public class ProductUtil {

    private static final String FILE_SUFFIX = "-product-config.xml";
    private static volatile boolean isRefresh = true;
    private static CacheManager manager = CacheManager.getInstance(CacheType.LOCAL_CACHE);


    public static void refresh(){
        isRefresh = true;
    }

    private static void loadMeta() throws Exception{
        if(!isRefresh)
            return;
        isRefresh = false;

        String apps = System.getProperty(CommonUtil.appNames);
        StringTokenizer tokenizer = new StringTokenizer(apps, ",");
        while(tokenizer.hasMoreElements()){
            InputStream stream = TaxUtil.class.getClass().getResourceAsStream("/config/"+tokenizer.nextToken()+FILE_SUFFIX);
            if(stream != null){
                loadMeta(stream);
            }
        }
    }

    private static void loadMeta(InputStream stream) throws Exception{

        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(stream);
        Element root = document.getRootElement();



    }



}
