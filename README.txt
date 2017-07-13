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
Time spent on testing : 3.5 hrs
Total time required : 12 hrs.

With more time I would have done
1. Output formatter for generated receipt, as it can be printed on web, downloaded as pdf/doc.
2. Custom exceptions to improve logging.
    Refactoring of common code like application custom exceptions and CommonUtil to separate common module.

====================================================MODULES========================================================

Modules:
1. io module     : io module responsible for parsing using inputs. Parsing criteria is configurable.
                    Advantage of io module is user can configure new type of parsing logic without changing code
                    eg: regex,custom code.
2. cache module  : cache module is independent module and provide factory of different types of cache.
                    All modules of application depends on cache. It abstracts the Cache implementation so we can change out
                    caching implementation without affecting other modules. We can also provide diffent types of cache
                    implementation like local, remote or distributes cache.
3. app module    : This is a main module which contains application entity like Tax, Product etc and responsible for
                    generating receipt from given list of PurchasedProduct.


