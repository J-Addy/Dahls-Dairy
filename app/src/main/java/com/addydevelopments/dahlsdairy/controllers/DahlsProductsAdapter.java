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

public class DahlsProductsAdapter extends RecyclerView.Adapter<DahlsProductsAdapter.DahlsProductViewHolder> {

    private List<Product> dahlsProducts;
    private DahlsProductClickListener dahlsProductrClickListener;
    public static int dahls_selected_position = -1;

    public DahlsProductsAdapter(List<Product> dahlsProducts, DahlsProductClickListener dahlsProductrClickListener){
        this.dahlsProducts = dahlsProducts;
        this.dahlsProductrClickListener = dahlsProductrClickListener;
    }




    @NonNull
    @Override
    public DahlsProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.invoice_product_layout, parent, false);
        return new DahlsProductViewHolder(itemView, dahlsProductrClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DahlsProductViewHolder holder, int position) {
        holder.invoiceProductNameTextView.setText(dahlsProducts.get(position).getProductName());
        holder.invoiceProductPriceTextView.setText((dahlsProducts.get(position).getProductPrice() + ""));

        if(dahls_selected_position == position){
            holder.background.setBackgroundColor(Color.parseColor("#FF00FF0A"));

        }
        else{
            holder.background.setBackgroundColor(Color.parseColor("#CDCDCD"));
        }
    }


    @Override
    public int getItemCount() {
        return dahlsProducts.size();
    }

    class DahlsProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView invoiceProductNameTextView, invoiceProductPriceTextView;
        DahlsProductClickListener dahlsProductClickListener;
        ConstraintLayout background;

        DahlsProductViewHolder(@NonNull View itemView, DahlsProductClickListener dahlsProductClickListener) {
            super(itemView);
            invoiceProductNameTextView = itemView.findViewById(R.id.invoiceItemNameTextView);
            invoiceProductPriceTextView = itemView.findViewById(R.id.invoiceItemPriceTextView);
            background = itemView.findViewById(R.id.productBackgroundLayout);
            this.dahlsProductClickListener = dahlsProductClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            dahlsProductClickListener.onDahlsProductClick(getAdapterPosition());
            if (dahlsProductClickListener != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    dahlsProductClickListener.onDahlsProductClick(position);
                    dahls_selected_position = position;
                    notifyDataSetChanged();
                }
            }
        }
    }

    public void unClick(){
        dahls_selected_position = -1;
        notifyDataSetChanged();
    }


    public interface DahlsProductClickListener {
        void onDahlsProductClick(int position);
    }
}
