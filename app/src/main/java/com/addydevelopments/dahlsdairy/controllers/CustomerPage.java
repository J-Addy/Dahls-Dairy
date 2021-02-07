package com.addydevelopments.dahlsdairy.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.addydevelopments.dahlsdairy.R;
import com.addydevelopments.dahlsdairy.controllers.CreateInvoiceActivity;
import com.addydevelopments.dahlsdairy.models.Customer;

public class CustomerPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_page);

        //Initialize Variables
        TextView customerNameTextView, customerAddressTextView;
        TextView customerBalanceTextView, phoneNumberTextView, notesTextView;
        Button createInvoice, applyPayment, orderHistory;
        ImageView googleMaps;



        Intent i = getIntent();
        final Customer customer = i.getParcelableExtra("Customer");

        //Fills out Page Info
        customerNameTextView = findViewById(R.id.customerNameTextView);
        customerAddressTextView = findViewById(R.id.customerAddressTextView);
        customerBalanceTextView = findViewById(R.id.balanceTextView);
        phoneNumberTextView = findViewById(R.id.phoneNumberTextView);
        notesTextView = findViewById(R.id.customerNotesTextView);

        assert customer != null;
        customerNameTextView.setText(customer.getLastName() + ", " + customer.getFirstName());
        customerAddressTextView.setText(customer.getAddress());
        customerBalanceTextView.setText("Balance: " + customer.getBalance());
        phoneNumberTextView.setText("Phone: " + customer.getPhoneNumber());
        notesTextView.setText(customer.getNotes());

        //Opens Google maps with direction to customer's address
        googleMaps = findViewById(R.id.googleMapsButton);
        googleMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q=an+"+ customer.getAddress()+"\""));
                startActivity(intent);
            }
        });

        //Starts the Create Invoice Activity
        createInvoice = findViewById(R.id.createInvoiceButton);
        createInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateInvoiceActivity.class);
                intent.putExtra("Customer", customer);
                startActivity(intent);
            }
        });

        Button orderHistoryButton = findViewById(R.id.getOrderHistoryButton);
        orderHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderHistory.class);
                intent.putExtra("Customer", customer);
                startActivity(intent);
            }
        });



    }

}
