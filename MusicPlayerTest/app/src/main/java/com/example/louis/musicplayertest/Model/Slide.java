package com.example.louis.musicplayertest.Model;

public class Slide {
    private int imgId;
    private String title;

    public Slide(int imgId, String title) {
        this.imgId = imgId;
        this.title = title;
    }

    public Slide(String title) {
        this.title = title;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
