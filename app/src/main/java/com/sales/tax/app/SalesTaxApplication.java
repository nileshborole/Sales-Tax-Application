package com.sales.tax.app;

import com.sales.tax.app.entity.Product;
import com.sales.tax.app.entity.PurchasedProduct;
import com.sales.tax.app.entity.Receipt;
import com.sales.tax.app.entity.TaxCategory;
import com.sales.tax.app.registry.CustomUnit;
import com.sales.tax.app.registry.Quantity;
import com.sales.tax.app.util.ProductUtil;
import com.sales.tax.app.util.TaxUtil;
import com.sales.tax.cache.CacheManager;
import com.sales.tax.cache.impl.CacheType;
import com.sales.tax.io.InputParser;
import com.sales.tax.io.exceptions.AppException;
import com.sales.tax.io.exceptions.AppRuntimeException;
import com.sales.tax.io.exceptions.CommonExceptionMessage;
import com.sales.tax.io.registry.Value;
import com.sales.tax.io.util.CommonUtil;
import com.sales.tax.io.util.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by Nilesh on 04-07-2017.
 */
public class SalesTaxApplication {

    private static final Logger logger = LoggerFactory.getLogger(SalesTaxApplication.class);
    private static DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private InputParser parser;
    private ReceiptGenerator generator;

    private CacheManager manager;
    private Map<String, Product> productMap;
    private Map<String, TaxCategory> categoryMap;

    private Function<Value, PurchasedProduct> inputLineParserFun = (value) -> {

        if(value != null){
            return purchasedProduct((Map<String, String>)value.getValue());
        }
        return null;
    };

    private Function<Receipt, StringBuilder> outputFormatter = (receipt) -> {
        if(receipt != null){

            StringBuilder builder = new StringBuilder();
            List<PurchasedProduct> products = receipt.getPurchasedProducts();
            for(PurchasedProduct product : products){
                Quantity quantity = product.getQuantity();
                boolean isImported = product.isTaxCategoryApplied(categoryMap.get("imported"));
                builder.append(quantity.intValue()).append(" ");
                if(isImported)
                    builder.append("imported ");
                if(quantity.getUnit() != null && !CommonUtil.isNullOrEmpty(quantity.getUnitName())){
                    builder.append(quantity.getUnitName()).append(" of ");
                }

                builder.append(product.getName()).append(": ").append(decimalFormat.format( product.getEndPrice().doubleValue()));
                builder.append("\n");
            }
            builder.append("Sales Taxes: ").append(receipt.getTotalSaleTax() != null ? decimalFormat.format(receipt.getTotalSaleTax().doubleValue()) : "0.00");
            builder.append("\nTotal: ").append(decimalFormat.format(receipt.getTotal().doubleValue()));

            return builder;
        }
        return null;
    };

    public SalesTaxApplication(String inputParserId) throws Exception{
        try {
            parser = IOUtil.getInputParser(inputParserId);
        }catch (Exception e){
            logger.error("Failed to load input parser : "+ inputParserId, e);
            throw e;
        }
        manager = CacheManager.getInstance(CacheType.LOCAL_CACHE);
        generator = new ReceiptGenerator();
        populateProductMap();
        populateTaxCategoryMap();
    }

    public StringBuilder printReceipt(final List<String> productList) throws Exception{
        List<PurchasedProduct> products = new ArrayList<PurchasedProduct>();
        for(String str : productList){
            try {
                PurchasedProduct product = parser.parse(str, inputLineParserFun);
                if (product == null) {
                    throw new Exception();
                }
                products.add(product);
            }catch (Exception e){
                throw new AppException(CommonExceptionMessage.INPUT_PARSE_ERROR, new Object[] { str });
            }
        }
        Receipt receipt = generator.generateReceipt(products);

        System.out.println(outputFormatter.apply(receipt));
        return outputFormatter.apply(receipt);
    }

    private void populateProductMap() throws Exception{
        Map<Object, Object> products = ProductUtil.getProducts();
        productMap = new HashMap<String, Product>();
        for(Object obj : products.values()){
            Product product = (Product) obj;
            productMap.put(product.getName().toLowerCase(), product);
        }
    }

    private void populateTaxCategoryMap() throws Exception{
        Map<Object, Object> taxCategories = TaxUtil.getTaxCategories();
        categoryMap = new HashMap<String, TaxCategory>();
        for(Object obj : taxCategories.values()){
            TaxCategory category = (TaxCategory) obj;
            categoryMap.put(category.getName(), category);
        }
    }

    private PurchasedProduct purchasedProduct(Map<String, String> values){
        if(values == null){
            logger.error("Failed to parse input.");
            throw new AppRuntimeException(CommonExceptionMessage.INPUT_PARSE_ERROR);
        }

        Product product = productMap.get(values.get("name").toLowerCase());
        if(product == null) {
            throw new AppRuntimeException(CommonExceptionMessage.INPUT_PARSE_ERROR, "Error while parsing input, product is not extracted.");
        }

        String priceStr = values.get("price");
        String currencyStr = values.get("currency");
        String unitStr = values.get("unit");
        String quantityStr = values.get("quantity");

        if(CommonUtil.isNullOrEmpty(priceStr))
            throw new AppRuntimeException(CommonExceptionMessage.INPUT_PARSE_ERROR,"Error while parsing input, product price is not extracted.");

        if(CommonUtil.isNullOrEmpty(quantityStr))
            throw new AppRuntimeException(CommonExceptionMessage.INPUT_PARSE_ERROR,"Error while parsing input, product quantity is not extracted.");

        Quantity price = new Quantity(new BigDecimal(priceStr), CommonUtil.isNullOrEmpty(currencyStr)? null : new CustomUnit(currencyStr));
        Quantity quantity = new Quantity(new BigDecimal(quantityStr), CommonUtil.isNullOrEmpty(unitStr)? null : new CustomUnit(unitStr) );
        PurchasedProduct purchasedProduct = new PurchasedProduct(product, price, quantity);

        if(!CommonUtil.isNullOrEmpty(values.get("imported"))){
            TaxCategory category = categoryMap.get("imported");
            if(category == null)
                logger.error("Imported tax category not found");

            purchasedProduct.addTaxCategoryCode(category);
        }

        purchasedProduct.addTaxCategoryCode(categoryMap.get("basic"));

        return purchasedProduct;
    }



}

