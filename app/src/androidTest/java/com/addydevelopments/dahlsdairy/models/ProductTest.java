package com.addydevelopments.dahlsdairy.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTest {
    Product product;

    @Before
    public void setUp() {
        product = new Product("1","product", 20.00, "Local", 2);
    }

    @Test
    public void getProductName() {
        assertEquals("product", product.getProductName());
    }

    @Test
    public void setProductName() {
        product.setProductName("new product");
        assertEquals("new product", product.getProductName());
    }

    @Test
    public void setProductPrice() {
        product.setProductPrice(30.00);
        assertEquals(30.00, product.getProductPrice(), 2);
    }

    @Test
    public void getProductPrice(){
        assertEquals(20.00, product.getProductPrice(),2);
    }

    @Test
    public void getProductType() {
        assertEquals("Local", product.getProductType());
    }

    @Test
    public void setProductType() {
        product.setProductType("Non-local");
        assertEquals("Non-local", product.getProductType());
    }

    @Test
    public void getProductID() {
        assertEquals("1", product.getProductID());
    }

    @Test
    public void setProductID() {
        product.setProductID("2");
        assertEquals("2", product.getProductID());
    }

    @Test
    public void getQuantity() {
        assertEquals(2, product.getQuantity());
    }

    @Test
    public void setQuantity() {
        product.setQuantity(3);
        assertEquals(3, product.getQuantity());
    }

    @Test
    public void getTotalPrice() {
        assertEquals("40.00", product.getTotalPrice());
    }

    @Test
    public void getTotal() {
        assertEquals(40.00, product.getTotal(),2);
    }

    @Test
    public void getProductPriceString() {
        assertEquals("20.00", product.getProductPriceString());
    }
}