package com.example.myanimelistapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.github.doomsdayrs.jikan4java.core.search.TopSearch;
import com.github.doomsdayrs.jikan4java.core.search.animemanga.AnimeSearch;
import com.github.doomsdayrs.jikan4java.enums.top.Tops;
import com.github.doomsdayrs.jikan4java.types.main.anime.animePage.AnimePage;
import com.github.doomsdayrs.jikan4java.types.main.anime.animePage.AnimePageAnime;
import com.github.doomsdayrs.jikan4java.types.main.manga.mangapage.MangaPageManga;
import com.github.doomsdayrs.jikan4java.types.main.top.Top;
import com.github.doomsdayrs.jikan4java.types.main.top.TopListing;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FragmentTwo extends Fragment/*, AppCompatActivity*/ {
    ListView animeListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two,container,false);

        //Anime search function
        /*AnimeSearch core = new AnimeSearch().setQuery("boku");
        core.setLimit(10);

        CompletableFuture completableFuture = core.get();
        int a = 0;
        while(!completableFuture.isDone())a++;

        List<AnimePageAnime> animeList;

        try{
            AnimePage resultPage = (AnimePage) completableFuture.get();
            animeList = resultPage.animes;

            final ArrayAdapter<AnimePageAnime> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, animeList);
            animeListView.setAdapter(adapter);

            for(AnimePageAnime x : resultPage.animes) {
                System.out.println(x);
            }
        }
        catch (Exception err){

        }*/
        return view;
    }
}

