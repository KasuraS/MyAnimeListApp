package com.example.myanimelistapp;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class AnimeDescActivity extends AppCompatActivity {
    public static final String TAG = "AnimeDescActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_desc);
        Log.d(TAG, "onCreated started.");

        Toolbar toolbar = findViewById(R.id.toolBar2);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        getIncomingIntent();
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");
        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("title")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String imgUrl = getIntent().getStringExtra("image_url");
            String title = getIntent().getStringExtra("title");
            setAnimeDescription(imgUrl, title);
        }
    }

    private void setAnimeDescription(String imgUrl, String title){
        TextView animeTitle = findViewById(R.id.animeTitle);
        animeTitle.setText(title);

        ImageView animeImage = findViewById(R.id.animeImage);
        Glide.with(this)
                .asBitmap()
                .load(imgUrl)
                .into(animeImage);
    }
}
