package com.lec.android.wswe.ui.random;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.lec.android.wswe.R;
import com.lec.android.wswe.database.Restaurant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.sort;

public class RandomAdapter extends RecyclerView.Adapter<RandomAdapter.ViewHolder> {
    private List<Restaurant> list = new ArrayList<>();
    private List<Restaurant> sub_list = new ArrayList<>();
    private int counter;
    private Context context;
    private ViewHolder holder;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
    } // end onCreateViewHolder()

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Restaurant restaurant = list.get(position);
        this.holder = holder;

        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation_ing));

        holder.tvName.setText(restaurant.getRest_name());
        if (!restaurant.getTelephone().trim().isEmpty() && restaurant.getTelephone() != null) {
            holder.tvPhone.setText(restaurant.getTelephone());
        } else {
            holder.tvPhone.setText("없음");
        }
        holder.stars.setRating(restaurant.getStar());
        holder.btnDelItem.setVisibility(View.INVISIBLE);
    } // end onBindViewHolder()

    @Override
    public int getItemCount() {
        return list.size();
    } // end getItemCount()

    public void setRandomList(List<Restaurant> restaurants) {
        this.list = restaurants;
        Log.d("myLog", "size : " + list.size());
        for (Restaurant r : list) {
            r.setRandomNum((int) ((Math.random() * 100) * (r.getStar() * 0.2) - r.getVisited()));
            Log.d("myLog", r.getRest_id() + " : " + r.getRandomNum());
        }

        Log.d("myLog", "---------random Success------");
        sort(list, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant o1, Restaurant o2) {
                if (o1.getRandomNum() > o2.getRandomNum()) return -1;
                if (o1.getRandomNum() < o2.getRandomNum()) return 1;
                return 0;
            }
        });

        for (Restaurant r : list) {
            Log.d("myLog", r.getRest_id() + " : " + r.getRandomNum());
        }

        Log.d("myLog", "---------sort Success------");
        if (list.size() > 6) {
            for (int i = 0; i < 5; i++) {
                sub_list.add(i, list.get(i));
            }
            list.removeAll(list);
        }

        list.addAll(sub_list);
        Log.d("myLog", "---------init Success------");
    } // end setRestaurantList()

    public void randomStart(){
        final Handler handler = new Handler();
        Thread randomThread = new Thread(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (counter < 100) {
                            counter++;
                            Restaurant temp = list.get(0);
                            list.remove(0);
                            list.add(temp);
                            notifyDataSetChanged();
                            Log.d("myLog", "setRandomList: " + counter);
                            handler.postDelayed(this, 200);
                        } else {
                            holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));
                            counter = 0;
                        }
                    }
                }, 200);
            }
        });
        randomThread.setDaemon(true);
        randomThread.start();
    } // end randomStart()

    public void randomStop() {
        counter = 100;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone;
        RatingBar stars;
        ImageButton btnDelItem, ivPhoto;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.item_cardView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            stars = itemView.findViewById(R.id.ratingBar);
            btnDelItem = itemView.findViewById(R.id.btnDelItem);
        } // end ViewHolder(View)
    } // end ViewHolder
} // end Adapter
