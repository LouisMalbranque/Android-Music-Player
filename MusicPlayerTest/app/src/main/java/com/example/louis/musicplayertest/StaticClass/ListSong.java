package com.example.louis.musicplayertest.StaticClass;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.louis.musicplayertest.Adapter.ListViewAdapter;
import com.example.louis.musicplayertest.MainActivity;
import com.example.louis.musicplayertest.Song;
import com.example.louis.musicplayertest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListSong extends android.app.Fragment{

    private static final ListSong ourInstance = new ListSong();

    List<Song> song= MainActivity.songs;

    List<String> songname=new ArrayList<String>();

    ListView listView;
    ListViewAdapter listViewAdapter;
    TextView name;


    private SearchView searchView;

    public static ListSong getInstance() {

        return ourInstance;
    }

    public ListSong() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_list_song, container, false);

        listView=view.findViewById(R.id.ListSong);

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

        if (listViewAdapter==null){
            listViewAdapter = new ListViewAdapter(getContext(),song);
        }


        listView.setAdapter(listViewAdapter);

        searchView=view.findViewById(R.id.txtsearch);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)){
                    listViewAdapter.filter("");
                    listView.clearTextFilter();
                }
                else{
                    listViewAdapter.filter(newText);
                }
                return true;
            }
        });

        System.out.println("create");
        return view;
    }


}
