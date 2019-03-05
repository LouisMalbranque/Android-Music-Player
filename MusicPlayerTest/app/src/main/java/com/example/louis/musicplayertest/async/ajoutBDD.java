package com.example.louis.musicplayertest.async;

import android.os.AsyncTask;


import com.example.louis.musicplayertest.Database.DataBaseHelper;
import com.example.louis.musicplayertest.interfaces.changementListeUtilisateur;
import com.example.louis.musicplayertest.pojo.User;

import java.util.List;

public class ajoutBDD extends AsyncTask<User,Void, Integer> {

    private changementListeUtilisateur mListener;

    public ajoutBDD(changementListeUtilisateur mListener){
        this.mListener=mListener;
    }

    @Override
    protected Integer doInBackground(User... strings) {
        List<User> user = DataBaseHelper.getInstance().getUserDAO().getAll();
        boolean boolea=false;
        String[] loguser = new String[user.size()];
        for(int i=0; i<loguser.length;i++){
            loguser[i]=user.get(i).getId();
            if(loguser[i].equals((strings[0].getId()))){
                boolea=true;
                System.out.print("OK");
            }
        }
        if (strings != null && boolea==false) {
            user.add(strings[0]);
            DataBaseHelper.getInstance().getUserDAO().insertAll(user);

        return 1;
    }
    else
    {
        return 0;
    }
    }

    @Override
    protected void onPostExecute(Integer bool) {
        System.out.print(bool);
        mListener.changement(bool);
    }
}