package com.example.louis.musicplayertest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1524515;
    MediaPlayer mp=new MediaPlayer();
    List<Song> songs = new ArrayList<Song>();
    List<String> strSongs = new ArrayList<String>();
    int songID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println();
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
            mp.start();

        }catch(Exception e){e.printStackTrace();}



        final ImageButton playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
            }
        });

        final ImageButton pauseButton = findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
            }
        });

        final ImageButton nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                mp.reset();
                songID = (songID+1)%songs.size();
                System.out.println(songs.get(songID).getPath());
                try {
                    mp.setDataSource(songs.get(songID).getPath());
                    mp.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mp.start();
            }
        });



    }
    File getStoragePath() {
        String removableStoragePath;
        File fileList[] = new File("/storage/").listFiles();
        for (File file : fileList) {
            if(!file.getAbsolutePath().equalsIgnoreCase(Environment.getExternalStorageDirectory().getAbsolutePath()) && file.isDirectory() && file.canRead()) {
                return file;
            }
        }
        return Environment.getExternalStorageDirectory();
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


class FileExtensionFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        return (name.endsWith(".mp3") || name.endsWith(".MP3"));
    }
}