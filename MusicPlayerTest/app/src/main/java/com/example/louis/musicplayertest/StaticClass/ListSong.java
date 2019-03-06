package com.example.louis.musicplayertest.StaticClass;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.louis.musicplayertest.Adapter.ListViewAdapter;
import com.example.louis.musicplayertest.Adapter.SongAdapter;
import com.example.louis.musicplayertest.MainActivity;
import com.example.louis.musicplayertest.Recycler.RecyclerTouch;
import com.example.louis.musicplayertest.Song;
import com.example.louis.musicplayertest.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ListSong extends android.app.Fragment{

    private static final ListSong ourInstance = new ListSong();

    private RecyclerView recyclerView;

    private SongAdapter songAdapter;

    List<Song> song= MainActivity.songs;

    List<String> songname=new ArrayList<String>();

    ListView listView;
    ListViewAdapter listViewAdapter;
    TextView name;


    private SearchView searchView;
    public static int samesong=1;

    public static ListSong getInstance() {

        return ourInstance;
    }

    public ListSong() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_list_song, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator( new DefaultItemAnimator());

        if (songAdapter==null){
            songAdapter = new SongAdapter(getContext(),song);
        }

        recyclerView.setAdapter(songAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouch(getContext(), recyclerView, new RecyclerTouch.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (Player.getInstance().getSongID()!=position){
                    samesong=0;
                    Player.getInstance().setSongID(position);
                    if(getContext().getClass()==MainActivity.class){
                        getActivity().setTitle("Player");
                        getFragmentManager().beginTransaction().replace(R.id.fragment, Player.getInstance()).commit();
                    }
                    else{
                        Player.getInstance().setSongID(position );
                        Player.getInstance().accessAndPlaySong(0);
                    }
                }
                else{
                    if(getContext().getClass()==MainActivity.class){
                        getActivity().setTitle("Player");
                        samesong=1;
                        getFragmentManager().beginTransaction().replace(R.id.fragment, Player.getInstance()).commit();
                    }
                }
            }
            @Override
            public void onLongClick(View view, int position) {

            }

        }));

        for (int i=0;i<song.size();i++){
            songname.add(song.get(i).getName());
        }

        samesong=1;


        //////////////////////GERER PAR LE RECYCLER VIEW////////////////////////////////////////
        /*listView=view.findViewById(R.id.ListSong);
        listView.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, songname));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Player.getInstance().setSongID(position);

                if(getContext().getClass()==MainActivity.class){
                    getFragmentManager().beginTransaction().replace(R.id.fragment, Player.getInstance()).commit();
                }
                else{
                    Player.getInstance().accessAndPlaySong(0);
                }

            }
        });


        if (listViewAdapter==null){
            listViewAdapter = new ListViewAdapter(getContext(),song);
        }*/
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////



        
        searchView=view.findViewById(R.id.txtsearch);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (TextUtils.isEmpty(newText)){
                    songAdapter.filter("");
                    //listViewAdapter.filter("");
                    //listView.clearTextFilter();

                }
                else{
                    songAdapter.filter(newText);
                    //listViewAdapter.filter(newText);
                }
                return true;
            }
        });



        return view;
    }


}
