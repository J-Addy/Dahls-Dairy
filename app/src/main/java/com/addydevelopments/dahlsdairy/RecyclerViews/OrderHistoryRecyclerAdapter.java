package com.addydevelopments.dahlsdairy.RecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.addydevelopments.dahlsdairy.R;
import com.addydevelopments.dahlsdairy.models.Order;
import com.addydevelopments.dahlsdairy.models.Route;

import java.util.List;

public class OrderHistoryRecyclerAdapter extends RecyclerView.Adapter<OrderHistoryRecyclerAdapter.ViewHolder>  {

    private List<Order> mOrders;
    private OrderHistoryClickListener mOrderHistoryClickListener;



    public OrderHistoryRecyclerAdapter(List<Order> orders, OrderHistoryClickListener orderHistoryListener){
        this.mOrders = orders;
        this.mOrderHistoryClickListener = orderHistoryListener;

    }


    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_history_recycler_view, viewGroup, false);
        return new ViewHolder(view, mOrderHistoryClickListener);
    }

    @Override
    public void onBindViewHolder(OrderHistoryRecyclerAdapter.ViewHolder holder, int position) {
        holder.orderDateTextView.setText(mOrders.get(position).getDate());
        holder.orderNameTextView.setText(mOrders.get(position).getLastName() + ", " + mOrders.get(position).getFirstName());
        holder.orderTotalTextView.setText(mOrders.get(position).getTotal() + "");

    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView orderDateTextView, orderNameTextView, orderTotalTextView;
        OrderHistoryClickListener orderHistoryClickListener;

        public ViewHolder(@NonNull View itemView, OrderHistoryClickListener orderHistoryClickListener) {
            super(itemView);
            orderDateTextView = itemView.findViewById(R.id.orderHistoryDateTextView);
            orderNameTextView = itemView.findViewById(R.id.orderHistoryNameTextView);
            orderTotalTextView = itemView.findViewById(R.id.orderHistoryTotalTextView);
            this.orderHistoryClickListener = orderHistoryClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            orderHistoryClickListener.onOrderClick(getAdapterPosition());
        }
    }

    public interface OrderHistoryClickListener{
        void onOrderClick(int position);
    }

}
