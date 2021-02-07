package com.addydevelopments.dahlsdairy.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OrderTest {

    Order order;
    List<Product> products = new ArrayList<>();
    List<Product> newProductlist = new ArrayList<>();

    @Before
    public void setUp() {
        Product firstProduct = new Product("1","product", 20.00, "Local", 2);
        Product secondProduct = new Product("1","product", 20.00, "Local", 2);
        products.add(firstProduct);
        products.add(secondProduct);
        order = new Order("1", "John", "Doe", "12/21/2020", products, 80.00);
    }

    @Test
    public void getOrderID() {
        assertEquals("1", order.getOrderID());
    }

    @Test
    public void setOrderID() {
        order.setOrderID("2");
        assertEquals("2", order.getOrderID());
    }

    @Test
    public void getFirstName() {
        assertEquals("John", order.getFirstName());
    }

    @Test
    public void setFirstName() {
        order.setFirstName("Joe");
        assertEquals("Joe", order.getFirstName());
    }

    @Test
    public void getLastName() {
        assertEquals("Doe", order.getLastName());
    }

    @Test
    public void setLastName() {
        order.setLastName("Johnson");
        assertEquals("Johnson", order.getLastName());
    }

    @Test
    public void getDate() {
        assertEquals("12/21/2020", order.getDate());
    }

    @Test
    public void setDate() {
        order.setDate("12/22/2020");
        assertEquals("12/22/2020", order.getDate());
    }

    @Test
    public void getProducts() {
        assertEquals(products, order.getProducts());
    }

    @Test
    public void setProducts() {
        Product product = new Product("1","product", 20.00, "Local", 2);
        newProductlist.add(product);
        order.setProducts(newProductlist);
        assertEquals(newProductlist, order.getProducts());

    }

    @Test
    public void getTotal() {
        assertEquals(80.00, order.getTotal(), 2);
    }

    @Test
    public void setTotal() {
        order.setTotal(90.00);
        assertEquals(90.00, order.getTotal(), 2);
    }
}