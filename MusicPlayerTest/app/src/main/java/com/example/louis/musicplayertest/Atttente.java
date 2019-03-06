package com.example.louis.musicplayertest;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.louis.musicplayertest.Fragment.InscriptionFragment;
import com.example.louis.musicplayertest.async.RechercheMusique;
import com.example.louis.musicplayertest.interfaces.RechercheMus;

import javax.net.ssl.SSLEngineResult;

public class Atttente extends Activity implements RechercheMus {

    private static Context sContext;
    RechercheMus mListener = this;
    ProgressBar progressBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sContext = getApplicationContext();
        setContentView(R.layout.activity_attente);
        progressBar=new ProgressBar(getContext());
        progressBar.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER));


        int color = 0xFF44BBE3;
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);




        RechercheMusique mMusiqueAsyncTask = new RechercheMusique(mListener);
        mMusiqueAsyncTask.execute();






    }




    public static Context getContext() {
        return sContext;
    }

    @Override
    public void finRecherche() {
        ProgressBar progression = (ProgressBar) findViewById(R.id.progressBar2);
        progression.setVisibility(View.GONE);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        Bundle extras = new Bundle();
        extras.putString("log", getApplicationContext().getSharedPreferences("com.example.projet2.log", Context.MODE_PRIVATE).getString("login", null));
        startActivity(intent);

        finish();


    }
}
