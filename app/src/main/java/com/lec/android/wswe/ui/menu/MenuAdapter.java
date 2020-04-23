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
    List<Restaurant> restaurantList = new ArrayList<>();

    static MenuAdapter adapter;

    public MenuAdapter() {
        this.adapter = this;
    }

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
        holder.setItem(restaurant);
    } // end onBindViewHolder

    @Override
    public int getItemCount() {
        return restaurantList.size();
    } // end getItemCount

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

        public void setItem(Restaurant restaurant) {
            tvName.setText(restaurant.getRest_name());
//            if (restaurant.getTelephone() != null) {
//                tvPhone.setText(restaurant.getTelephone());
//            } else {
//                tvPhone.setText("없음");
//            }
            stars.setRating(restaurant.getStar());
        }
    } // end ViewHolder

    public void addRest(Restaurant restaurant) {
        restaurantList.add(restaurant);
    }

    public void addItem(int position, Restaurant restaurant) {
        restaurantList.add(position, restaurant);
    }

    public void setItems(ArrayList<Restaurant> restaurants) {
        this.restaurantList = restaurants;
    }

    public Restaurant getItem(int position) {
        return restaurantList.get(position);
    }

    public void setItem(int position, Restaurant restaurant) {
        restaurantList.set(position, restaurant);
    }

    public void removeItem(int position) {
        restaurantList.remove(position);
    }

} // end MenuAdapter
