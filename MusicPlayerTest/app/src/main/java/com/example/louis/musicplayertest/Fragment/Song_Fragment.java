package com.example.louis.musicplayertest.Fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.louis.musicplayertest.R;
import com.example.louis.musicplayertest.Song;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * A simple {@link Fragment} subclass.
 */
public class Song_Fragment extends Fragment {

    private MediaPlayer mp=new MediaPlayer();
    private int songID = 0;
    private View viewfragment;

    private List<Song> songs = new ArrayList<Song>(1);

    private ImageButton playButton;
    private ImageButton pauseButton;
    private ImageButton nextButton;
    private ImageButton previousButton;

    private SeekBar sb;

    public Song_Fragment() {
        // Required empty public constructor
    }
    public static Song_Fragment newInstance(List<Song> songs) {

        Bundle args = new Bundle();
        for (int i=0; i<songs.size(); i++){
            args.putSerializable("song"+Integer.toString(i), songs.get(i));
        }
        Song_Fragment fragment = new Song_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (int i=0; i<getArguments().size(); i++){
            songs.add((Song) getArguments().getSerializable("song"+Integer.toString(i)));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_song, container, false);
        viewfragment=view;

        playButton = view.findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playButton.setVisibility(ImageButton.INVISIBLE);
                pauseButton.setVisibility(ImageButton.VISIBLE);
                mp.start();

            }
        });

        pauseButton = view.findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseButton.setVisibility(ImageButton.INVISIBLE);
                playButton.setVisibility(ImageButton.VISIBLE);
                mp.pause();
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
        return view;
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

    }

    private void nameSong(){
        TextView nameSong=viewfragment.findViewById(R.id.songName);
        nameSong.setText(songs.get(songID).getName());
    }


    private void newSong(){
        Toast t= makeText(getContext(),songs.get(songID).getName(), Toast.LENGTH_LONG);
        t.setGravity(Gravity.TOP,0,150);
        t.show();

    }
}
