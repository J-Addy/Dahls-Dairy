package com.addydevelopments.dahlsdairy.controllers;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.addydevelopments.dahlsdairy.R;
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


public class RoutesActivity extends AppCompatActivity implements RecyclerViewAdapter.RecyclerClickListener {

    private static final String TAG = "RoutesActivity";
    private RecyclerView routesRecyclerView;

    private RecyclerViewAdapter routesAdapter;

    //holds the List of available routes
    public static List<Route> routes = new ArrayList<>();
    public static List<String> routeInfo = new ArrayList<>();
    public List<RecyclerType> routeList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routes_layout);
        routesRecyclerView = findViewById(R.id.routesRecyclerView);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        getRoutesData();
        initRecyclerView();

    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        routesRecyclerView.setLayoutManager(linearLayoutManager);
        routesAdapter = new RecyclerViewAdapter(routeList, this);
        RecyclerViewMargin recyclerViewMargin = new RecyclerViewMargin(20, routes.size());
        routesRecyclerView.addItemDecoration(recyclerViewMargin);
        routesRecyclerView.setAdapter(routesAdapter);
    }

    public void getRoutesData(){

        ExecutorService executor = Executors.newSingleThreadExecutor();
        RoutesWorkerClass routeWorker = new RoutesWorkerClass();
        Future<List<Route>> futures = executor.submit(routeWorker);
        executor.shutdown();
        try {
            if (futures.get() != null){
                for (int i = 0; i < futures.get().size(); i ++){
                    routes.add(futures.get().get(i));
                    routeInfo.add(futures.get().get(i).getRouteName());
                    Route route = (Route) futures.get().get(i);
                    routeList.add(route);
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(int position) {
        Log.d(TAG, "Route clicked!! : " + position  );
        routeInfo.clear();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<List<String>> result = executorService.submit(new getRouteCustomerNames(routes.get(position).getCustomerIDsArray()));
        executorService.shutdown();

        try{
            if (result.get() != null){

                for (int i = 0; i < result.get().size(); i++){
                    routeInfo.add(result.get().get(i));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        routesAdapter.notifyDataSetChanged();


    }


    private class RoutesWorkerClass implements Callable<List<Route>> {

        static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        //String url = "jdbc:mysql://172.20.10.2:3306/dahls?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT";
        String url = "jdbc:mysql://192.168.2.8:3306/dahls?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT";
        String user = "jonathonaddy";
        String password = "student";

        List<Route> routesTemp = new ArrayList<>();

        @Override
        public List<Route> call() throws Exception {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                String sql = "Select * FROM dahls.routes";
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    String routeID = rs.getString("routeID");
                    String routeName = rs.getString("routeName");
                    String routeCustomerIDs = rs.getString("routeCustomer");
                    Route route = new Route(routeID, routeName, routeCustomerIDs);
                    routesTemp.add(route);
                }

                rs.close();
                stmt.close();
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }return routesTemp;

        }
    }

    public class getRouteCustomerNames implements Callable<List<String>>{

        int[] numbers;

        public getRouteCustomerNames(int...numbers){
            this.numbers = numbers == null ? new int[0]: numbers;
        }

        static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        //String url = "jdbc:mysql://172.20.10.2:3306/dahls?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT";
        String url = "jdbc:mysql://192.168.2.8:3306/dahls?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT";
        String user = "jonathonaddy";
        String password = "student";

        List<String> routeCustomerNames = new ArrayList<>();

        @Override
        public List<String> call() throws Exception {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            for(int i = 0; i < numbers.length; i ++){

                try {
                    Connection conn = DriverManager.getConnection(url, user, password);
                    Statement stmt = conn.createStatement();
                    String sql = "Select first_name, last_name FROM dahls.customers where customerID = " + numbers[i];
                    ResultSet rs = stmt.executeQuery(sql);

                    while (rs.next()) {
                        String firstName  = rs.getString("first_name");
                        String lastName = rs.getString("last_name");
                        routeCustomerNames.add(firstName + " " + lastName);


                    }

                    rs.close();
                    stmt.close();
                    conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            }return routeCustomerNames;

        }
    }

}

