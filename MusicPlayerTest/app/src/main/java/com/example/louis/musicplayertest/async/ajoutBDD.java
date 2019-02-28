package com.example.louis.musicplayertest.async;

import android.os.AsyncTask;


import com.example.louis.musicplayertest.Database.DataBaseHelper;
import com.example.louis.musicplayertest.interfaces.changementListeUtilisateur;
import com.example.louis.musicplayertest.pojo.User;

import java.util.List;

public class ajoutBDD extends AsyncTask<User,Void, Void> {

    private changementListeUtilisateur mListener;

    public ajoutBDD(changementListeUtilisateur mListener){
        this.mListener=mListener;
    }

    @Override
    protected Void doInBackground(User... strings) {
        List<User> user= DataBaseHelper.getInstance().getUserDAO().getAll();
        user.add(strings[0]);
        DataBaseHelper.getInstance().getUserDAO().insertAll(user);
        return null;
    }

    protected void onPostExecute(List<User> Utilisateurs) { mListener.changement(Utilisateurs); }
}