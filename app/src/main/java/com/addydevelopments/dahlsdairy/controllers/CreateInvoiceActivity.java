package com.addydevelopments.dahlsdairy.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.addydevelopments.dahlsdairy.RecyclerViews.DahlsProductsAdapter;
import com.addydevelopments.dahlsdairy.RecyclerViews.FrozenProductsAdapter;
import com.addydevelopments.dahlsdairy.RecyclerViews.InvoiceProductListAdapter;
import com.addydevelopments.dahlsdairy.RecyclerViews.LocalProductsAdapter;
import com.addydevelopments.dahlsdairy.RecyclerViews.RecyclerViewMargin;
import com.addydevelopments.dahlsdairy.models.Customer;
import com.addydevelopments.dahlsdairy.models.DBhelper;
import com.addydevelopments.dahlsdairy.models.Order;
import com.addydevelopments.dahlsdairy.models.Product;
import com.addydevelopments.dahlsdairy.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CreateInvoiceActivity extends AppCompatActivity implements FrozenProductsAdapter.FrozenProductClickListener, InvoiceProductListAdapter.ProductClickListener, DahlsProductsAdapter.DahlsProductClickListener, LocalProductsAdapter.LocalProductClickListener {

    //Initialize variables
    private List<Product> dahlsProducts = new ArrayList<>();
    private List<Product> localProducts = new ArrayList<>();
    private List<Product> frozenProducts = new ArrayList<>();
    private List<Product> orderProducts = new ArrayList<>();
    Product tempProduct;
    Customer customer;

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

        //Hides actionbar
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }


        //Gets Customer Data from Customer List Activity
        Intent i = getIntent();
        customer = i.getParcelableExtra("Customer");
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

        //Initializes on click listeners
        startOnClickButtons();

        //Gets product data
        try {
            populateProductLists();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //initiates recylerviews
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

    //Gets product data and sorts it by type
    public void populateProductLists() throws ExecutionException, InterruptedException {

        List<Product> tempProducts = new ArrayList<>();

        DBhelper dBhelper = new DBhelper();
        tempProducts = dBhelper.getProductJSONS();
        setTempTotal();

        if(tempProducts.size() > 0) {

            for (int i = 0; i < tempProducts.size(); i++) {
                if (tempProducts.get(i).getProductType().equals("Dahls")) {
                    dahlsProducts.add(tempProducts.get(i));
                } else if (tempProducts.get(i).getProductType().equals("Local")) {
                    localProducts.add(tempProducts.get(i));
                } else {
                    frozenProducts.add(tempProducts.get(i));
                }
            }
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

        //Increments selected item quantity
        incrementItemQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((tempProduct != null)){
                    tempProduct.setQuantity(tempProduct.getQuantity() + 1);
                    invoiceItemQuantityTextView.setText(tempProduct.getQuantity()+"");
                }
            }
        });

        //Decrements selected item quanity
        decrementItemQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((tempProduct != null)){
                    //Sets new quantity but prevents order from having a negative quantity of an item
                    if(tempProduct.getQuantity() > 0){
                        tempProduct.setQuantity(tempProduct.getQuantity() - 1);
                        invoiceItemQuantityTextView.setText(tempProduct.getQuantity() + "");
                    }
                }
            }
        });

        //Erases entire order
        clearOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderProducts.clear();
                invoiceProductListAdapter.notifyDataSetChanged();
                setTempTotal();
            }
        });

        //Deletes selected item from order
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

        //Competes the invoice and sends it to the Database
        completeInvoiceButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createOrder();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


    }


    @Override
    public void onProductClick(int position) {
        frozenListAdapter.unClick();
        localProductsAdapter.unClick();
        dahlsListAdapter.unClick();

        //Prepares the product for be added to the list
        tempProduct = orderProducts.get(position);
        invoiceItemName.setText(tempProduct.getProductName());
        invoiceItemQuantityTextView.setText(tempProduct.getQuantity() + "");

    }

    @Override
    public void onDahlsProductClick(int position) {
        //Unhighlights previously highlighted item
        frozenListAdapter.unClick();
        localProductsAdapter.unClick();
        invoiceProductListAdapter.unClick();

        //Prepares the product for be added to the list
        tempProduct = dahlsProducts.get(position);
        invoiceItemName.setText(tempProduct.getProductName());
        invoiceItemQuantityTextView.setText(tempProduct.getQuantity() + "");


    }

    @Override
    public void onLocalProductClick(int position) {
        //Unhighlights previously highlighted item
        frozenListAdapter.unClick();
        dahlsListAdapter.unClick();
        invoiceProductListAdapter.unClick();

        //Prepares the product for be added to the list
        tempProduct = localProducts.get(position);
        invoiceItemName.setText(tempProduct.getProductName());
        invoiceItemQuantityTextView.setText(tempProduct.getQuantity()+"");

    }

    @Override
    public void onFrozenProductClick(int position) {
        //Unhighlights previously highlighted item
        localProductsAdapter.unClick();
        dahlsListAdapter.unClick();
        invoiceProductListAdapter.unClick();

        //Prepares the product for be added to the order
        tempProduct = frozenProducts.get(position);
        invoiceItemName.setText(tempProduct.getProductName());
        invoiceItemQuantityTextView.setText(tempProduct.getQuantity()+"");

    }

    //Checks to see if selected item is already in the order to prevent duplicates
    public boolean checkOrderList() {
        for (Product product : orderProducts) {
            if (tempProduct.getProductName().equals(product.getProductName())) {
                product.setQuantity(tempProduct.getQuantity());
                return true;

            }
        }
            return false;
    }

    //Sets the total of the list
    public void setTempTotal(){
        double quantity = 0;
        for (Product product : orderProducts){
            quantity += product.getTotal();
        }
        invoiceTotalTextView.setText("Total: " + quantity);
    }


    //Sends the order to the database
    public void createOrder() throws ExecutionException, InterruptedException {
        Order order = new Order(customer.getFirstName(), customer.getLastName(), orderProducts);
        order.createInvoice();
    }



}