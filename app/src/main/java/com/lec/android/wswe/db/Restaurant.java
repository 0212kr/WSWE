package com.lec.android.wswe.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "RESTAURANT", indices = {@Index(value = "telephone", unique = true)})
public class Restaurant {
    @PrimaryKey(autoGenerate = true)
    private int rest_id;
    @NonNull
    private String name;
    private String telephone;
    private int star;
    private int visited;

    public Restaurant(@NonNull String name, String telephone, int star) {
        this.name = name;
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
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getStar() {
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
