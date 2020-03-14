package com.example.myanimelistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    TextView appTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appTextView = findViewById(R.id.appTextView);

        // Set listeners to SearchView
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnSearchClickListener(v -> appTextView.setVisibility(View.INVISIBLE));
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                appTextView.setVisibility(View.VISIBLE);
                return false;
            }
        });

        // Add search bar
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        searchPlate.setBackgroundResource(R.drawable.search_query_background);

        // Change default search icon
        int searchIconId = getResources().getIdentifier("android:id/search_button", null, null);
        ImageView searchIcon = searchView.findViewById(searchIconId);
        searchIcon.setImageResource(R.drawable.ic_search_white_24dp);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        // Set drawer button
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
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
        switch (menuItem.getItemId()){
            case R.id.home: // Go to home fragment
                System.out.print("test");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerMain, new MainFragment())
                        .commit();
                break;
            case R.id.animeList: // Go to anime fragment
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerMain, new FragmentTwo())
                        .commit();
                break;
            case R.id.mangaList: // Go to manga fragment
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerMain, new FragmentThree())
                        .commit();
                break;
            case R.id.profile: // Go to profile fragment
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerMain, new FragmentFour())
                        .commit();
                break;
            case R.id.logOut: // Send Toast message
                Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show();
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
}
