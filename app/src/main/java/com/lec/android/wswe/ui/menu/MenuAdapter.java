package com.lec.android.wswe.ui.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lec.android.wswe.R;
import com.lec.android.wswe.database.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private List<Restaurant> restaurantList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        View itemView = inf.inflate(R.layout.item, parent, false);

        return new ViewHolder(itemView);
    } // end onCreateViewHolder

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);

        holder.tvName.setText(restaurant.getRest_name());
//            if (restaurant.getTelephone() != null) {
//                holder.tvPhone.setText(restaurant.getTelephone());
//            } else {
//                holder.tvPhone.setText("없음");
//            }
        holder.stars.setRating(restaurant.getStar());
    } // end onBindViewHolder

    @Override
    public int getItemCount() {
        return restaurantList.size();
    } // end getItemCount

    public void setRestaurantList(List<Restaurant> restaurants){
        this.restaurantList = restaurants;
        notifyDataSetChanged();
    } // end setRestaurantList()

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPhone;
        RatingBar stars;
        ImageButton btnDelItem, ivPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.etPhone);
            stars = itemView.findViewById(R.id.ratingBar);
            btnDelItem = itemView.findViewById(R.id.btnDelItem);

        } // end ViewHolder()
    } // end ViewHolder
} // end MenuAdapter
