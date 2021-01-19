package com.addydevelopments.dahlsdairy.models;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Order {


    //Initialzie variables
    public String orderID;
    public String firstName;
    public String lastName;
    public String date;
    public List<Product> products;

    //Generic constructor
    public Order(){}

    //overload constructor
    public Order(String firstName, String lastName, List<Product> products){
        this.firstName = firstName;
        this.lastName = lastName;
        this.products = products;
    }

    //overload constructor
    public Order(String orderID, String firstName, String lastName, String date, List<Product> products){
        this.orderID = orderID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.orderID = orderID;
        this.products = products;
    }

    //getters and setters

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    //rMethod to create an invoice based on the order object
    public void createInvoice() throws ExecutionException, InterruptedException {
        DBhelper dBhelper = new DBhelper();
        dBhelper.createInvoice(this);
    }



}
