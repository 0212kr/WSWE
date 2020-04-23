package com.lec.android.wswe.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "RESTAURANT")
public class Restaurant {
    @PrimaryKey(autoGenerate = true)
    private int rest_id;
    @NonNull
    private String rest_name;
    private String telephone;
    private float star;
    private int visited;

    public Restaurant(@NonNull String rest_name, String telephone, float star) {
        this.rest_name = rest_name;
        this.telephone = telephone;
        this.star = star;
    }

    public int getRest_id() {
        return rest_id;
    }

    public void setRest_id(int rest_id) {
        this.rest_id = rest_id;
    }

    @NonNull
    public String getRest_name() {
        return rest_name;
    }

    public void setRest_name(@NonNull String rest_name) {
        this.rest_name = rest_name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        if (telephone == null) {
            this.telephone = "없음";
        } else {
            this.telephone = telephone;
        }
    }

    public float getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getVisited() {
        return visited;
    }

    public void setVisited(int visited) {
        this.visited = visited;
    }
}
