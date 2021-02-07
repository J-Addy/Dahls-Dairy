package com.addydevelopments.dahlsdairy.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.addydevelopments.dahlsdairy.R;
import com.addydevelopments.dahlsdairy.RecyclerViews.CustomerRecyclerAdapter;
import com.addydevelopments.dahlsdairy.RecyclerViews.OrderHistoryRecyclerAdapter;
import com.addydevelopments.dahlsdairy.RecyclerViews.RecyclerViewMargin;
import com.addydevelopments.dahlsdairy.models.Customer;
import com.addydevelopments.dahlsdairy.models.DBhelper;
import com.addydevelopments.dahlsdairy.models.Order;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class OrderHistory extends AppCompatActivity implements OrderHistoryRecyclerAdapter.OrderHistoryClickListener{

    private RecyclerView orderHistoryRecycler;
    private OrderHistoryRecyclerAdapter orderHistoryRecyclerAdapter;

    private List<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        //Gets customer from Customer Page Activity
        Intent i = getIntent();
        final Customer customer = i.getParcelableExtra("Customer");

        orderHistoryRecycler = findViewById(R.id.orderHistoryRecyclerView);

        try {
            getOrders(customer);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        initOrderList();


    }

    //get ORder History Data
    public void getOrders(Customer customer) throws ExecutionException, InterruptedException {
        DBhelper dBhelper = new DBhelper();
        orders = dBhelper.getOrderHistory(customer);
    }



    //Initiates recyclerview with retrieved data
    public void initOrderList(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        orderHistoryRecycler.setLayoutManager(linearLayoutManager);
        orderHistoryRecyclerAdapter = new OrderHistoryRecyclerAdapter(orders,  this);
        RecyclerViewMargin recyclerViewMargin = new RecyclerViewMargin(20, orders.size());
        orderHistoryRecycler.addItemDecoration(recyclerViewMargin);
        orderHistoryRecycler.setAdapter(orderHistoryRecyclerAdapter);

    }

    @Override
    public void onOrderClick(int position) {

    }
}