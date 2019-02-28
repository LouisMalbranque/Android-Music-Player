package com.example.louis.musicplayertest;

import java.io.File;
import java.io.Serializable;

public class Song implements Serializable {
    private String path;
    private String name;
    public Song(String name){
        this.name=name;

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
