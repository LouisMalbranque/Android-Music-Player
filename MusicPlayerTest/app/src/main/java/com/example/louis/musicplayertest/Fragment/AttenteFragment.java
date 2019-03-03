package com.example.louis.musicplayertest.Fragment;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.louis.musicplayertest.R;
import com.example.louis.musicplayertest.StaticClass.Player;
import com.example.louis.musicplayertest.async.RechercheMusique;

import static android.content.Context.MODE_PRIVATE;

public class AttenteFragment extends Fragment {

    private RechercheMusique mMusiqueAsyncTask;
    public android.app.FragmentManager manager;
    public FragmentTransaction transaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_attente,container,false);


        final ProgressBar progressBar = new ProgressBar(getActivity());
        progressBar.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER));


        int color = 0xFF44BBE3;
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);

        //progressBar.setIndeterminate(true);

        container.addView(progressBar);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mMusiqueAsyncTask=new RechercheMusique();
        mMusiqueAsyncTask.execute();


    }

}
