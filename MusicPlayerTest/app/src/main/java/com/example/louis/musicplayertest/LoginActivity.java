package com.example.louis.musicplayertest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.louis.musicplayertest.Fragment.InscriptionFragment;
import com.example.louis.musicplayertest.async.RechercheBDD;
import com.example.louis.musicplayertest.async.ajoutBDD;
import com.example.louis.musicplayertest.interfaces.changementListeUtilisateur;
import com.example.louis.musicplayertest.interfaces.chargementUtilisateurs;
import com.example.louis.musicplayertest.pojo.User;

import java.util.List;


public class LoginActivity extends Activity implements chargementUtilisateurs{
    InscriptionFragment inscription_frag = new InscriptionFragment();
    private static Context sContext;
    private RechercheBDD mBDDAsyncTask;

    chargementUtilisateurs mListener = this;
    private ajoutBDD muserAsyncTask;
    private changementListeUtilisateur mListener2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sContext = getApplicationContext();
        setContentView(R.layout.activity_login);

        muserAsyncTask = new ajoutBDD(mListener2);


        final Button loginButton = findViewById(R.id.loginButton);

        final Button subscribeButton = findViewById(R.id.inscription);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 mBDDAsyncTask=new RechercheBDD(mListener);
                 mBDDAsyncTask.execute();

            }
        });

        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribeButton.setClickable(false);

                loginButton.setVisibility(View.GONE);
                getFragmentManager().beginTransaction().add(R.id.container, inscription_frag).commit();

            }
        });
    }

    public void login() {
       final Switch loginSwitch = findViewById(R.id.loginSwitch);

        Intent intent = new Intent(getApplicationContext(), Atttente.class);
        Bundle extras = new Bundle();
        extras.putString("log", getApplicationContext().getSharedPreferences("com.example.projet2.log", Context.MODE_PRIVATE).getString("login", null));
        startActivity(intent);

        finish();
    }

    public static Context getContext() {
        return sContext;
    }


    public void chargementUtil(List<User> listeUser) {

    final SharedPreferences mesPreferences = getApplicationContext().getSharedPreferences("com.example.projet2.log", Context.MODE_PRIVATE);
    String[] loginBDD= new String[listeUser.size()];
    String log = mesPreferences.getString("log", null);
    String pw = mesPreferences.getString("pw", null);

    final TextView tv_login = (TextView) findViewById(R.id.loginText);
    final TextView tv_pw = (TextView) findViewById(R.id.passwordText);

    final Switch loginSwitch = findViewById(R.id.loginSwitch);

    for (int i = 0; i < listeUser.size(); i++) {
                       loginBDD[i]=listeUser.get(i).getId();
    }

                    if (TextUtils.isEmpty(((TextView) findViewById(R.id.loginText)).getText())) {
        Toast.makeText(getApplicationContext(), "Entrez un login", Toast.LENGTH_LONG).show();

    } else if (TextUtils.isEmpty(((TextView) findViewById(R.id.passwordText)).getText())) {
        Toast.makeText(getApplicationContext(), "Entrez un mot de passe", Toast.LENGTH_LONG).show();
    } else {
                        for (int i = 0; i < loginBDD.length; i++) {
                            if (loginBDD[i].equals(tv_login.getText().toString())) {
                                if(listeUser.get(i).getText().equals(tv_pw.getText().toString())){

                                //Enregistrement du login et du password

                                if (loginSwitch.isChecked()) {
                                    SharedPreferences.Editor prefs_edit = mesPreferences.edit();
                                    prefs_edit.putString("log", tv_login.getText().toString());
                                    prefs_edit.putString("pw", tv_pw.getText().toString());
                                    prefs_edit.commit();
                                    log = mesPreferences.getString("log", null);
                                    Toast.makeText(getApplicationContext(), log, Toast.LENGTH_LONG).show();
                                    login();
                                } else {
                                    SharedPreferences.Editor prefs_edit = mesPreferences.edit();
                                    prefs_edit.putString(null, tv_login.getText().toString());
                                    prefs_edit.putString(null, tv_pw.getText().toString());
                                    prefs_edit.commit();
                                    tv_login.setText(null);
                                    tv_pw.setText(null);
                                    login();
                                }
                                break;
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Le mot de passe ne correspond pas", Toast.LENGTH_LONG).show();
                                }

                            }
                            else {
                                if(i==loginBDD.length-1) {
                                    Toast.makeText(getApplicationContext(), "Cet Utilisateur n'existe pas", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } }
}
