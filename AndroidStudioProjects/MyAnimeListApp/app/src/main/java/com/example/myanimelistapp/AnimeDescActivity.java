package com.example.myanimelistapp;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ImageView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.doomsdayrs.jikan4java.core.Connector;
import com.github.doomsdayrs.jikan4java.types.main.anime.Anime;
import com.github.doomsdayrs.jikan4java.types.main.anime.Studios;
import com.github.doomsdayrs.jikan4java.types.support.basic.meta.Genre;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("title") && getIntent().hasExtra("ID")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String imgUrl = getIntent().getStringExtra("image_url");
            String title = getIntent().getStringExtra("title");
            int id = getIntent().getIntExtra("ID", 0);
            setAnimeDescription(imgUrl, title, id);
        }
    }

    private void setAnimeDescription(String imgUrl, String title, int id){
        TextView animeTitle = findViewById(R.id.animeTitle);
        animeTitle.setText(title);

        ImageView animeImage = findViewById(R.id.animeImage);
        Glide.with(this)
                .asBitmap()
                .load(imgUrl)
                .into(animeImage);

        Anime anime = getAnimeByID(id);

        ArrayList<String> elements = new ArrayList<>();
        elements.add("Original title: " + anime.title_japanese);
        elements.add("Type: " + anime.type);
        elements.add("Source: " + anime.source);
        elements.add("Episodes: " + anime.episodes);
        elements.add("Status: " + anime.status);
        elements.add("Date: " + anime.premiered);
        elements.add("Score: " + anime.score);
        elements.add("Rank " + anime.rank);
        elements.add("Popularity: " + anime.popularity);
        elements.add("Members: " + anime.members);
        elements.add("Favorites: " + anime.favorites);
        String genres = "";
        for(Genre genre: anime.genres) {
            genres = genres.concat(genre.name + " ");
            System.out.println(genre.name);
        }
        String studios = "";
        for(Studios studio: anime.studios) {
            studios = studios.concat(studio.name + " ");
        }
        elements.add("Genres: " + genres);
        elements.add("Studios: " + studios);

        ListView descriptions = findViewById(R.id.description_list_anime);
        ArrayAdapter ad_anime = new ArrayAdapter(this, android.R.layout.simple_list_item_1, elements);
        descriptions.setAdapter(ad_anime);
    }

    private Anime getAnimeByID(int id) {
        try
        {
            Anime anime = new Connector().retrieveAnime(id).get();
            return anime;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
