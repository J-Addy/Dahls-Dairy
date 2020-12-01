package com.addydevelopments.dahlsdairy.controllers;

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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "RoutesRecyclerAdapter";


    private List<RecyclerType> mDataSet;
    private RecyclerClickListener mRecyclerClickListener;


    public RecyclerViewAdapter(List<RecyclerType> dataSet, RecyclerClickListener recyclerClickListener) {
        this.mDataSet = dataSet;
        this.mRecyclerClickListener = recyclerClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView;
        switch (viewType) {
            case RecyclerType.TYPE_ROUTE:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.routes_recycler_view, viewGroup, false);
                return new RouteViewHolder(itemView, mRecyclerClickListener);
            case RecyclerType.TYPE_CUSTOMER:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.routes_recycler_view, viewGroup, false);
                return new CustomerViewHolder(itemView, mRecyclerClickListener);
                /*
            case RecyclerType.TYPE_PRODUCT:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.invoice_product_layout, viewGroup, false);
                return new ProductViewHolder(itemView, mRecyclerClickListener);

                 */
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case RecyclerType.TYPE_ROUTE:
                ((RouteViewHolder) holder).bindView(position);
                break;
            case RecyclerType.TYPE_CUSTOMER:
                ((CustomerViewHolder) holder).bindView(position);
                break;/*
            case RecyclerType.TYPE_PRODUCT:
                ((ProductViewHolder) holder).bindView(position);
                */

        }
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSet.get(position).getType();
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();

    }

    class RouteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView routeInfoHolder;
        RecyclerClickListener recyclerClickListener;

        public RouteViewHolder(@NonNull View itemView, RecyclerClickListener recyclerClickListener) {
            super(itemView);
            routeInfoHolder = itemView.findViewById(R.id.routeNameText);
            this.recyclerClickListener = recyclerClickListener;

            itemView.setOnClickListener(this);
        }

        void bindView(int position) {
            Route route = (Route) mDataSet.get(position);
            routeInfoHolder.setText(route.getRouteName());
        }

        @Override
        public void onClick(View v) {
            recyclerClickListener.onClick(getAdapterPosition());
        }
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView routeInfoHolder;
        RecyclerClickListener recyclerClickListener;

        public CustomerViewHolder(@NonNull View itemView, RecyclerClickListener recyclerClickListener) {
            super(itemView);
            routeInfoHolder = itemView.findViewById(R.id.routeNameText);
            this.recyclerClickListener = recyclerClickListener;

            itemView.setOnClickListener(this);
        }

        void bindView(int position) {
            Customer customer = (Customer) mDataSet.get(position);
            routeInfoHolder.setText(customer.getLastName() + ", " + customer.getFirstName());
        }

        @Override
        public void onClick(View v) {
            recyclerClickListener.onClick(getAdapterPosition());
        }
    }


    /*

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView invoiceProductNameTextView, invoiceProductPriceTextView;
        RecyclerClickListener recyclerClickListener;

        ProductViewHolder(@NonNull View itemView, RecyclerClickListener recyclerClickListener) {
            super(itemView);
            invoiceProductNameTextView = itemView.findViewById(R.id.invoiceItemNameTextView);
            invoiceProductPriceTextView = itemView.findViewById(R.id.invoiceItemPriceTextView);
            this.recyclerClickListener = recyclerClickListener;

            itemView.setOnClickListener(this);
        }

        void bindView(int position){
            Product product = (Product) mDataSet.get(position);
            invoiceProductNameTextView.setText(product.getProductName());
            invoiceProductPriceTextView.setText((product.getProductPrice() +""));
        }

        @Override
        public void onClick(View v) {
            recyclerClickListener.onClick(getAdapterPosition());
        }
    }


    class TempOrderLayoutHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tempOrderQuantityTextView, tempOrderProductNameTextView, tempOrderPrice, tempOrderItemTotalTextView;
        RecyclerClickListener recyclerClickListener;

        TempOrderLayoutHolder(@NonNull View itemView, RecyclerClickListener recyclerClickListener) {
            super(itemView);
            tempOrderQuantityTextView = itemView.findViewById(R.id.tempOrderQuantityTextView);
            tempOrderProductNameTextView = itemView.findViewById(R.id.tempOrderProductNameTextView);
            tempOrderPrice = itemView.findViewById(R.id.tempOrderPrice);
            tempOrderItemTotalTextView = itemView.findViewById(R.id.tempOrderItemTotalTextView);
            this.recyclerClickListener = recyclerClickListener;
            }

                    @Override
                    public void onClick(View v) {
                        recyclerClickListener.onClick(getAdapterPosition());
                    }
    }
    */

            public interface RecyclerClickListener {
                void onClick(int position);
            }
        }
