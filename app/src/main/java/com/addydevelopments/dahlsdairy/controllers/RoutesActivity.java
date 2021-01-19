package com.addydevelopments.dahlsdairy.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.addydevelopments.dahlsdairy.R;
import com.addydevelopments.dahlsdairy.models.DBhelper;
import com.addydevelopments.dahlsdairy.models.Route;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class RoutesActivity extends AppCompatActivity implements RoutesRecyclerAdapter.RouteClickListener {

    //Initialize Variables
    private static final String TAG = "RoutesActivity";
    private RecyclerView routesRecyclerView;
    private RoutesRecyclerAdapter routesAdapter;

    //holds the List of available routes
    public static List<Route> routes = new ArrayList<>();
    public List<Route> routeList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routes_layout);

        //Assigns variable to layout
        routesRecyclerView = findViewById(R.id.routesRecyclerView);

        //Hides the action bar
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        //Retrives the routes data
        try {
            getRoutesData();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Initiates recyclerview
        initRecyclerView();

    }

    //Initiates recyclerview with retrieved data
    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        routesRecyclerView.setLayoutManager(linearLayoutManager);
        routesAdapter = new RoutesRecyclerAdapter(routeList, this);
        RecyclerViewMargin recyclerViewMargin = new RecyclerViewMargin(20, routes.size());
        routesRecyclerView.addItemDecoration(recyclerViewMargin);
        routesRecyclerView.setAdapter(routesAdapter);
    }

    //Method to get data
    public void getRoutesData() throws ExecutionException, InterruptedException {
        DBhelper dBhelper = new DBhelper();
        routeList = dBhelper.getRoutes();

    }


    //Click listener for recyclerview, sends customerID's to next activity as it launches
    @Override
    public void onRouteClick(int position) {
        Intent intent = new Intent(getApplicationContext(), CustomerList.class);
        intent.putExtra("CustomerList", routeList.get(position).getCustomerIDs());
        startActivity(intent);
    }

}

