package com.example.louis.musicplayertest;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;


import com.example.louis.musicplayertest.Fragment.Lecteur;
import com.example.louis.musicplayertest.Fragment.ListSong;

import org.xml.sax.ext.LexicalHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1524515;

    public static List<Song> songs = new ArrayList<Song>();

    ListSong listSong = new ListSong();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        try{
            System.out.println("Demande d'accès à la mémoire du telephone");
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                //ask for permission
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }


            String addr = "";

            System.out.println("Recherches des musiques:");

            searchMusicFiles("/storage");
            searchMusicFiles("/sdcard");
            System.out.println(songs.size() + "musiques trouvées.");

        }catch(Exception e){e.printStackTrace();}

        SearchView searchView = findViewById(R.id.searchBox);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                for (Song s : songs){
                    if (s.getName().contains(newText)){
                        System.out.println(s.getName());
                    }
                }
                return false;
            }
        });

        Button buttonPlayer = findViewById(R.id.Player);
        buttonPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment,(android.app.Fragment) new Lecteur()).commit();
            }
        });
        
        Button buttonListSong = findViewById(R.id.ListSong);
        buttonListSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment,(android.app.Fragment) new ListSong()).commit();
            }
        });
    }
    private boolean searchMusicFiles(String path) {
        System.out.println("Recherche dans : "+path);

        File directory = new File(path);

        for (File f : directory.listFiles()) {
            if (f.isDirectory()) {
                try {
                    searchMusicFiles(f.getPath());
                }catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                if (f.getPath().endsWith(".mp3")) {
                    System.out.println(f.getPath());
                    songs.add(new Song(f));
                }
            }
        }
        return true;
    }


}