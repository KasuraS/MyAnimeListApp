package com.example.myanimelistapp;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("title") && getIntent().hasExtra("animeID")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String imgUrl = getIntent().getStringExtra("image_url");
            String title = getIntent().getStringExtra("title");
            int id = getIntent().getIntExtra("animeID", 0);
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
        elements.add("Episodes: " + (anime.episodes == 0 ? "In Progress" : anime.episodes));
        elements.add("Status: " + anime.status);
        elements.add("Date: " + (anime.premiered == null ? "N/A" : anime.premiered));

        String genres = "";
        for(Genre genre: anime.genres)
            genres = genres.concat(genre.name + " - ");
        if(!genres.isEmpty())
            genres = genres.substring(0,genres.length()-2);
        elements.add("Genres: " + (genres == "" ? "N/A" : genres));

        String studios = "";
        for(Studios studio: anime.studios)
            studios = studios.concat(studio.name + " - ");
        if(!studios.isEmpty())
            studios = studios.substring(0,studios.length()-2);
        elements.add("Studios: " + (studios == "" ? "N/A" : studios));

        elements.add("Score: " + (anime.score == 0 ? "N/A" : anime.score));
        elements.add("Rank " + (anime.rank == 0 ? "N/A" : anime.rank));
        elements.add("Popularity: " + anime.popularity);
        elements.add("Members: " + anime.members);
        elements.add("Favorites: " + anime.favorites);

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
