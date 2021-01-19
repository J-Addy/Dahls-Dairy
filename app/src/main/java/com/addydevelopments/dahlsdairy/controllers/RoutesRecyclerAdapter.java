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

public class RoutesRecyclerAdapter extends RecyclerView.Adapter<RoutesRecyclerAdapter.ViewHolder> {

    private static final String TAG = "RoutesRecyclerAdapter";

    private List<Route> mRouteInfo;
    private RouteClickListener mRouteClickListener;
    Context mContext;


    public RoutesRecyclerAdapter(List<Route> routesInfo, RouteClickListener routeClickListener){
        this.mRouteInfo = routesInfo;
        this.mRouteClickListener = routeClickListener;

    }


    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.routes_recycler_view, viewGroup, false);
        return new ViewHolder(view, mRouteClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.routeInfoHolder.setText(mRouteInfo.get(position).getRouteName());

    }

    @Override
    public int getItemCount() {
        return mRouteInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView routeInfoHolder;
        RouteClickListener routeClickListener;

        public ViewHolder(@NonNull View itemView, RouteClickListener routeClickListener) {
            super(itemView);
            routeInfoHolder = itemView.findViewById(R.id.routeNameText);
            this.routeClickListener = routeClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            routeClickListener.onRouteClick(getAdapterPosition());
        }
    }

    public interface RouteClickListener{
        void onRouteClick(int position);
    }


}