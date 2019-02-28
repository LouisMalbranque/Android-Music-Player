package com.example.louis.musicplayertest.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.louis.musicplayertest.MainActivity;
import com.example.louis.musicplayertest.R;
import com.example.louis.musicplayertest.Song;
import com.example.louis.musicplayertest.StaticClass.ListSong;
import com.example.louis.musicplayertest.StaticClass.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    ArrayList<Song> arrayList=new ArrayList<Song>();
    List<Song> songList;

    public ListViewAdapter(Context context, List<Song> songList) {
        mContext=context;
        this.songList = songList;
        inflater = LayoutInflater.from(mContext);
        this.arrayList.addAll(songList);


    }

    public class ViewHolder{
        TextView name;
    }

    @Override
    public int getCount() {
        return songList.size();
    }

    @Override
    public Object getItem(int position) {
        return songList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view==null){
            holder=new ViewHolder();
            view= inflater.inflate(R.layout.name_song,null);
            holder.name=view.findViewById(R.id.nameSong);
            view.setTag(holder);
        }
        else{
            holder=(ViewHolder)view.getTag();
        }

        holder.name.setText(songList.get(position).getName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player.getInstance().pause();
                Player.getInstance().setSongID(position);
                Player.getInstance().accessAndPlaySong(0);
                Player.getInstance().play();
            }
        });
        return view;
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
