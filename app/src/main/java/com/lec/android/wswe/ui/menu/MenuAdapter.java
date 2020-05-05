package com.lec.android.wswe.ui.menu;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lec.android.wswe.R;
import com.lec.android.wswe.database.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private List<Restaurant> restaurantList = new ArrayList<>();
    private OnClickDeleteListener deleteListener;
    private OnItemClickListener itemClickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        View itemView = inf.inflate(R.layout.item, parent, false);

        return new ViewHolder(itemView);
    } // end onCreateViewHolder

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Restaurant restaurant = restaurantList.get(position);

        holder.tvName.setText(restaurant.getRest_name());
        if (!restaurant.getTelephone().trim().isEmpty() && restaurant.getTelephone() != null) {
            holder.tvPhone.setText(restaurant.getTelephone());
        } else {
            holder.tvPhone.setText("번호가 없어요");
        }
        holder.stars.setRating(restaurant.getStar());

    } // end onBindViewHolder

    @Override
    public int getItemCount() {
        return restaurantList.size();
    } // end getItemCount

    public void setRestaurantList(List<Restaurant> restaurants) {
        this.restaurantList = restaurants;
        notifyDataSetChanged();
    } // end setRestaurantList()

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPhone;
        RatingBar stars;
        ImageButton btnDelItem, ivPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            stars = itemView.findViewById(R.id.ratingBar);
            btnDelItem = itemView.findViewById(R.id.btnDelItem);

            btnDelItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (deleteListener != null && position != RecyclerView.NO_POSITION) {
                        deleteListener.onClickDelete(restaurantList.get(position));
                    }
                }
            }); // end btnDelItem.setOnClickListener()

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                        Log.d("myLog", "setOnClickListener : " + position);
                    if (itemClickListener != null && position != RecyclerView.NO_POSITION) {
                        itemClickListener.onItemClick(restaurantList.get(position));
                    }
                }
            }); // end itemView.setOnClickListener()
        } // end ViewHolder()
    } // end ViewHolder

    public interface OnClickDeleteListener {
        void onClickDelete(Restaurant restaurant);
    } // end onClickDelete()

    public void setOnClickDeleteListener(OnClickDeleteListener listener) {
        this.deleteListener = listener;
    } // end setOnClickDeleteListener()

    public interface OnItemClickListener {
        void onItemClick(Restaurant restaurant);
    } // end OnItemClickListener()

    public void setItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    } // end setOnItemClickListener()
} // end MenuAdapter
