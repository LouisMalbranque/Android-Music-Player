package com.example.louis.musicplayertest;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileInputStream;
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

            if (new File(getStoragePath().getPath() + "/Music/").exists()){
                addr = getStoragePath().getPath() + "/Music/";
            }
            else if (new File("/sdcard/Download/Music/").exists()){
                addr = getStoragePath().getPath() + "/Music/";
            }else{
                System.out.println("Error: No directory found");
            }
            File directory = new File(addr);


            System.out.println("Recherches des musiques:");
            for (File f : directory.listFiles()) {
                songs.add(new Song(f));
                strSongs.add(f.getPath());
                System.out.println(f.getPath());
            }
            System.out.println(songs.size() + "musiques trouvées.");




            mp.setDataSource(songs.get(0).getPath());
            mp.prepare();
            mp.start();

        }catch(Exception e){e.printStackTrace();}



        final Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
            }
        });

        final Button pauseButton = findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
            }
        });

        final Button nextButton = findViewById(R.id.nextButton);
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

}


class FileExtensionFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        return (name.endsWith(".mp3") || name.endsWith(".MP3"));
    }
}