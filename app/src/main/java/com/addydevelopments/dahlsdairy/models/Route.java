package com.addydevelopments.dahlsdairy.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.List;

public class Route implements Parcelable {

    //Initialize variables
    private String routeName;
    private String customerIDs;
    private String routeID;

    //Generic constructor
    public Route() {}

    //Overload Constructor
    public Route(String rI, String rN, String cI){
        this.routeID = rI;
        this.routeName = rN;
        this.customerIDs = cI;

    }

    //Getter and setter methods

    protected Route(Parcel in) {
        routeName = in.readString();
        customerIDs = in.readString();
        routeID = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(routeName);
        dest.writeString(customerIDs);
        dest.writeString(routeID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Route> CREATOR = new Creator<Route>() {
        @Override
        public Route createFromParcel(Parcel in) {
            return new Route(in);
        }

        @Override
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };

    public String getRouteID() {
        return routeID;
    }

    public void setRouteID(String routeID) {
        this.routeID = routeID;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getCustomerIDs() {
        customerIDs = customerIDs.replace("\"", "");
        return customerIDs;
    }

    public void setCustomerIDs(String customerIDs) {
        this.customerIDs = customerIDs;
    }



}