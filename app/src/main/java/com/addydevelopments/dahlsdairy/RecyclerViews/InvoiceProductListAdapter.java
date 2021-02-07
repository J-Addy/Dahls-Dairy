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

public class InvoiceProductListAdapter extends RecyclerView.Adapter<InvoiceProductListAdapter.ProductViewHolder> {

    private final static String TAG = "InvoiceProductAdapter";

    private List<Product> products;
    private ProductClickListener productClickListener;
    private int invoice_selected_position = -1;

    public InvoiceProductListAdapter (List<Product> products, ProductClickListener productClickListener){
        this.products = products;
        this.productClickListener = productClickListener;
    }



    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.temp_order_layout, parent, false);
        return new ProductViewHolder(itemView, productClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        if(products.get(position).getQuantity() > 0){
            holder.tempOrderQuantityTextView.setText(products.get(position).getQuantity() + "");
        } else {
            holder.tempOrderQuantityTextView.setText(0 + "");
        }
        holder.tempOrderProductNameTextView.setText(products.get(position).getProductName());
        holder.tempOrderPrice.setText(products.get(position).getProductPriceString() + "");
        holder.tempOrderItemTotalTextView.setText(products.get(position).getTotalPrice());

        if(invoice_selected_position == position){
            holder.background.setBackgroundColor(Color.parseColor("#FF00FF0A"));
        }
        else{
            holder.background.setBackgroundColor(Color.parseColor("#CDCDCD"));
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tempOrderQuantityTextView, tempOrderProductNameTextView, tempOrderPrice, tempOrderItemTotalTextView;
        ProductClickListener productClickListener;
        ConstraintLayout background;

        public ProductViewHolder(@NonNull View itemView, ProductClickListener productClickListener) {
            super(itemView);

            tempOrderQuantityTextView = itemView.findViewById(R.id.tempOrderQuantityTextView);
            tempOrderProductNameTextView = itemView.findViewById(R.id.tempOrderProductNameTextView);
            tempOrderPrice = itemView.findViewById(R.id.tempOrderPrice);
            tempOrderItemTotalTextView = itemView.findViewById(R.id.tempOrderItemTotalTextView);
            this.background = itemView.findViewById(R.id.tempOrderBackground);
            this.productClickListener = productClickListener;


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            productClickListener.onProductClick(getAdapterPosition());

            if (productClickListener != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    productClickListener.onProductClick(position);
                    invoice_selected_position = position;
                    notifyDataSetChanged();
                }
            }
        }
    }


    public void unClick(){
        invoice_selected_position = -1;
        notifyDataSetChanged();
    }


    public interface ProductClickListener{
        void onProductClick(int position);
    }


}
