package com.example.louis.musicplayertest.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    public String id;

    @ColumnInfo
    @NonNull
    public String text;

    public String getId() {
        return id;
    }

    public void setId(String name) {
        this.id = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String name) {
        this.text = name;
    }

}
