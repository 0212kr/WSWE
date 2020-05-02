package com.lec.android.wswe.ui.random;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lec.android.wswe.R;
import com.lec.android.wswe.database.Restaurant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.sort;

public class RandomAdapter extends RecyclerView.Adapter<RandomAdapter.ViewHolder> {
    private List<Restaurant> restaurantList = new ArrayList<>();
    private List<Restaurant> randomList = new ArrayList<>();
    private int MAX_LISTS = 0;
    private static final int MAX_SHUFFLE = 100;
    private int counter;
    private Context context;
    private View view;

    public RandomAdapter(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
    } // end onCreateViewHolder()

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Restaurant restaurant = randomList.get(position);

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
        return randomList.size();
    } // end getItemCount()

    public void setRandomList(List<Restaurant> restaurants) {
        this.restaurantList = restaurants;

        ArrayList<Restaurant> temp_list = new ArrayList<>(restaurantList);

        // initialize MAX_LISTS
        if (temp_list.size() > 10) {
            MAX_LISTS = 10;
        } else {
            MAX_LISTS = temp_list.size();
        }

        // set randomNumber
        for (Restaurant r : temp_list) {
            r.setRandomNum((int) ((Math.random() * 100) * (r.getStar() * 0.2) - r.getVisited()));
        }

        sort(temp_list, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant o1, Restaurant o2) {
                if (o1.getRandomNum() > o2.getRandomNum()) return -1;
                if (o1.getRandomNum() < o2.getRandomNum()) return 1;
                return 0;
            }
        });

        for (int i = 0; i < MAX_LISTS; i++) {
            randomList.add(i, temp_list.get(i));
        }

        notifyDataSetChanged();
    } // end setRestaurantList()

    public boolean randomStart() {
        if (randomList.size() < 2){
            Toast.makeText(context, "식당 정보가 없거나 랜덤을 돌릴 만큼 존재하지 않습니다.\n식당 정보를 추가하거나 다시 시도해 주세요.", Toast.LENGTH_LONG).show();
            return false;
        }else {
            final Handler handler = new Handler();
            Thread randomThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (counter < MAX_SHUFFLE) {
                                counter++;
                                Restaurant temp = randomList.get(0);
                                randomList.remove(0);
                                randomList.add(temp);
                                notifyDataSetChanged();
                                handler.postDelayed(this, 300);
                            } else {
                                counter = 0;
                                randomEnd();
                            }
                        }
                    });
                }
            });
            randomThread.setDaemon(true);
            randomThread.start();
            return true;
        }
    } // end randomStart()

    public void randomStop() {
        counter = MAX_SHUFFLE;
    }

    public void randomEnd() {
        Button randomStart = view.findViewById(R.id.randStart);
        Button randomStop = view.findViewById(R.id.randStop);
        randomStart.setVisibility(View.VISIBLE);
        randomStop.setVisibility(View.INVISIBLE);
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
