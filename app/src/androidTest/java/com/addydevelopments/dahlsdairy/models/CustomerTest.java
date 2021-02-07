package com.addydevelopments.dahlsdairy.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerTest {

    Customer customer;

    @Before
    public void setUp(){
        customer = new Customer("1", "Doe", "John", "123 Fake St.", "20.00", "2187806334", "Note");
    }

    @Test
    public void setCustomerID() {
        customer.setCustomerID("2");
        assertEquals("2", customer.getCustomerID());
    }

    @Test
    public void setLastName() {
        customer.setLastName("Johnson");
        assertEquals("Johnson", customer.getLastName());
    }

    @Test
    public void setFirstName() {
        customer.setFirstName("Joe");
        assertEquals("Joe", customer.getFirstName());
    }

    @Test
    public void setAddress() {
        customer.setAddress("123 Real St.");
        assertEquals("123 Real St.", customer.getAddress());
    }

    @Test
    public void setBalance() {
        customer.setBalance("30.00");
        assertEquals("30.00", customer.getBalance());
    }

    @Test
    public void setPhoneNumber() {
        customer.setPhoneNumber("5555555555");
        assertEquals("5555555555", customer.getPhoneNumber());
    }

    @Test
    public void setNotes() {
        customer.setNotes("New Note");
        assertEquals("New Note", customer.getNotes());
    }

    @Test
    public void getCustomerID() {
        assertEquals("1", customer.getCustomerID());
    }

    @Test
    public void getLastName() {
        assertEquals("Doe", customer.getLastName());
    }

    @Test
    public void getFirstName() {
        assertEquals("John", customer.getFirstName());
    }

    @Test
    public void getAddress() {
        assertEquals("123 Fake St.", customer.getAddress());
    }

    @Test
    public void getBalance() {
        assertEquals("20.00", customer.getBalance());
    }

    @Test
    public void getPhoneNumber() {
        assertEquals("2187806334", customer.getPhoneNumber());
    }

    @Test
    public void getNotes() {
        assertEquals("Note", customer.getNotes());
    }

    @Test
    public void formatAddressforRouteOptimization() {
        assertEquals("123%2CFake%2CSt.", customer.formatAddressforRouteOptimization());
    }
}