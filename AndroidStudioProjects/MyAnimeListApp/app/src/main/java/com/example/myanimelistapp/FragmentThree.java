package com.example.myanimelistapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.github.doomsdayrs.jikan4java.core.search.animemanga.MangaSearch;
import com.github.doomsdayrs.jikan4java.types.main.manga.mangapage.MangaPage;
import com.github.doomsdayrs.jikan4java.types.main.manga.mangapage.MangaPageManga;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FragmentThree extends Fragment {
    ListView mangaListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three,container,false);

        /*mangaListView = getActivity().findViewById(R.id.mangaListView);

        //Manga search function
        MangaSearch core = new MangaSearch().setQuery("boku");
        core.setLimit(10); //Set result numbers

        CompletableFuture completableFuture = core.get();
        int a = 0;
        while(!completableFuture.isDone())a++;

        List<MangaPageManga> mangaList;

        try{
            MangaPage resultPage = (MangaPage) completableFuture.get();
            mangaList = resultPage.mangas;

            final ArrayAdapter<MangaPageManga> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mangaList);
            mangaListView.setAdapter(adapter);

            for(MangaPageManga x : resultPage.mangas) {
                System.out.println(x);
            }
        }
        catch (Exception err){

        }*/
        return view;
    }
}
