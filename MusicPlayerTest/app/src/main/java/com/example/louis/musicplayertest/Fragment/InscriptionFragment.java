package com.example.louis.musicplayertest.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.louis.musicplayertest.LoginActivity;
import com.example.louis.musicplayertest.R;
import com.example.louis.musicplayertest.async.ajoutBDD;
import com.example.louis.musicplayertest.interfaces.changementListeUtilisateur;
import com.example.louis.musicplayertest.pojo.User;


public class InscriptionFragment extends Fragment {

    private ajoutBDD muserAsyncTask;
    private changementListeUtilisateur mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.frament_subscribe,container,false);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        muserAsyncTask=new ajoutBDD(mListener);

        Button inscrireButton = getView().findViewById(R.id.inscrire);
        final TextView new_login = (TextView)getView().findViewById(R.id.nouvNom);
        final TextView new_pw = (TextView)getView().findViewById(R.id.nouvMdp);
        inscrireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User nouvelUtilisateur= new User();
                nouvelUtilisateur.text=new_pw.getText().toString();
                nouvelUtilisateur.id=new_login.getText().toString();
                Toast.makeText(LoginActivity.getContext(), "Vous êtes inscrit", Toast.LENGTH_LONG).show();
                muserAsyncTask.execute(nouvelUtilisateur);
                getFragmentManager().beginTransaction().remove(InscriptionFragment.this).commit();
            }

        });


    }




}
