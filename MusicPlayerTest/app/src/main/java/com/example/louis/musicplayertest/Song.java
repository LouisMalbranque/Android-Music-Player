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
    private String mSecond;
    private String mMinute;

    public Song(){

    }
    public Song(File f){
        path = f.getPath();
        MediaMetadataRetriever mmdr = new MediaMetadataRetriever();
        mmdr.setDataSource(path);
        name = mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        artist = mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        date = mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE);
        mSecond=Integer.toString((Integer.parseInt(mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) - ((Integer.parseInt(mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) / 60000) * 60000)) / 1000);
        mMinute=Integer.toString(Integer.parseInt(mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) / 60000);
        duration=mMinute+":"+mSecond;
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
    public String getDuration() {
        if (mSecond.length()==1){
            duration=mMinute+":0"+mSecond;
        }
        else{
            duration=mMinute+":"+mSecond;
        }

        return duration; }
}
