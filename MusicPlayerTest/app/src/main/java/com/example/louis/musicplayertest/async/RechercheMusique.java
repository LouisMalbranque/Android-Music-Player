package com.example.louis.musicplayertest.async;

import android.os.AsyncTask;

import com.example.louis.musicplayertest.Song;
import com.example.louis.musicplayertest.StaticClass.ListSong;
import com.example.louis.musicplayertest.interfaces.RechercheMus;
import com.example.louis.musicplayertest.interfaces.chargementUtilisateurs;

import java.io.File;
import java.util.List;

import static com.example.louis.musicplayertest.MainActivity.songs;

public class RechercheMusique extends AsyncTask<Void,Void, List<Song>> {
    private RechercheMus mListenerR;
    public RechercheMusique(RechercheMus mListenerR) {this.mListenerR=mListenerR; }


    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1524515;
    @Override
    protected List<Song> doInBackground(Void... voids) {
        List<Song> songs2;
        try{
            System.out.println("Demande d'accès à la mémoire du telephone");

            String addr = "";

            System.out.println("Recherches des musiques:");
            searchMusicFiles("/storage");

            searchMusicFiles("/sdcard");

            System.out.println(songs.size() + "musiques trouvées.");

        }catch(Exception e){e.printStackTrace();}
        return songs;
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
    protected void onPostExecute(List<Song> songs) {
        mListenerR.finRecherche();
    }
}
