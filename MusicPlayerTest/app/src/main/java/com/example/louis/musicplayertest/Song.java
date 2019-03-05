package com.example.louis.musicplayertest;

import android.media.MediaMetadataRetriever;

import java.io.File;
import java.io.Serializable;

public class Song implements Serializable {
    private String path;
    private String name;
    private String artist;
    private String date;
    private String album;
    private String duration;

    public Song(){

    }
    public Song(File f){
        path = f.getPath();
        MediaMetadataRetriever mmdr = new MediaMetadataRetriever();
        mmdr.setDataSource(path);
        name = mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        artist = mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        date = mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE);
        duration = Integer.toString(Integer.parseInt(mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) / 60000) + ":" + Integer.toString((Integer.parseInt(mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) - ((Integer.parseInt(mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) / 60000) * 60000)) / 1000);
        album = mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
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
    public String getAlbum() { return album; }
    public String getDuration() { return duration; }
}
