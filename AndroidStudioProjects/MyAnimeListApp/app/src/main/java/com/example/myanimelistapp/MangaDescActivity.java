package com.example.myanimelistapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.github.doomsdayrs.jikan4java.core.Connector;
import com.github.doomsdayrs.jikan4java.types.main.manga.Manga;
import com.github.doomsdayrs.jikan4java.types.main.manga.Serializations;
import com.github.doomsdayrs.jikan4java.types.support.basic.meta.Authors;
import com.github.doomsdayrs.jikan4java.types.support.basic.meta.Genre;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("title") && getIntent().hasExtra("animeID")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String imgUrl = getIntent().getStringExtra("image_url");
            String title = getIntent().getStringExtra("title");
            int id = getIntent().getIntExtra("animeID", 0);
            setMangaDescription(imgUrl, title, id);
        }
    }

    private void setMangaDescription(String imgUrl, String title, int id){
        TextView mangaTitle = findViewById(R.id.mangaTitle);
        mangaTitle.setText(title);

        ImageView mangaImage = findViewById(R.id.mangaImage);
        Glide.with(this)
                .asBitmap()
                .load(imgUrl)
                .into(mangaImage);

        Manga manga = getMangaByID(id);

        ArrayList<String> elements = new ArrayList<>();
        elements.add("Original title: " + manga.title_japanese);
        elements.add("Type: " + manga.type);
        String authors = "";
        for(Authors author: manga.authors)
            authors = authors.concat(author.name);
        elements.add("Authors: " + authors);
        elements.add("Chapters: " + (manga.chapters ==  0 ? "In Progress" : manga.chapters));
        elements.add("Volumes: " + (manga.volumes ==  0 ? "In Progress" : manga.volumes));
        elements.add("Status: " + manga.status);
        elements.add("Date: " + manga.published.from.substring(0, 10));

        String genres = "";
        for(Genre genre: manga.genres)
            genres = genres.concat(genre.name + " - ");
        if(!genres.isEmpty())
            genres = genres.substring(0,genres.length()-2);
        elements.add("Genres: " + (genres == "" ? "N/A" : genres));

        String serializations = "";
        for(Serializations serialization: manga.serializations)
            serializations = serializations.concat(serialization.name + " - ");
        if(!serializations.isEmpty())
            serializations = serializations.substring(0,serializations.length()-2);
        elements.add("Publisher: " + (serializations == "" ? "N/A" : serializations));

        elements.add("Score: " + (manga.score == 0 ? "N/A" : manga.score));
        elements.add("Rank " + (manga.rank == 0 ? "N/A" : manga.rank));
        elements.add("Popularity: " + manga.popularity);
        elements.add("Members: " + manga.members);
        elements.add("Favorites: " + manga.favorites);


        ListView descriptions = findViewById(R.id.description_list_manga);
        ArrayAdapter ad_anime = new ArrayAdapter(this, android.R.layout.simple_list_item_1, elements);
        descriptions.setAdapter(ad_anime);
    }

    private Manga getMangaByID(int id) {
        try
        {
            Manga manga = new Connector().retrieveManga(id).get();
            return manga;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
