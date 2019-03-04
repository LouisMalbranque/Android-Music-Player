package com.example.louis.musicplayertest;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.louis.musicplayertest.StaticClass.ListSong;
import com.example.louis.musicplayertest.StaticClass.Player;
import com.example.louis.musicplayertest.R;

public class MainCombined extends AppCompatActivity {

    private FragmentManager manager;

    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_combined);
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
}
