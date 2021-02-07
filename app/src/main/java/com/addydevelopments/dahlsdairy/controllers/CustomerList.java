package com.addydevelopments.dahlsdairy.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.addydevelopments.dahlsdairy.R;
import com.addydevelopments.dahlsdairy.RecyclerViews.CustomerRecyclerAdapter;
import com.addydevelopments.dahlsdairy.RecyclerViews.RecyclerViewMargin;
import com.addydevelopments.dahlsdairy.models.Customer;
import com.addydevelopments.dahlsdairy.models.DBhelper;
import com.addydevelopments.dahlsdairy.models.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CustomerList extends AppCompatActivity implements CustomerRecyclerAdapter.CustomerClickListener {

    //Initialize Variables
    private RecyclerView customerRecyclerView;
    private CustomerRecyclerAdapter customerListAdapter;

    private List<Customer> customers = new ArrayList<>();
    private String customerIDS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Initializes layouts
        setContentView(R.layout.activity_customer_list);
        customerRecyclerView = findViewById(R.id.customerRecyclerView);

        //Hides action bar
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
            System.out.print(e);
        }

        Button routeOptimizerButton = findViewById(R.id.optimizeRouteButton);

        //Gets customer info from the routes activity if it exists
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            customerIDS = extras.getString("CustomerList");
        } else{

            //Hides route optimization button
            routeOptimizerButton.setVisibility(View.INVISIBLE);
        }

        //If customerIDS is null, this activity was launched for the main activity
        if(customerIDS == null) {
            try {
                getCustomerListData();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            initCustomerList();
            //If customerIDS is not null, it was launched from the routes activity, only selectively populate recyclerview
        } else {
            DBhelper dBhelper = new DBhelper();
            try {
                customers = dBhelper.getCustomerJSONSByName(customerIDS);
                initCustomerList();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        routeOptimizerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert extras != null;
                Route route = extras.getParcelable("Route");
                DBhelper dBhelper = new DBhelper();
                try {
                    dBhelper.optimizeRoute(route);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                customerListAdapter.notifyDataSetChanged();

            }
        });






    }

    //Retrieve customer list data
    private void getCustomerListData() throws ExecutionException, InterruptedException {

        DBhelper dBhelper = new DBhelper();
        customers = dBhelper.getCustomerJSONS();


    }

    //Initiates recyclerview with retrieved data
    public void initCustomerList(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        customerRecyclerView.setLayoutManager(linearLayoutManager);
        customerListAdapter = new CustomerRecyclerAdapter(customers,  this);
        RecyclerViewMargin recyclerViewMargin = new RecyclerViewMargin(20, customers.size());
        customerRecyclerView.addItemDecoration(recyclerViewMargin);
        customerRecyclerView.setAdapter(customerListAdapter);

    }


    //Click listener that sends customer object to customerpage activity
    @Override
    public void onCustomerClick(int position) {
        Intent intent = new Intent(getApplicationContext(), CustomerPage.class);
        intent.putExtra("Customer", customers.get(position));
        startActivity(intent);
    }
}