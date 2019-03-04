package com.example.louis.musicplayertest.StaticClass;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.louis.musicplayertest.AsyncTasks.GetAlbumImage;
import com.example.louis.musicplayertest.MainActivity;
import com.example.louis.musicplayertest.MainCombined;
import com.example.louis.musicplayertest.Song;
import com.example.louis.musicplayertest.R;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.widget.Toast.makeText;

public class Player extends android.app.Fragment implements SeekBar.OnSeekBarChangeListener{
    private static final Player ourInstance = new Player();

    public static Player getInstance() {
        return ourInstance;
    }


    private MediaPlayer mp=new MediaPlayer();
    private int songID = -1;
    private View view;

    private List<Song> songs = MainActivity.songs;

    private ImageButton playButton;
    private ImageButton pauseButton;
    private ImageButton nextButton;
    private ImageButton previousButton;
    private ImageButton forwardButton;
    private ImageButton backwardButton;


    private SeekBar sb;
    private SeekBar mSeekBar;

    private AudioManager am;
    private int Volume;

    Handler handler;
    Runnable runnable;

    private TextView songProgress;
    private TextView songMax;


    private boolean isPlaying = false;

    public Player() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view= inflater.inflate(R.layout.fragment_player, container, false);

        am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);

        mp.setLooping(true);

        sb = view.findViewById(R.id.sbVolume);
        sb.setMax(maxVolume);
        sb.setProgress(curVolume);
        sb.setOnSeekBarChangeListener(this);

        playButton = view.findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (songID != -1) play();

                else accessAndPlaySong(1);
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

        if (isPlaying) play();
        else if (songID!=-1) pause();
        forwardButton = view.findViewById(R.id.forwardButton);
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveMusic(3);
            }
        });
        backwardButton = view.findViewById(R.id.backwardButton);
        backwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveMusic(-3);
            }
        });

        if (isPlaying) play(); else pause();

        mSeekBar=view.findViewById(R.id.songDuration);

        handler =new Handler();

        if (songID!=-1) mSeekBar.setMax(mp.getDuration());

        songProgress=view.findViewById(R.id.SongProgress);
        songMax=view.findViewById(R.id.songMax);


        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mSeekBar.setMax(mp.getDuration());
                final long Minutes=(mp.getDuration()/1000)/60;//converting into minutes
                final int Seconds=((mp.getDuration()/1000)%60);//converting into seconds
                if (Seconds<10){
                    songMax.setText(Minutes+":0"+Seconds);
                }
                else{
                    songMax.setText(Minutes+":"+Seconds);
                }

                playCycle();
            }
        });

        playCycle();

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean input) {
                if (input){
                    mp.seekTo(progress);
                }
                final long mMinutes=(progress/1000)/60;//converting into minutes
                final int mSeconds=((progress/1000)%60);//converting into seconds
                if (mSeconds<10){
                    songProgress.setText(mMinutes+":0"+mSeconds);
                }
                else{
                    songProgress.setText(mMinutes+":"+mSeconds);
                }


                final long Minutes=(mp.getDuration()/1000)/60;//converting into minutes
                final int Seconds=((mp.getDuration()/1000)%60);//converting into seconds
                songMax.setText(Minutes+":"+Seconds);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        });

        if (getContext().getClass()==MainActivity.class && songID!=-1){
            accessAndPlaySong(0);
        }


        return view;
    }

    public void playCycle(){
        mSeekBar.setProgress(mp.getCurrentPosition());
        if (mp.isPlaying()){
            runnable = new Runnable() {
                @Override
                public void run() {
                    playCycle();
                }
            };
            handler.postDelayed(runnable,250);
        }
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
        if (songID!=-1){
            nameSong();
            if (isPlaying) play(); else pause();
        }

    }


    public void accessAndPlaySong(int nextOrPrevious){
        if (songID !=-1) {
            mp.stop();
            mp.reset();
        }
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

        new GetAlbumImage().execute(songs.get(songID).getArtist(),songs.get(songID).getName(),view.findViewById(R.id.imageView));
    }
    public void newSong(){
        try {
            Toast t = makeText(getContext(), songs.get(songID).getName(), Toast.LENGTH_LONG);
            t.setGravity(Gravity.TOP, 0, 150);
            t.show();
        }catch (Exception e){ e.printStackTrace();
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
    public void moveMusic(int sec){
        int time = mp.getCurrentPosition() + sec*1000;
        if (time < 0) time = 0;
        else if (time > mp.getDuration())
        {
            accessAndPlaySong(1);
            return;
        }
        mp.seekTo(time);
    }
}
