package com.example.louis.musicplayertest.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
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


public class InscriptionFragment extends Fragment implements changementListeUtilisateur{

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
        muserAsyncTask=new ajoutBDD(this);

        final Button inscrireButton = getView().findViewById(R.id.inscrire);

        inscrireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView new_login = (TextView)getView().findViewById(R.id.nouvNom);
                final TextView new_pw = (TextView)getView().findViewById(R.id.nouvMdp);
                User nouvelUtilisateur = new User();
                nouvelUtilisateur.text = new_pw.getText().toString();
                nouvelUtilisateur.id = new_login.getText().toString();
                if (TextUtils.isEmpty(((TextView) getActivity().findViewById(R.id.loginText)).getText())) {
                    Toast.makeText(getActivity().getApplicationContext(), "Entrez un login", Toast.LENGTH_LONG).show();
                    getFragmentManager().beginTransaction().remove(InscriptionFragment.this).commit();
                } else if (TextUtils.isEmpty(((TextView) getActivity().findViewById(R.id.passwordText)).getText())) {
                    Toast.makeText(getActivity().getApplicationContext(), "Entrez un mot de passe", Toast.LENGTH_LONG).show();
                    getFragmentManager().beginTransaction().remove(InscriptionFragment.this).commit();
                } else {
                    muserAsyncTask.execute(nouvelUtilisateur);
                }
            }
        });
    }

    @Override
    public void changement(Integer bool) {
        Button loginButton = getActivity().findViewById(R.id.loginButton);
        Button subscribeButton = getActivity().findViewById(R.id.inscription);
        loginButton.setVisibility(View.VISIBLE);
        subscribeButton.setClickable(true);
        if(bool==0){
            Toast.makeText(getActivity().getApplicationContext(), "Cet utilisateur existe deja", Toast.LENGTH_LONG).show();
        }
        else{
        Toast.makeText(LoginActivity.getContext(), "Vous êtes inscrit", Toast.LENGTH_LONG).show();}
        getFragmentManager().beginTransaction().remove(InscriptionFragment.this).commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof changementListeUtilisateur){
            mListener=(changementListeUtilisateur) context;
        }
    }
}
