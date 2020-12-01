package com.addydevelopments.dahlsdairy.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.addydevelopments.dahlsdairy.controllers.RecyclerType;


public class Customer implements Parcelable, RecyclerType {

    //Customer Attributes
    String customerID;
    String lastName;
    String firstName;
    String address;
    String balance;
    String phoneNumber;
    String notes;

    //Generic Constructor
    public Customer(){}

    //Constructor
    public Customer(String id, String lastName, String firstName, String address, String balance, String phoneNumber, String notes) {
        this.customerID = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.balance = balance;
        this.phoneNumber = phoneNumber;
        this.notes = notes;

    }

    //Serialized object to be passed between activities
    protected Customer(Parcel in) {
        customerID = in.readString();
        lastName = in.readString();
        firstName = in.readString();
        address = in.readString();
        balance = in.readString();
        phoneNumber = in.readString();
        notes = in.readString();
    }


    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    //Getter and setter methods

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    public void setNotes(String notes) {this.notes = notes;}


    public String getCustomerID() {
        return customerID;
    }

    public String getLastName(){
        return lastName;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getAddress(){
        return address;
    }

    public String getBalance(){
        return balance;
    }

    public String getPhoneNumber() {return phoneNumber;}

    public String getNotes() {return notes;}

    @Override
    public int describeContents() {
        return 0;
    }

    //Method to pass serializaed object to next activity
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(customerID);
        dest.writeString(lastName);
        dest.writeString(firstName);
        dest.writeString(address);
        dest.writeString(balance);
        dest.writeString(phoneNumber);
        dest.writeString(notes);
    }

    @Override
    public int getType() {
        return RecyclerType.TYPE_CUSTOMER;
    }
}
