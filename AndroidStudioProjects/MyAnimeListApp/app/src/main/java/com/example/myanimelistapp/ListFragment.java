package com.example.myanimelistapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.doomsdayrs.jikan4java.core.userlisting.AnimeUserListingSearch;
import com.github.doomsdayrs.jikan4java.core.userlisting.MangaUserListingSearch;
import com.github.doomsdayrs.jikan4java.exceptions.IncompatibleEnumException;
import com.github.doomsdayrs.jikan4java.types.main.manga.Manga;
import com.github.doomsdayrs.jikan4java.types.main.user.listing.animelist.AnimeList;
import com.github.doomsdayrs.jikan4java.types.main.user.listing.animelist.AnimeListAnime;
import com.github.doomsdayrs.jikan4java.types.main.user.listing.mangalist.MangaList;
import com.github.doomsdayrs.jikan4java.types.main.user.listing.mangalist.MangaListManga;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ListFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);

        //Copy FragmentFour code

        super.onCreate(savedInstanceState);
        TabHost tabs = (TabHost) view.findViewById(R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("tag1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("ANIME");
        tabs.addTab(spec);
        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("MANGA");
        tabs.addTab(spec);

        try {
            anime_initRecyclerView();
            manga_initRecycleView();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IncompatibleEnumException e) {
            e.printStackTrace();
        }

        return view;
    }

    private AnimeList retrieveAnimeList() throws ExecutionException, InterruptedException {
        AnimeUserListingSearch core = FragmentFour.user.getAnimeListSearch();

        CompletableFuture completableFuture = core.getList();
        int a = 0;
        while(!completableFuture.isDone())a++;
        AnimeList result = (AnimeList) completableFuture.get();

        return result;
    }

    private MangaList retrieveMangaList() throws ExecutionException, InterruptedException {
        MangaUserListingSearch core = FragmentFour.user.getMangaListSearch();

        CompletableFuture completableFuture = core.getList();
        int a = 0;
        while(!completableFuture.isDone())a++;
        MangaList result = (MangaList) completableFuture.get();

        return result;
    }

    private void anime_initRecyclerView() throws ExecutionException, InterruptedException, IncompatibleEnumException {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), retrieveAnimeList());
        RecyclerView recyclerView = view.findViewById(R.id.tab_anime_recycleView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void manga_initRecycleView() throws ExecutionException, InterruptedException, IncompatibleEnumException {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), retrieveMangaList());
        RecyclerView recyclerView = view.findViewById(R.id.tab_manga_recycleView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


}
