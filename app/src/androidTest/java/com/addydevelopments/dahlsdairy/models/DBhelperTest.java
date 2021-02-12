package com.addydevelopments.dahlsdairy.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

public class DBhelperTest {
    DBhelper dBhelper;

    @Before
    public void setUp() throws Exception {
        dBhelper = new DBhelper();
    }

    @Test
    public void getRoutes() throws ExecutionException, InterruptedException {
        List<Route> routeList = new ArrayList<>();

        routeList = dBhelper.getRoutes();

        assertEquals(true, routeList != null);


    }

    @Test
    public void getCustomerJSONS() throws ExecutionException, InterruptedException {
        List<Customer> customers = new ArrayList<>();
        customers = dBhelper.getCustomerJSONS();

        assertEquals(true, customers != null);
    }

    @Test
    public void getCustomerJSONSByName() throws ExecutionException, InterruptedException {
        List<Customer> customers = new ArrayList<>();
        String routeIDS = "ObjectId(5ffb8749c3e20365478259c1), ObjectId(5ffd11aae0933ab6367df1aa)";
        customers = dBhelper.getCustomerJSONSByName(routeIDS);

        assertEquals(true, customers != null);

    }

    @Test
    public void getProductJSONS() throws ExecutionException, InterruptedException {
        List<Product> products = new ArrayList<>();
        products = dBhelper.getProductJSONS();
        assertEquals(true, products != null);
    }


}