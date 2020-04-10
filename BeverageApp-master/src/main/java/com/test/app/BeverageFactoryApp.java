package com.test.app;


import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.app.factory.BeverageFactory;

public class BeverageFactoryApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeverageFactoryApp.class);

    public static void main(String[] args) {

    	BeverageFactory factory = new BeverageFactory();

    	 //Modify the below string and run the program to calculate your order cost
    	System.out.println("Modify the below string and run the program to calculate your order cost  Empty string will ake default value");
    	System.out.println(" Chai ,-milk, -water,  Coffee, Mojito");
    	
    	String order = " Chai ,-milk, -water,  Coffee, Mojito";
    	Scanner sc =new Scanner(System.in);
    	String str=sc.nextLine();
    	if(!"".equals(str) && str!=null)
    	{
    		order=str;
    	}

        final double cost = factory.getInvoiceFromOrder(order);

        LOGGER.info("Your total cost is ${}", cost);

    }


}
