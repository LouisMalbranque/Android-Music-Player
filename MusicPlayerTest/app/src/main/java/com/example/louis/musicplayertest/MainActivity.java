package com.example.louis.musicplayertest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.louis.musicplayertest.Fragment.Song_Fragment;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1524515;
    List<Song> songs = new ArrayList<Song>();
    SeekBar sb;
    AudioManager am;
    int Volume=0;

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
        Volume = progress;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //Toast.makeText(getApplicationContext(), "Volume: " + Integer.toString(Volume), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        sb =(SeekBar) findViewById(R.id.sbVolume);
        sb.setMax(maxVolume);
        sb.setProgress(curVolume);

        sb.setOnSeekBarChangeListener(this);

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



        Bundle b = new Bundle();
        for (int i=0; i<songs.size(); i++){
            b.putSerializable("song"+Integer.toString(i), songs.get(i));
        }
        getSupportFragmentManager().beginTransaction().add(R.id.mainActivity,Song_Fragment.newInstance(songs)).commit();

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