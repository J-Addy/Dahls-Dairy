package com.addydevelopments.dahlsdairy.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;

public class Product implements Parcelable {

    private String productName;
    private double productPrice;
    private String productType;
    private int productID;
    private int quantity;



    public Product(int productID, String productName, double productPrice, String productType) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productType = productType;
    }

    protected Product(Parcel in) {
        productName = in.readString();
        productPrice = in.readDouble();
        productType = in.readString();
        productID = in.readInt();
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        DecimalFormat df = new DecimalFormat("0.00");
        String formatted = df.format(productPrice);
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getProductID() { return productID;}

    public void setProductID(int productID) { this.productID = productID; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getTotalPrice(){
        DecimalFormat df = new DecimalFormat("0.00");
        double total = quantity * productPrice;
        String formatted = df.format(total);
        return formatted;
    }

    public double getTotal(){
        double total  = quantity * productPrice;
        return total;
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
        dest.writeInt(productID);
        dest.writeInt(quantity);
    }
}
