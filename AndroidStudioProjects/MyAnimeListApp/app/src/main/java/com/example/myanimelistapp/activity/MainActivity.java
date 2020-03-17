package com.example.myanimelistapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myanimelistapp.fragment.FragmentFour;
import com.example.myanimelistapp.fragment.FragmentThree;
import com.example.myanimelistapp.fragment.FragmentTwo;
import com.example.myanimelistapp.fragment.MainFragment;
import com.example.myanimelistapp.R;
import com.google.android.material.navigation.NavigationView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private TextView appTextView;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Deserialize();
        } catch (IOException | ClassNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_main);
        appTextView = findViewById(R.id.appTextView);

        // Set listeners to SearchView
        searchView = findViewById(R.id.searchView);
        searchView.setOnSearchClickListener(v -> appTextView.setVisibility(View.INVISIBLE));
        searchView.setOnCloseListener(() -> {
            appTextView.setVisibility(View.VISIBLE);
            return false;
        });
        searchView.setVisibility(View.INVISIBLE); // Hidden at the start

        // Add search bar
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        searchPlate.setBackgroundResource(R.drawable.search_query_background);

        // Change default search icon
        int searchIconId = getResources().getIdentifier("android:id/search_button", null, null);
        ImageView searchIcon = searchView.findViewById(searchIconId);
        searchIcon.setImageResource(R.drawable.ic_search_white_24dp);

        // Set toolbar as support action bar
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        // Set navigation listener
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        // Set menu options at the start of the activity
        Menu menuNav = navigationView.getMenu();
        MenuItem logInItem = menuNav.findItem(R.id.profile);
        MenuItem logOutItem = menuNav.findItem(R.id.logOut);
        logInItem.setTitle(R.string.login);
        logOutItem.setVisible(false);

        // Set drawer button
        drawerLayout = findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        {
            public void onDrawerOpened(View view){
                super.onDrawerOpened(view);
                // When logged in
                if(FragmentFour.getUser()!=null) {
                    logInItem.setTitle(R.string.profile);
                    logOutItem.setVisible(true);
                }
            }
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                // When logged out
                if(FragmentFour.getUser()==null) {
                    logInItem.setTitle(R.string.login);
                    logOutItem.setVisible(false);
                }
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);

        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerMain, new MainFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        searchView.setQuery("", false);
        searchView.setFocusable(false);
        searchView.setIconified(true);
        searchView.clearFocus();
        switch (menuItem.getItemId()){
            case R.id.home: // Go to main fragment / home
                searchView.setVisibility(View.INVISIBLE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerMain, new MainFragment())
                        .commit();
                break;
            case R.id.animeList: // Go to anime fragment
                searchView.setVisibility(View.VISIBLE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerMain, new FragmentTwo())
                        .commit();
                break;
            case R.id.mangaList: // Go to manga fragment
                searchView.setVisibility(View.VISIBLE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerMain, new FragmentThree())
                        .commit();
                break;
            case R.id.profile: // Go to profile fragment
                searchView.setVisibility(View.INVISIBLE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerMain, new FragmentFour())
                        .commit();
                break;
            case R.id.logOut: // Log out profile
                // Re-init
                FragmentFour.setUser(null);
                FragmentFour.setUsername(null);
                TextView username_in_header = findViewById(R.id.username_in_header);
                username_in_header.setText(R.string.admin);
                ImageView profile_photo_in_header = findViewById(R.id.profile_photo_in_header);
                profile_photo_in_header.setImageResource(R.mipmap.ic_launcher);
                // Go to main fragment / home
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerMain, new MainFragment())
                        .commit();
                Toast.makeText(this, R.string.loggedout, Toast.LENGTH_SHORT).show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void Deserialize() throws IOException, ClassNotFoundException {
        FileInputStream fis = openFileInput("user.txt");
        ObjectInputStream is = new ObjectInputStream(fis);
        FragmentFour.setUsername((String) is.readObject());
        is.close();
        fis.close();
    }
}
