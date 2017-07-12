                                Sales Tax Application
                               =======================

1. Build tool : Apache Maven v3.0+
2. Java version : 1.8
3. To run application:
        i.   cd <application_home>
        ii.  execute command "mvn clean install"
        iii. execute command "mvn exec:java"

4. Application configuration:
    /io/*-input-parser-config.xml : to configure input criteria (regex).
    /config/*-product-config.xml : to configure product categories, products and applied taxes on product.
    /config/*-tax-config.xml : to configure taxes.

    All configuration file/folder can be placed on classpath
    or app.config.directory system property can be set to absolute path of config directory,
        eg. <path>/SalesTaxApplication/app/setting

    A system property app.name.property must be set, contains comma separated app name. all configuration files must
    start with app name.



======================================================================================================================
Time spent on testing : 1.5 hrs
Total time required : 8 hrs.

With more time I would have done
1. Output formatter for generated receipt, as it can be printed on web, downloaded as pdf/doc.
2. Custom exceptions to improve logging.