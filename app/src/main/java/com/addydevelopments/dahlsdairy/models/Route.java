package com.addydevelopments.dahlsdairy.models;

import com.addydevelopments.dahlsdairy.controllers.RecyclerType;

import java.util.Arrays;
import java.util.List;

public class Route {

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
        return customerIDs;
    }

    public void setCustomerIDs(String customerIDs) {
        this.customerIDs = customerIDs;
    }



}