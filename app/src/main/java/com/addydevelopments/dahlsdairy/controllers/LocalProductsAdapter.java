package com.addydevelopments.dahlsdairy.controllers;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.addydevelopments.dahlsdairy.R;
import com.addydevelopments.dahlsdairy.models.Product;

import java.util.List;

public class LocalProductsAdapter extends RecyclerView.Adapter<LocalProductsAdapter.LocalProductViewHolder> {

    private LocalProductClickListener localProductClickListener;
    private List<Product> localProducts;
    public static int local_selected_position = -1;

    public LocalProductsAdapter(List<Product> localProducts, LocalProductClickListener localProductClickListener){
        this.localProducts = localProducts;
        this.localProductClickListener = localProductClickListener;
    }




    @NonNull
    @Override
    public LocalProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.invoice_product_layout, parent, false);
        return new LocalProductViewHolder(itemView, localProductClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalProductViewHolder holder, int position) {
        holder.invoiceProductNameTextView.setText(localProducts.get(position).getProductName());
        holder.invoiceProductPriceTextView.setText((localProducts.get(position).getProductPrice() + ""));

        if(local_selected_position == position){
            holder.background.setBackgroundColor(Color.parseColor("#FF00FF0A"));
        }
        else{
            holder.background.setBackgroundColor(Color.parseColor("#CDCDCD"));
        }
    }


    @Override
    public int getItemCount() {
        return localProducts.size();
    }

    class LocalProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView invoiceProductNameTextView, invoiceProductPriceTextView;
        LocalProductClickListener mlocalProductClickListener;
        ConstraintLayout background;

        LocalProductViewHolder(@NonNull View itemView, LocalProductClickListener dahlsProductClickListener) {
            super(itemView);
            invoiceProductNameTextView = itemView.findViewById(R.id.invoiceItemNameTextView);
            invoiceProductPriceTextView = itemView.findViewById(R.id.invoiceItemPriceTextView);
            this.mlocalProductClickListener = localProductClickListener;
            background = itemView.findViewById(R.id.productBackgroundLayout);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            localProductClickListener.onLocalProductClick(getAdapterPosition());

            if (localProductClickListener != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    localProductClickListener.onLocalProductClick(position);
                    local_selected_position = position;
                    notifyDataSetChanged();
                }
            }
        }
    }

    public void unClick(){
        local_selected_position = -1;
        notifyDataSetChanged();
    }


    public interface LocalProductClickListener {
        void onLocalProductClick(int position);
    }
}
