package com.example.louis.musicplayertest;

import android.Manifest;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SeekBar;


import com.example.louis.musicplayertest.Adapter.SlideAdapter;
import com.example.louis.musicplayertest.Adapter.SongAdapter;

import com.example.louis.musicplayertest.Fragment.BlankFragment;
import com.example.louis.musicplayertest.Fragment.BlankFragment2;
import com.example.louis.musicplayertest.Model.Slide;
import com.example.louis.musicplayertest.StaticClass.*;
import com.example.louis.musicplayertest.async.RechercheMusique;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1524515;

    public static List<Song> songs = new ArrayList<Song>();

    public FragmentTransaction transaction;
    public android.app.FragmentManager manager;



    private List<Slide> listSliding;
    private SlideAdapter adapter;
    private ListView listViewSliding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;



    private static Context sContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sContext = getApplicationContext();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getFragmentManager().beginTransaction().add(R.id.container, fragmentatt).commit();

        listViewSliding=(ListView) findViewById(R.id.lv_sliding_menu);
        drawerLayout=findViewById(R.id.mainActivity);
        listSliding=new ArrayList<>();

        listSliding.add(new Slide("Player"));
        listSliding.add(new Slide("Mes musiques"));
        listSliding.add(new Slide("Liste de lecture"));
        adapter = new SlideAdapter(this,listSliding);
        listViewSliding.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(listSliding.get(0).getTitle());
        listViewSliding.setItemChecked(0,true);
        drawerLayout.closeDrawer(listViewSliding);

        listViewSliding.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                setTitle(listSliding.get(position).getTitle());
                listViewSliding.setItemChecked(position,true);
                replaceFragment(position);
                drawerLayout.closeDrawer(listViewSliding);
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_opened,R.string.drawer_closed){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

        };




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    private void replaceFragment(int position){
        Fragment fragment=null;

        switch(position){

            case 0:
                fragment=Player.getInstance();
                break;
            case 1:
                fragment = ListSong.getInstance();
                break;
            case 2:
                transaction = manager.beginTransaction();
                transaction.remove(Player.getInstance());
                transaction.remove(ListSong.getInstance());
                transaction.commit();
                Intent i = new Intent(MainActivity.this, MainCombined.class);
                startActivity(i);
                break;
            default:
                break;
                }

            if (null!=fragment && position!=2){
                manager=getFragmentManager();
                transaction=manager.beginTransaction();
                transaction.replace(R.id.fragment,fragment);
                transaction.commit();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.fragment, ListSong.getInstance()).commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.remove(Player.getInstance());
        transaction.remove(ListSong.getInstance());

        transaction.commit();
    }

    private boolean searchMusicFiles(String path) {
        System.out.println("Recherche dans : "+path);

        File directory = new File(path);

        try {
            for (File f : directory.listFiles()) {
                if (f.isDirectory()) {
                    try {
                        searchMusicFiles(f.getPath());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (f.getPath().endsWith(".mp3")) {
                        System.out.println(f.getPath());
                        songs.add(new Song(f));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        if (item.getItemId()==R.id.Déconnexion){
            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    public static Context getContext() {
        return sContext;
    }

}


/*XML mainActivity
          <Button
            android:id="@+id/buttonLecteur"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="Player" />

        <Button
            android:id="@+id/buttonListSong"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="Listes Song" />

        <Button
            android:id="@+id/buttonCombined"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="Combiné" />


            */