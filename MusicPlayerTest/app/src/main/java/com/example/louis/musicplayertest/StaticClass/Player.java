package com.example.louis.musicplayertest.StaticClass;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.louis.musicplayertest.MainActivity;
import com.example.louis.musicplayertest.Song;
import com.example.louis.musicplayertest.R;

import java.io.IOException;
import java.util.List;

import static android.widget.Toast.makeText;

public class Player extends android.app.Fragment implements SeekBar.OnSeekBarChangeListener{
    private static final Player ourInstance = new Player();

    public static Player getInstance() {
        return ourInstance;
    }

    private MediaPlayer mp=new MediaPlayer();
    private int songID = 0;
    private View view;

    private List<Song> songs = MainActivity.songs;

    private ImageButton playButton;
    private ImageButton pauseButton;
    private ImageButton nextButton;
    private ImageButton previousButton;

    private SeekBar sb;

    private AudioManager am;
    private int Volume=0;

    private boolean isPlaying = false;

    public Player() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view= inflater.inflate(R.layout.fragment_player, container, false);

        am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);

        mp.setLooping(true);

        sb =(SeekBar) view.findViewById(R.id.sbVolume);
        sb.setMax(maxVolume);
        sb.setProgress(curVolume);
        sb.setOnSeekBarChangeListener(this);

        playButton = view.findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp.getDuration() != 0) play();
                else accessAndPlaySong(0);
            }
        });
        pauseButton = view.findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });
        nextButton = view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessAndPlaySong(1);
            }
        });
        previousButton=view.findViewById(R.id.previousbutton);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessAndPlaySong(-1);
            }
        });

        if (isPlaying) play(); else pause();

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isPlaying", isPlaying);
        outState.putInt("songID", songID);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null){
            isPlaying = savedInstanceState.getBoolean("isPlaying");
            songID = savedInstanceState.getInt("songID");
        }
        nameSong();
        if (isPlaying) play(); else pause();
    }


    public void accessAndPlaySong(int nextOrPrevious){
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
        play();
        nameSong();
        newSong();

    }
    public void nameSong(){
        TextView nameSong=view.findViewById(R.id.songName);
        nameSong.setText(songs.get(songID).getName());
    }
    public void newSong(){
        try {
            Toast t = makeText(getContext(), songs.get(songID).getName(), Toast.LENGTH_LONG);
            t.setGravity(Gravity.TOP, 0, 150);
            t.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

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
    public void setSongID(int songID) {
        this.songID = songID;
    }
    public void pause(){
        pauseButton.setVisibility(ImageButton.INVISIBLE);
        playButton.setVisibility(ImageButton.VISIBLE);
        mp.pause();
        isPlaying = false;
    }
    public void play(){
        pauseButton.setVisibility(ImageButton.VISIBLE);
        playButton.setVisibility(ImageButton.INVISIBLE);
        mp.start();
        isPlaying = true;
    }

}
