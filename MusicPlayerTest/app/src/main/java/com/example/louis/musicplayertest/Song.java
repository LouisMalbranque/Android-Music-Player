package com.example.louis.musicplayertest;

import java.io.File;

public class Song {
    private String path;
    Song(){

    }
    Song(File f){
        path = f.getPath();
    }
    String getPath(){
        return path;
    }
}
