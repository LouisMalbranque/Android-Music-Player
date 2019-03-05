package com.example.louis.musicplayertest.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.louis.musicplayertest.R;
import com.example.louis.musicplayertest.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.MyViewHolder> {


    private List<Song> songList;
    ArrayList<Song> arrayList=new ArrayList<Song>();
    Context mContext;
    LayoutInflater inflater;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView artist;
        public TextView album;
        public TextView duration;

        public MyViewHolder(View view) {
            super(view);
            title=(TextView) view.findViewById(R.id.titre_chanson);
            artist=(TextView) view.findViewById(R.id.artiste_chanson);
            album=(TextView) view.findViewById(R.id.album_chanson);
            duration=(TextView) view.findViewById(R.id.duree_chanson);

        }
    }
    public SongAdapter(Context context,List<Song> songList) {
        mContext=context;
        this.songList = songList;
        inflater = LayoutInflater.from(mContext);
        this.arrayList.addAll(songList);
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewtype) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.song_adapter,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        Song song=songList.get(position);
        myViewHolder.title.setText(song.getName());
        myViewHolder.album.setText(song.getAlbum());
        myViewHolder.artist.setText(song.getArtist());
        myViewHolder.duration.setText(song.getDuration());
    }


    @Override
    public int getItemCount() {
        return songList.size();
    }

    public void filter(String charText){
        charText=charText.toLowerCase(Locale.getDefault());
        songList.clear();
        if (charText.length()==0){
            songList.addAll(arrayList);
        }
        else{
            for (Song song:arrayList){
                if (song.getName().toLowerCase(Locale.getDefault()).contains(charText)){
                    songList.add(song);
                }
            }
        }
        notifyDataSetChanged();
    }
}
