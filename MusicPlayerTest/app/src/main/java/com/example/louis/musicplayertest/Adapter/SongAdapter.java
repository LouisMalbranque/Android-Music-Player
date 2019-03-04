package com.example.louis.musicplayertest.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.louis.musicplayertest.R;
import com.example.louis.musicplayertest.Song;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.MyViewHolder> {


    private List<Song> songList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title=(TextView) view.findViewById(R.id.titre_chanson);

        }
    }
    public SongAdapter(List<Song> song) {
        this.songList = song;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewtype) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.song_recycler_view,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        Song song=songList.get(position);
        myViewHolder.title.setText(song.getName());
    }


    @Override
    public int getItemCount() {
        return songList.size();
    }
}
