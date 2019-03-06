package com.example.louis.musicplayertest;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.louis.musicplayertest.StaticClass.ListSong;
import com.example.louis.musicplayertest.StaticClass.Player;
import com.example.louis.musicplayertest.R;

public class MainCombined extends AppCompatActivity {

    private FragmentManager manager;
    private static Context sContext;
    private FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_combined);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sContext=getContext();

        manager = getFragmentManager();

    }

    @Override
    protected void onPause() {
        super.onPause();

        transaction = manager.beginTransaction();
        transaction.remove(Player.getInstance());
        transaction.remove(ListSong.getInstance());
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        transaction = manager.beginTransaction();

        transaction.add(R.id.fragment_player, Player.getInstance());
        transaction.commit();

        transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_list_song, ListSong.getInstance());
        transaction.commit();
    }

    public static Context getContext() {
        return sContext;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.Déconnexion){
            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
            Player.getInstance().pause();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}
