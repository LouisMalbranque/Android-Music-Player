package com.example.louis.musicplayertest.Fragment;


import android.os.Bundle;
import android.os.MessageQueue;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.louis.musicplayertest.MainActivity;
import com.example.louis.musicplayertest.R;
import com.example.louis.musicplayertest.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListSong extends android.app.Fragment implements AdapterView.OnItemClickListener{


    List<Song> song= MainActivity.songs;
    List<String> songname=new ArrayList<String>();

    public ListSong() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_list_song, container, false);


        ListView listView=view.findViewById(R.id.ListSong);
        for (int i=0;i<song.size();i++){
            songname.add(song.get(i).getName());
            System.out.println("test");

        }
        listView.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, songname));
        listView.setOnItemClickListener(this);

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //getFragmentManager().beginTransaction().add(R.id.mainActivity,new Lecteur()).addToBackStack(null).commit();
    }
}
