package com.example.louis.musicplayertest;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SeekBar;


import com.example.louis.musicplayertest.Adapter.SlideAdapter;
import com.example.louis.musicplayertest.Model.Slide;
import com.example.louis.musicplayertest.StaticClass.*;


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



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewSliding=(ListView) findViewById(R.id.lv_sliding_menu);
        drawerLayout=findViewById(R.id.mainActivity);
        listSliding=new ArrayList<>();

        listSliding.add(new Slide("BlanckFragment"));
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

        //drawerLayout.setDrawerListener(actionBarDrawerToggle);

        try{
            System.out.println("Demande d'accès à la mémoire du telephone");
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                //ask for permission
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }

            String addr = "";

            System.out.println("Recherches des musiques:");

            searchMusicFiles("/storage");
            searchMusicFiles("/sdcard");
            System.out.println(songs.size() + "musiques trouvées.");

        }catch(Exception e){e.printStackTrace();}

     /*   final SearchView searchView = findViewById(R.id.searchBox);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                for (Song s : songs){
                    if (s.getName().contains(newText)){
                        System.out.println(s.getName());
                    }
                }
                return false;
            }
        });*/

 /*       final Button buttonLecteur = findViewById(R.id.buttonLecteur);
        buttonLecteur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment, Player.getInstance());
                transaction.commit();
            }
        });

        final Button buttonListSong = findViewById(R.id.buttonListSong);
        buttonListSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment, ListSong.getInstance());
                transaction.commit();
            }
        });
        final Button buttonCombined = findViewById(R.id.buttonCombined);
        buttonCombined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = manager.beginTransaction();
                transaction.remove(Player.getInstance());
                transaction.remove(ListSong.getInstance());
                transaction.commit();
                Intent i = new Intent(MainActivity.this, MainCombined.class);
                startActivity(i);
            }
        });
*/



    }

    private void replaceFragment(int position){
        Fragment fragment=null;

        switch(position){
            case 1:
                fragment=Player.getInstance();
                break;
            case 2:
                fragment = ListSong.getInstance();
                break;
            case 3:
                manager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                transaction = manager.beginTransaction();
                transaction.remove(Player.getInstance());

                manager.executePendingTransactions();
                transaction.remove(ListSong.getInstance());

                manager.executePendingTransactions();
                transaction.commit();
                Intent i = new Intent(MainActivity.this, MainCombined.class);
                startActivity(i);
                break;
            default:
                break;




                }
            if (null!=fragment && position!=3){
                manager=getFragmentManager();
                transaction=manager.beginTransaction();
                transaction.replace(R.id.fragment,fragment);
                transaction.addToBackStack(null).commit();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.fragment, Player.getInstance());
        transaction.commit();
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
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