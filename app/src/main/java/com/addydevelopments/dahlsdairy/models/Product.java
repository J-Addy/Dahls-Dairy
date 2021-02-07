package com.addydevelopments.dahlsdairy.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;

public class Product implements Parcelable {

    //Initializes variables
    private String productName;
    private double productPrice;
    private String productType;
    private String productID;
    private int quantity;

    //Generic constructor
    public Product(){}

    //Overload constructor
    public Product(String productID, String productName, double productPrice, String productType, int quantity) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productType = productType;
        this.quantity = quantity;
    }

    //Constructor to make object parcelable
    protected Product(Parcel in) {
        productName = in.readString();
        productPrice = in.readDouble();
        productType = in.readString();
        productID = in.readString();
        quantity = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    //Getter and setter methods
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getProductPrice(){ return productPrice; }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductID() { return productID;}

    public void setProductID(String productID) { this.productID = productID; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    //Gets product total price in the form of a string
    public String getTotalPrice(){
        DecimalFormat df = new DecimalFormat("0.00");
        double total = quantity * productPrice;
        String formatted = df.format(total);
        return formatted;
    }

    //Gets product price in the form of a double for mathematical operations
    public double getTotal(){
        double total  = quantity * productPrice;
        return total;
    }

    //Gets a formatted version of the product price
    public String getProductPriceString() {
        DecimalFormat df = new DecimalFormat("0.00");
        String formatted = df.format(productPrice);
        return formatted;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productName);
        dest.writeDouble(productPrice);
        dest.writeString(productType);
        dest.writeString(productID);
        dest.writeInt(quantity);
    }
}
