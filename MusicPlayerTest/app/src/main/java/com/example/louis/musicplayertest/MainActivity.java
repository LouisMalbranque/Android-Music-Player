package com.example.louis.musicplayertest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1524515;
    MediaPlayer mp=new MediaPlayer();
    List<Song> songs = new ArrayList<Song>();
    List<String> strSongs = new ArrayList<String>();
    int songID = 0;

    ImageButton playButton;
    ImageButton pauseButton;
    ImageButton nextButton;
    ImageButton previousButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            System.out.println("Demande d'accès à la mémoire du telephone");
            if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                //ask for permission
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }


            String addr = "";

            System.out.println("Recherches des musiques:");
            /*for (File f : directory.listFiles()) {
                f.listFiles()
                songs.add(new Song(f));
                strSongs.add(f.getPath());
                System.out.println(f.getPath());
            }*/
            searchMusicFiles("/storage");
            searchMusicFiles("/sdcard");
            System.out.println(songs.size() + "musiques trouvées.");




            mp.setDataSource(songs.get(0).getPath());
            mp.prepare();
            newSong();
            nameSong();

        }catch(Exception e){e.printStackTrace();}



        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playButton.setVisibility(ImageButton.INVISIBLE);
                pauseButton.setVisibility(ImageButton.VISIBLE);
                mp.start();

                mp.start();
            }
        });

        pauseButton = findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseButton.setVisibility(ImageButton.INVISIBLE);
                playButton.setVisibility(ImageButton.VISIBLE);
                mp.pause();
            }
        });

        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessAndPlaySong(1);
            }
        });
        previousButton=findViewById(R.id.previousbutton);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessAndPlaySong(-1);
            }
        });
    }
    private void accessAndPlaySong(int nextOrPrevious){
        mp.stop();
        mp.reset();
        songID = (songID + nextOrPrevious);
        if (songID < 0) songID = songs.size()-1;
        if (songID > songs.size()-1) songID = 0;
        System.out.println(songs.get(songID).getPath());
        try {
            mp.setDataSource(songs.get(songID).getPath());
            mp.prepare();
        }
        catch (IOException e) { e.printStackTrace();
        }
        mp.start();
        newSong();
        nameSong();

    } //si next mettre 1 sinon -1

    private void nameSong(){
        TextView nameSong=findViewById(R.id.songName);
        nameSong.setText(songs.get(songID).getName());
    }


    private void newSong(){
        Toast t=Toast.makeText(getApplicationContext(),songs.get(songID).getName(), Toast.LENGTH_LONG);
        t.setGravity(Gravity.TOP,0,150);
        t.show();

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