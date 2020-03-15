package com.example.myanimelistapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

public class MangaDescActivity extends AppCompatActivity {
    public static final String TAG = "MangaDescActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_desc);
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
            setMangaDescription(imgUrl, title);
        }
    }

    private void setMangaDescription(String imgUrl, String title){
        TextView mangaTitle = findViewById(R.id.mangaTitle);
        mangaTitle.setText(title);

        ImageView mangaImage = findViewById(R.id.mangaImage);
        Glide.with(this)
                .asBitmap()
                .load(imgUrl)
                .into(mangaImage);
    }
}
