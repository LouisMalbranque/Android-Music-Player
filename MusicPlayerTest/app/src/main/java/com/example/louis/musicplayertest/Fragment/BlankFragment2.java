package com.example.louis.musicplayertest.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.louis.musicplayertest.R;

public class BlankFragment2 extends android.app.Fragment {
    private static final BlankFragment2 ourInstance = new BlankFragment2();
    public static BlankFragment2 getInstance() {
        return ourInstance;
    }
    public BlankFragment2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }
}
