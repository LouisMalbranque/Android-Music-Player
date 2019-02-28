package com.example.louis.musicplayertest.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.example.louis.musicplayertest.pojo.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM User")
    List<User> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> utilisateurs);

    @Delete
    void delete(User utilisateur);
}
