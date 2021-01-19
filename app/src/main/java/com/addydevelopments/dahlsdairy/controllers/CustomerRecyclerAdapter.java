package com.addydevelopments.dahlsdairy.controllers;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.addydevelopments.dahlsdairy.R;
import com.addydevelopments.dahlsdairy.models.Customer;
import com.addydevelopments.dahlsdairy.models.Route;

import java.util.List;

public class CustomerRecyclerAdapter extends RecyclerView.Adapter<CustomerRecyclerAdapter.ViewHolder> {

    private static final String TAG = "CustomerRecyclerAdapter";

    private List<Customer> mCustomerInfo;
    private CustomerClickListener mCustomerClickListener;
    Context mContext;


    public CustomerRecyclerAdapter(List<Customer> customerInfo, CustomerClickListener customerClickListener){
        this.mCustomerInfo = customerInfo;
        this.mCustomerClickListener = customerClickListener;

    }


    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.routes_recycler_view, viewGroup, false);
        return new ViewHolder(view, mCustomerClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.routeInfoHolder.setText(mCustomerInfo.get(position).getLastName());

    }

    @Override
    public int getItemCount() {
        return mCustomerInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView routeInfoHolder;
        CustomerClickListener customerClickListener;

        public ViewHolder(@NonNull View itemView, CustomerClickListener customerClickListener) {
            super(itemView);
            routeInfoHolder = itemView.findViewById(R.id.routeNameText);
            this.customerClickListener = customerClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            customerClickListener.onCustomerClick(getAdapterPosition());
        }
    }

    public interface CustomerClickListener{
        void onCustomerClick(int position);
    }


}
