package com.example.louis.musicplayertest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.louis.musicplayertest.Fragment.InscriptionFragment;
import com.example.louis.musicplayertest.pojo.User;


public class LoginActivity extends Activity {
    InscriptionFragment inscription_frag = new InscriptionFragment();
    private static Context sContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sContext = getApplicationContext();
        setContentView(R.layout.activity_login);


        final SharedPreferences mesPreferences = getApplicationContext().getSharedPreferences("com.example.projet2.log", Context.MODE_PRIVATE);

        String log = mesPreferences.getString("log", null);
        String pw = mesPreferences.getString("pw", null);

        final TextView tv_login = (TextView)findViewById(R.id.loginText);
        final TextView tv_pw = (TextView)findViewById(R.id.passwordText);

        Button loginButton = findViewById(R.id.loginButton);
        final Switch loginSwitch = findViewById(R.id.loginSwitch);
        Button subscribeButton = findViewById(R.id.inscription);
        final Button inscrireButton = findViewById(R.id.inscrire);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(((TextView) findViewById(R.id.loginText)).getText())) {
                    Toast.makeText(getApplicationContext(), "Entrez un login", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(((TextView) findViewById(R.id.passwordText)).getText())) {
                    Toast.makeText(getApplicationContext(), "Entrez un mot de passe", Toast.LENGTH_LONG).show();
                } else {
                    //Enregistrement du login et du password
                    if(loginSwitch.isChecked()){
                        SharedPreferences.Editor prefs_edit = mesPreferences.edit();
                        prefs_edit.putString("log", tv_login.getText().toString());
                        prefs_edit.putString("pw", tv_pw.getText().toString());
                        prefs_edit.commit();
                        String log = mesPreferences.getString("log", null);
                        Toast.makeText(getApplicationContext(), log, Toast.LENGTH_LONG).show();
                        login();
                    }
                    else{
                        SharedPreferences.Editor prefs_edit = mesPreferences.edit();
                        prefs_edit.putString(null, tv_login.getText().toString());
                        prefs_edit.putString(null, tv_pw.getText().toString());
                        prefs_edit.commit();
                        tv_login.setText(null);
                        tv_pw.setText(null);
                        login();
                    }

                }

            }
        });

        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

}