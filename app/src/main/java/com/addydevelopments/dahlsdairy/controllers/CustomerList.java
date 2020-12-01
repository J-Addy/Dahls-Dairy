package com.addydevelopments.dahlsdairy.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.addydevelopments.dahlsdairy.R;
import com.addydevelopments.dahlsdairy.models.Customer;

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

public class CustomerList extends AppCompatActivity implements RecyclerViewAdapter.RecyclerClickListener {

    private static final String TAG = "CustomersActivity";

    private RecyclerView customerRecyclerView;
    private RecyclerViewAdapter customerListAdapter;

    private List<Customer> customers = new ArrayList<>();
    private List<String> customerNames = new ArrayList<>();
    private List<RecyclerType> customerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        customerRecyclerView = findViewById(R.id.customerRecyclerView);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }


        getCustomerListData();
        initCustomerList();

    }

    private void getCustomerListData() {
        //customers.clear();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<List<Customer>> futures = executorService.submit(new GetCustomerData());
        executorService.shutdown();
        try {
            if (futures.get() != null){
                for (int i = 0; i < futures.get().size(); i ++){
                    customers.add(futures.get().get(i));
                    customerNames.add(futures.get().get(i).getFirstName() + " " + futures.get().get(i).getLastName());
                    Customer customer = (Customer) futures.get().get(i);
                    customerList.add(customer);
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //customerListAdapter.notifyDataSetChanged();
    }

    public void initCustomerList(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        customerRecyclerView.setLayoutManager(linearLayoutManager);
        customerListAdapter = new RecyclerViewAdapter(customerList,  this);
        RecyclerViewMargin recyclerViewMargin = new RecyclerViewMargin(20, customers.size());
        customerRecyclerView.addItemDecoration(recyclerViewMargin);
        customerRecyclerView.setAdapter(customerListAdapter);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getApplicationContext(), CustomerPage.class);
        intent.putExtra("Customer", customers.get(position));
        startActivity(intent);

    }

    public class GetCustomerData implements Callable<List<Customer>> {
        static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        //String url = "jdbc:mysql://172.20.10.2:3306/dahls?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT";
        String url = "jdbc:mysql://192.168.2.8:3306/dahls?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT";
        String user = "jonathonaddy";
        String password = "student";

        List<Customer> customerTemp = new ArrayList<>();

        @Override
        public List<Customer> call() throws Exception {
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
                String sql = "Select * FROM dahls.customers";
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    String custmerID = rs.getString("customerID");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String address = rs.getString("address");
                    String balance = rs.getString("balance");
                    String phoneNumber = rs.getString("customerPhone");
                    String notes = rs.getString("customerNotes");
                    Customer customer = new Customer(custmerID, lastName, firstName, address, balance, phoneNumber, notes);
                    customerTemp.add(customer);
                }

                rs.close();
                stmt.close();
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();

            }return customerTemp;

        }
    }



}