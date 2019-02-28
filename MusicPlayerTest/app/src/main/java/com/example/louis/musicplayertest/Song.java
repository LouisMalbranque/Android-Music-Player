package com.example.louis.musicplayertest;

import android.media.MediaMetadataRetriever;

import java.io.File;
import java.io.Serializable;

public class Song implements Serializable {
    private String path;
    private String name;
    private String artist;
    private String date;

    public Song(){

    }
    public Song(File f){
        path = f.getPath();
        MediaMetadataRetriever mmdr = new MediaMetadataRetriever();
        mmdr.setDataSource(path);
        name = mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        artist = mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        date = mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE);
    }
    public String getPath(){
        return path;
    }
    public String getName() {
        return name;
    }
    public String getArtist(){
        return artist;
    }
    public String getDate(){
        return date;
    }
}
