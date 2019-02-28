package com.example.louis.musicplayertest.Database;

import android.arch.persistence.room.Room;

import com.example.louis.musicplayertest.DAO.UserDAO;
import com.example.louis.musicplayertest.LoginActivity;


public class DataBaseHelper {
    static DataBaseHelper instance=null;
    private final UtilisateurDB db;

    public static DataBaseHelper getInstance(){
        if(instance==null){
            instance=new DataBaseHelper();
        }
        return instance;
    }

    public UserDAO getUserDAO(){
        return db.userDAO();
    }

    public DataBaseHelper(){
        db = Room.databaseBuilder(LoginActivity.getContext(),UtilisateurDB.class,"ma_bdd_utilisateur.db").build();

    }
}