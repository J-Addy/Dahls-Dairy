package com.addydevelopments.dahlsdairy.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RouteTest {
    Route route;

    @Before
    public void setUp() {
        route = new Route("1", "RouteName", "1,2,3");
    }

    @Test
    public void getRouteID() {
        assertEquals("1", route.getRouteID());
    }

    @Test
    public void setRouteID() {
        route.setRouteID("2");
        assertEquals("2", route.getRouteID());
    }

    @Test
    public void getRouteName() {
        assertEquals("RouteName", route.getRouteName());

    }

    @Test
    public void setRouteName() {
        route.setRouteName("NewRouteName");
        assertEquals("NewRouteName", route.getRouteName());
    }

    @Test
    public void getCustomerIDs() {
        assertEquals("1,2,3", route.getCustomerIDs());
    }

    @Test
    public void setCustomerIDs() {
        route.setCustomerIDs("1,2,3,4");
        assertEquals("1,2,3,4", route.getCustomerIDs());
    }
}