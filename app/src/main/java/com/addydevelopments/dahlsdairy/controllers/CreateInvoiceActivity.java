package com.addydevelopments.dahlsdairy.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.addydevelopments.dahlsdairy.models.Customer;
import com.addydevelopments.dahlsdairy.models.Product;
import com.addydevelopments.dahlsdairy.R;

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

public class CreateInvoiceActivity extends AppCompatActivity implements FrozenProductsAdapter.FrozenProductClickListener, InvoiceProductListAdapter.ProductClickListener, DahlsProductsAdapter.DahlsProductClickListener, LocalProductsAdapter.LocalProductClickListener {

    private List<Product> dahlsProducts = new ArrayList<>();
    private List<Product> localProducts = new ArrayList<>();
    private List<Product> frozenProducts = new ArrayList<>();
    private List<Product> orderProducts = new ArrayList<>();
    Product tempProduct;

    //Sets RecyclerViews/Adapters
    RecyclerView dahlsProductsRecycler;
    RecyclerView localProductsRecycler;
    RecyclerView frozenProductsRecycler;
    DahlsProductsAdapter dahlsListAdapter;
    LocalProductsAdapter localProductsAdapter;
    FrozenProductsAdapter frozenListAdapter;
    RecyclerView invoicePreviewRecycler;
    InvoiceProductListAdapter invoiceProductListAdapter;

    //Initiates textview/buttons
    TextView invoiceCustomerNameTextView;
    TextView invoiceItemQuantityTextView;
    TextView invoiceItemName;
    TextView invoiceTotalTextView;
    Button addItemToOrder, incrementItemQuantity, decrementItemQuantity, clearOrder, deleteItem,
            completeInvoiceButon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invoice);

        //Gets Customer Data from Customer List Activity
        Intent i = getIntent();
        final Customer customer = i.getParcelableExtra("Customer");
        invoiceCustomerNameTextView = findViewById(R.id.invoiceNameTextView);
        invoiceCustomerNameTextView.setText(customer.getLastName() + ", " + customer.getFirstName());

        //Initialize RecyclerViews
        dahlsProductsRecycler = findViewById(R.id.dahlsProductsRecyclerView);
        localProductsRecycler = findViewById(R.id.localProductsRecyclerView);
        frozenProductsRecycler = findViewById(R.id.frozenProductsRecyclerView);
        invoicePreviewRecycler = findViewById(R.id.tempOrderRecyclerView);

        //Initialize Add Item Area
        invoiceTotalTextView = findViewById(R.id.tempTotalTextView);
        addItemToOrder = findViewById(R.id.addItemToOrderButton);
        incrementItemQuantity = findViewById(R.id.incrementQuantityButton);
        decrementItemQuantity = findViewById(R.id.decrementQuantityButton);
        invoiceItemQuantityTextView = findViewById(R.id.itemQuantityTextView);
        invoiceItemName = findViewById(R.id.invoiceItemNameTextView);
        clearOrder = findViewById(R.id.clearOrderButton);
        deleteItem = findViewById(R.id.invoiceDeleteItem);
        completeInvoiceButon = findViewById(R.id.completeInvoice);


        startOnClickButtons();
        populateProductLists();
        initProductRecyclerViews();



    }

    private void initProductRecyclerViews() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        dahlsProductsRecycler.setLayoutManager(linearLayoutManager);
        dahlsProductsRecycler.hasFixedSize();
        dahlsListAdapter = new DahlsProductsAdapter(dahlsProducts, this);
        RecyclerViewMargin recyclerViewMargin = new RecyclerViewMargin(20, dahlsProducts.size());
        dahlsProductsRecycler.addItemDecoration(recyclerViewMargin);
        dahlsProductsRecycler.setAdapter(dahlsListAdapter);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        localProductsRecycler.setLayoutManager(linearLayoutManager2);
        localProductsRecycler.hasFixedSize();
        localProductsAdapter = new LocalProductsAdapter(localProducts, this);
        RecyclerViewMargin recyclerViewMargin2 = new RecyclerViewMargin(20, localProducts.size());
        localProductsRecycler.addItemDecoration(recyclerViewMargin2);
        localProductsRecycler.setAdapter(localProductsAdapter);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        frozenProductsRecycler.setLayoutManager(linearLayoutManager3);
        frozenProductsRecycler.hasFixedSize();
        frozenListAdapter = new FrozenProductsAdapter(frozenProducts, this);
        RecyclerViewMargin recyclerViewMargin3 = new RecyclerViewMargin(20, frozenProducts.size());
        frozenProductsRecycler.addItemDecoration(recyclerViewMargin3);
        frozenProductsRecycler.setAdapter(frozenListAdapter);

        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(this);
        invoicePreviewRecycler.setLayoutManager(linearLayoutManager4);
        invoicePreviewRecycler.hasFixedSize();
        invoiceProductListAdapter = new InvoiceProductListAdapter(orderProducts, this);
        RecyclerViewMargin recyclerViewMargin4 = new RecyclerViewMargin(20, orderProducts.size());
        invoicePreviewRecycler.addItemDecoration(recyclerViewMargin4);
        invoicePreviewRecycler.setAdapter(invoiceProductListAdapter);

    }


    public void populateProductLists(){

        //Starts a thread to get product data
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<List<Product>> futures = executorService.submit(new GetProductData());
        executorService.shutdown();

        try{
            System.out.println("futures size: " + futures.get().size());
            if(futures.get() != null){
                for (int i = 0; i < futures.get().size(); i ++){

                    if(futures.get().get(i).getProductType().equals("Dahls")){
                        dahlsProducts.add(futures.get().get(i));
                    }
                    else if (futures.get().get(i).getProductType().equals("Local")){
                        localProducts.add(futures.get().get(i));
                    }
                    else {
                        frozenProducts.add(futures.get().get(i));

                    }
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    public void startOnClickButtons(){
        addItemToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tempProduct != null){

                    if (checkOrderList() == false){
                        orderProducts.add(tempProduct);
                        invoiceProductListAdapter.notifyDataSetChanged();
                    }
                    else {
                        invoiceProductListAdapter.notifyDataSetChanged();
                    }
                    setTempTotal();

                }
            }
        });

        incrementItemQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((tempProduct != null)){
                    tempProduct.setQuantity(tempProduct.getQuantity() + 1);
                    invoiceItemQuantityTextView.setText(tempProduct.getQuantity()+"");
                }
            }
        });

        decrementItemQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((tempProduct != null)){
                    tempProduct.setQuantity(tempProduct.getQuantity() - 1);
                    invoiceItemQuantityTextView.setText(tempProduct.getQuantity()+"");
                }
            }
        });

        clearOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderProducts.clear();
                invoiceProductListAdapter.notifyDataSetChanged();
                setTempTotal();
            }
        });

        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < orderProducts.size(); i++) {
                    if (tempProduct.getProductName().equals(orderProducts.get(i).getProductName())){
                        orderProducts.remove(i);
                        invoiceProductListAdapter.notifyDataSetChanged();
                        setTempTotal();
                    }
                }
            }
        });

        completeInvoiceButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


    @Override
    public void onProductClick(int position) {
        frozenListAdapter.unClick();
        localProductsAdapter.unClick();
        dahlsListAdapter.unClick();

        tempProduct = orderProducts.get(position);
        invoiceItemName.setText(tempProduct.getProductName());
        invoiceItemQuantityTextView.setText(tempProduct.getQuantity() + "");

    }

    @Override
    public void onDahlsProductClick(int position) {
        frozenListAdapter.unClick();
        localProductsAdapter.unClick();
        invoiceProductListAdapter.unClick();

        tempProduct = dahlsProducts.get(position);
        invoiceItemName.setText(tempProduct.getProductName());
        invoiceItemQuantityTextView.setText(tempProduct.getQuantity() + "");



    }

    @Override
    public void onLocalProductClick(int position) {
        frozenListAdapter.unClick();
        dahlsListAdapter.unClick();
        invoiceProductListAdapter.unClick();

        tempProduct = localProducts.get(position);
        invoiceItemName.setText(tempProduct.getProductName());
        invoiceItemQuantityTextView.setText(tempProduct.getQuantity()+"");


    }

    @Override
    public void onFrozenProductClick(int position) {
        localProductsAdapter.unClick();
        dahlsListAdapter.unClick();
        invoiceProductListAdapter.unClick();

        tempProduct = frozenProducts.get(position);
        invoiceItemName.setText(tempProduct.getProductName());
        invoiceItemQuantityTextView.setText(tempProduct.getQuantity()+"");

    }

    public boolean checkOrderList() {
        for (Product product : orderProducts) {
            if (tempProduct.getProductName().equals(product.getProductName())) {
                product.setQuantity(tempProduct.getQuantity());
                return true;

            }
        }
            return false;
    }

    public void setTempTotal(){
        double quantity = 0;
        for (Product product : orderProducts){
            quantity += product.getTotal();
        }
        invoiceTotalTextView.setText("Total: " + quantity);
    }




    public class GetProductData implements Callable<List<Product>> {

        static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        //String url = "jdbc:mysql://172.20.10.2:3306/dahls?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT";
        String url = "jdbc:mysql://192.168.2.8:3306/dahls?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT";
        String user = "jonathonaddy";
        String password = "student";

        List<Product> productTemp = new ArrayList<>();

        @Override
        public List<Product> call() throws Exception {
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
                String sql = "Select * FROM dahls.Product";
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    int productID = rs.getInt("productID");
                    String productName = rs.getString("productName");
                    Double productPrice = rs.getDouble("productPrice");
                    String productType = rs.getString("productType");
                    Product product = new Product(productID, productName, productPrice, productType);
                    product.setQuantity(1);
                    productTemp.add(product);
                }

                rs.close();
                stmt.close();
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();

            }return productTemp;

        }
    }


}