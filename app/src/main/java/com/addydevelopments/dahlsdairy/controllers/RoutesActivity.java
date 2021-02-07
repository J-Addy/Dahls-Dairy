package com.addydevelopments.dahlsdairy.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.addydevelopments.dahlsdairy.R;
import com.addydevelopments.dahlsdairy.RecyclerViews.RecyclerViewMargin;
import com.addydevelopments.dahlsdairy.RecyclerViews.RoutesRecyclerAdapter;
import com.addydevelopments.dahlsdairy.models.DBhelper;
import com.addydevelopments.dahlsdairy.models.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;


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
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException e) {
            System.out.println(e);
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
        intent.putExtra("Route", routeList.get(position));
        startActivity(intent);
    }

}

