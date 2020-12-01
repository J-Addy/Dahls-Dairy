package com.addydevelopments.dahlsdairy.models;

import com.addydevelopments.dahlsdairy.controllers.RecyclerType;

public class Route implements RecyclerType {
    private String routeID;
    private String routeName;
    private String customerIDs;

    public Route() {}

    public Route(String rI, String rN, String cI){
        this.routeID = rI;
        this.routeName = rN;
        this.customerIDs = cI;

    }

    public String getID(){ return this.routeID;}

    public String getRouteName(){ return this.routeName;}

    public String getCustomerIDs(){ return this.customerIDs;}

    public int[] getCustomerIDsArray(){
        String[] parts = this.customerIDs.split(",");
        int[] ints = new int[parts.length];
        for (int i = 0; i < ints.length; i++){
            ints[i] = Integer.parseInt(parts[i]);
        }
        return ints;
    }


    @Override
    public int getType() {
        return RecyclerType.TYPE_ROUTE;
    }
}