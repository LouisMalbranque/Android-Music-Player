package com.example.louis.musicplayertest;

import java.io.File;

public class Song {
    private String path;
    private String name;
    Song(){

    }
    public Song(File f){
        path = f.getPath();
        String splitedPath[] = f.getPath().split("/");
        name = splitedPath[splitedPath.length-1].split(".mp3")[0];
    }
    public String getPath(){
        return path;
    }
    public String getName(){
        return name;
    }
}
