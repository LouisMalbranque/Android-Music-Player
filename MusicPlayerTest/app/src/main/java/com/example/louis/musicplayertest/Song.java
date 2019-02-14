package com.example.louis.musicplayertest;

import java.io.File;

public class Song {
    private String path;
    private String name;
    Song(){

    }
    Song(File f){
        path = f.getPath();
        String splitedPath[] = f.getPath().split("/");
        String name = splitedPath[splitedPath.length-1].split(".mp3")[0];
    }
    String getPath(){
        return path;
    }
    String getName(){
        return name;
    }
}
