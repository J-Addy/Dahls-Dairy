package com.addydevelopments.dahlsdairy.RecyclerViews;

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

public class FrozenProductsAdapter extends RecyclerView.Adapter<FrozenProductsAdapter.FrozenProductViewHolder> {

    private List<Product> frozenProducts;
    private FrozenProductClickListener frozenProductClickListener;
    private int frozen_selected_position = -1;

    public FrozenProductsAdapter(List<Product> frozenProducts, FrozenProductClickListener frozenProductClickListener){
        this.frozenProducts = frozenProducts;
        this.frozenProductClickListener = frozenProductClickListener;
    }




    @NonNull
    @Override
    public FrozenProductsAdapter.FrozenProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.invoice_product_layout, parent, false);
        return new FrozenProductViewHolder(itemView, frozenProductClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FrozenProductViewHolder holder, int position) {
        holder.invoiceProductNameTextView.setText(frozenProducts.get(position).getProductName());
        holder.invoiceProductPriceTextView.setText((frozenProducts.get(position).getProductPriceString() + ""));

        if(frozen_selected_position == position){
            holder.background.setBackgroundColor(Color.parseColor("#FF00FF0A"));
        }
        else{
            holder.background.setBackgroundColor(Color.parseColor("#CDCDCD"));
        }
    }



    @Override
    public int getItemCount() {
        return frozenProducts.size();
    }

    class FrozenProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView invoiceProductNameTextView, invoiceProductPriceTextView;
        FrozenProductClickListener frozenProductClickListener;
        ConstraintLayout background;

        FrozenProductViewHolder(@NonNull View itemView, FrozenProductClickListener frozenProductClickListener) {
            super(itemView);
            invoiceProductNameTextView = itemView.findViewById(R.id.invoiceItemNameTextView);
            invoiceProductPriceTextView = itemView.findViewById(R.id.invoiceItemPriceTextView);
            background = itemView.findViewById(R.id.productBackgroundLayout);
            this.frozenProductClickListener = frozenProductClickListener;

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            frozenProductClickListener.onFrozenProductClick(getAdapterPosition());

            if (frozenProductClickListener != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    frozenProductClickListener.onFrozenProductClick(position);
                    frozen_selected_position = position;
                    notifyDataSetChanged();
                }
            }
        }
    }

    public void unClick(){
        frozen_selected_position = -1;
        notifyDataSetChanged();
    }



    public interface FrozenProductClickListener {
        void onFrozenProductClick(int position);
    }
}
