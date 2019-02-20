package com.example.louis.musicplayertest.StaticClass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.louis.musicplayertest.MainActivity;
import com.example.louis.musicplayertest.Song;
import com.example.louis.musicplayertest.R;

import java.util.ArrayList;
import java.util.List;

public class ListSong extends android.app.Fragment{
    private static final ListSong ourInstance = new ListSong();

    List<Song> song= MainActivity.songs;
    List<String> songname=new ArrayList<String>();

    public static ListSong getInstance() {
        return ourInstance;
    }

    public ListSong() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_list_song, container, false);


        ListView listView=view.findViewById(R.id.ListSong);
        for (int i=0;i<song.size();i++){
            songname.add(song.get(i).getName());
        }
        listView.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, songname));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Player.getInstance().pause();
                Player.getInstance().setSongID(position);
                Player.getInstance().accessAndPlaySong(0);
                Player.getInstance().play();
            }
        });
        return view;
    }
}
