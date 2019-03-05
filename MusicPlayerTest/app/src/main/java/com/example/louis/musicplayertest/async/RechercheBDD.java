package com.example.louis.musicplayertest.async;

import android.os.AsyncTask;

import com.example.louis.musicplayertest.Database.DataBaseHelper;
import com.example.louis.musicplayertest.interfaces.changementListeUtilisateur;
import com.example.louis.musicplayertest.interfaces.chargementUtilisateurs;
import com.example.louis.musicplayertest.pojo.User;

import java.util.List;

public class RechercheBDD extends AsyncTask<Void,Void,List<User>> {
    private chargementUtilisateurs mListenerR;
    public RechercheBDD(chargementUtilisateurs mListenerR) {this.mListenerR=mListenerR; }

    @Override
    protected List<User> doInBackground(Void... voids) {
        List<User> user= DataBaseHelper.getInstance().getUserDAO().getAll();

        return user;
    }

    @Override
    protected void onPostExecute(List<User> listeUser) {System.out.println(listeUser.get(0).getId());
        System.out.println(listeUser.get(1).getId());
        mListenerR.chargementUtil(listeUser);
    }
}
