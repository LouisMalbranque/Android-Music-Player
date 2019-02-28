package com.example.louis.musicplayertest.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.louis.musicplayertest.DAO.UserDAO;
import com.example.louis.musicplayertest.pojo.User;


@Database(entities = {User.class}, version=1)
public abstract class UtilisateurDB extends RoomDatabase {
    public abstract UserDAO userDAO();
}