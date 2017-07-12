package com.sales.tax.app.util;

import com.sales.tax.cache.CacheManager;
import com.sales.tax.cache.impl.CacheType;
import com.sales.tax.app.entity.Product;
import com.sales.tax.app.entity.ProductCategory;
import com.sales.tax.app.entity.Tax;
import com.sales.tax.io.util.CommonUtil;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;


/**
 * Created by Nilesh on 05-07-2017.
 */
public class ProductUtil {

    private static final Logger logger = LoggerFactory.getLogger(ProductUtil.class);
    private static final String FILE_SUFFIX = "-product-config.xml";
    private static volatile boolean isRefresh = true;
    private static CacheManager manager = CacheManager.getInstance(CacheType.LOCAL_CACHE);

    public static void refresh(){
        isRefresh = true;
    }



    public static Map<Object, Object> getProducts() throws Exception{
        if(manager.getAll(AppCacheIdentifier.PRODUCT_CACHE) == null){
            loadMeta();
        }
        return manager.getAll(AppCacheIdentifier.PRODUCT_CACHE);
    }

    public static Product getProduct(String id) throws Exception{
        if(manager.get(AppCacheIdentifier.PRODUCT_CACHE, id) == null)
            loadMeta();

        return (Product) manager.get(AppCacheIdentifier.PRODUCT_CACHE, id);
    }

    public static ProductCategory getProductCategory(String id) throws Exception{
        if(manager.get(AppCacheIdentifier.PRODUCT_CATEGORY_CACHE, id) == null)
            loadMeta();

        return (ProductCategory) manager.get(AppCacheIdentifier.PRODUCT_CATEGORY_CACHE, id);
    }

    private static void loadMeta() throws Exception{
        if(!isRefresh)
            return;
        isRefresh = false;

        String apps = System.getProperty(CommonUtil.appNames);
        StringTokenizer tokenizer = new StringTokenizer(apps, ",");
        String configDir = System.getProperty(CommonUtil.configDir);
        logger.debug(configDir);
        while(tokenizer.hasMoreElements()){
            String prefix = tokenizer.nextToken();
            InputStream stream = TaxUtil.class.getClass().getResourceAsStream("/config/"+prefix+FILE_SUFFIX);

            if(stream == null)
                stream = new FileInputStream(configDir+"/config/"+prefix+FILE_SUFFIX);

            if(stream != null){
                loadMeta(stream);
            }else
                logger.warn("product config not found.");
        }
    }

    private static void loadMeta(InputStream stream) throws Exception{

        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(stream);
        Element root = document.getRootElement();

        parseRoot(root);

    }

    private static void parseRoot(Element root) throws Exception{

        if(root == null){
            throw new Exception("No product categories provided!");
        }
        Element globalTaxElement = root.getChild("global-taxes");
        List<Element> globalTaxElementList = globalTaxElement != null? globalTaxElement.getChildren("tax") : null;
        Set<Tax> globalTaxes = parseTaxes(globalTaxElementList);

        Element categoryElement = root.getChild("categories");
        List<Element> categoryElementList = categoryElement != null ? categoryElement.getChildren("category") : null;

        if(CommonUtil.isNullOrEmpty(categoryElementList))
            throw new Exception("No product categories provided!");

        for(Element element : categoryElementList){

            ProductCategory category = parseProductCategory(element, globalTaxes);
            if(category != null)
                manager.put(AppCacheIdentifier.PRODUCT_CATEGORY_CACHE, category.getId(), category);
        }
    }

    private static ProductCategory parseProductCategory(Element productCategoryElement, Set<Tax> globalTaxes) throws Exception{

        if(productCategoryElement == null)
            return null;

        String id = productCategoryElement.getAttributeValue("id");
        String title = productCategoryElement.getAttributeValue("title");

        if(CommonUtil.isNullOrEmpty(id) || CommonUtil.isNullOrEmpty(title))
            throw new Exception("Invalid product category");

        Element taxesElement = productCategoryElement.getChild("taxes");
        List<Element> taxElements = taxesElement != null ? taxesElement.getChildren("tax") : null;
        Set<Tax> taxes = parseTaxes(taxElements);
        boolean noTaxes = productCategoryElement.getChild("exempted") != null;

        Element productElement = productCategoryElement.getChild("products");
        List<Element> productElementList = productElement != null ? productElement.getChildren("product") : null;

        parseProducts(productElementList, id);

        if(!noTaxes) {
            if (taxes != null && globalTaxes != null) {
                taxes.addAll(globalTaxes);
            } else if (taxes == null) {
                taxes = globalTaxes;
            }
        }

        ProductCategory category = new ProductCategory(id, title);
        if(taxes != null && !noTaxes)
            category.setTaxList(new ArrayList<Tax>(taxes));
        return category;
    }


    private static List<Product> parseProducts(List<Element> productElementList, String categoryId) throws Exception{

        if(CommonUtil.isNullOrEmpty(productElementList))
            return null;

        List<Product> products = new ArrayList<Product>();

        for(Element productElement : productElementList){

            String id = productElement.getAttributeValue("id");
            String name = productElement.getAttributeValue("name");

            if(CommonUtil.isNullOrEmpty(id) || CommonUtil.isNullOrEmpty(name)){
                logger.warn("Product id or name is empty for category id : "+ categoryId);
                continue;
            }

            Product product = new Product(id, name, categoryId);
            manager.put(AppCacheIdentifier.PRODUCT_CACHE, id, product);
            products.add(product);
        }

        return products;
    }





    private static Set<Tax> parseTaxes(List<Element> taxElements) throws Exception{

        if(CommonUtil.isNullOrEmpty(taxElements))
            return null;

        Set<Tax> taxes = new HashSet<Tax>();

        for(Element taxElement : taxElements){
            String taxId = taxElement.getAttributeValue("id");
            Tax tax = null;
            if(!CommonUtil.isNullOrEmpty(taxId) && (tax = TaxUtil.getTax(taxId)) != null){
                taxes.add(tax);
            }else{
                throw new Exception("Invalid tax id : "+ taxId);
            }
        }

        return taxes;
    }



}
