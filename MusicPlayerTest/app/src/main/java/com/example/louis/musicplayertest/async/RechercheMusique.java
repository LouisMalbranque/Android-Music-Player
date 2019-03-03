package com.example.louis.musicplayertest.async;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.example.louis.musicplayertest.MainActivity;
import com.example.louis.musicplayertest.Song;

import java.io.File;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;
import static com.example.louis.musicplayertest.MainActivity.getContext;
import static com.example.louis.musicplayertest.MainActivity.songs;

public class RechercheMusique extends AsyncTask<Void,Void,Boolean> {
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1524515;
    @Override
    protected Boolean doInBackground(Void... voids) {
        try{
            System.out.println("Demande d'accès à la mémoire du telephone");

            String addr = "";

            System.out.println("Recherches des musiques:");
            searchMusicFiles("/storage");

            searchMusicFiles("/sdcard");

            System.out.println(songs.size() + "musiques trouvées.");

        }catch(Exception e){e.printStackTrace();}
        return null;
    }

    private boolean searchMusicFiles(String path) {
        System.out.println("Recherche dans : "+path);

        File directory = new File(path);

        try {
            for (File f : directory.listFiles()) {
                if (f.isDirectory()) {
                    try {
                        searchMusicFiles(f.getPath());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (f.getPath().endsWith(".mp3")) {
                        System.out.println(f.getPath());
                        songs.add(new Song(f));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
