package com.addydevelopments.dahlsdairy.controllers;

public interface RecyclerType {
    int TYPE_ROUTE = 1;
    int TYPE_CUSTOMER = 2;
    int TYPE_PRODUCT = 3;
    int TYPE_INVOICE = 4;

    int getType();
}
