package com.example.cse.makeupapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class CosmeticModel {

    @PrimaryKey @NonNull
    private String id;
    private String name;

    public CosmeticModel(@NonNull String id, String name, String image_link, String description) {
        this.id = id;
        this.name = name;
        this.image_link = image_link;
        this.description = description;
    }

    public CosmeticModel(){

    }

    String image_link;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

}
