package com.example.louis.musicplayertest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.louis.musicplayertest.Fragment.AttenteFragment;
import com.example.louis.musicplayertest.Fragment.InscriptionFragment;
import com.example.louis.musicplayertest.async.RechercheMusique;

import javax.net.ssl.SSLEngineResult;

public class Atttente extends Activity {
    AttenteFragment attente_frag = new AttenteFragment();
    private static Context sContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sContext = getApplicationContext();
        setContentView(R.layout.activity_attente);

        getFragmentManager().beginTransaction().add(R.id.container, attente_frag).commit();


        RechercheMusique mMusiqueAsyncTask = new RechercheMusique();
        mMusiqueAsyncTask.execute();



            /*Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        Bundle extras = new Bundle();
        extras.putString("log", getApplicationContext().getSharedPreferences("com.example.projet2.log", Context.MODE_PRIVATE).getString("login", null));
        startActivity(intent);

        finish();*/



    }




    public static Context getContext() {
        return sContext;
    }
}
