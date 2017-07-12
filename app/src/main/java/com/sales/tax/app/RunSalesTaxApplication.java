package com.sales.tax.app;

import com.sales.tax.io.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Nilesh on 06-07-2017.
 */
public class RunSalesTaxApplication {

    private static final Logger logger = LoggerFactory.getLogger(RunSalesTaxApplication.class);

    private SalesTaxApplication application;

    public RunSalesTaxApplication(String inputParserId) throws Exception{
        this.application = new SalesTaxApplication(inputParserId);
    }

    public void run() throws Exception{
        System.out.println("Enter Input (enter r to generate receipt):");
        Scanner scanner = new Scanner(System.in);
        List<String> inputStrList = new ArrayList<String>();
        String next = null;
        while((next = scanner.nextLine()) != null){
            //String next = scanner.nextLine();
            if(!CommonUtil.isNullOrEmpty(next)){

                if("r".equalsIgnoreCase(next))
                    break;
                else
                    inputStrList.add(next);

            }
        }

        if(CommonUtil.isNullOrEmpty(inputStrList)){
            logger.info("No input provided, exiting...");
            return;
        }

        this.application.printReceipt(inputStrList);

    }

    public static void main(String[] args) {

        try{
            if(args.length < 1){
                logger.error("input parser id not provided. usage: java RunSalesTaxApplication <inputParserId>");
                return;
            }
            logger.debug("input parser id : "+args[0] +", app : "+System.getProperty(CommonUtil.appNames));
            RunSalesTaxApplication app = new RunSalesTaxApplication(args[0]);
            app.run();

        }catch (Exception e){
            logger.error("Unexpected error occurred.", e);
        }

    }

}
