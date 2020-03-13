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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.doomsdayrs.jikan4java.core.search.TopSearch;
import com.github.doomsdayrs.jikan4java.core.search.animemanga.AnimeSearch;
import com.github.doomsdayrs.jikan4java.enums.top.Tops;
import com.github.doomsdayrs.jikan4java.exceptions.IncompatibleEnumException;
import com.github.doomsdayrs.jikan4java.types.main.anime.animePage.AnimePage;
import com.github.doomsdayrs.jikan4java.types.main.anime.animePage.AnimePageAnime;
import com.github.doomsdayrs.jikan4java.types.main.manga.mangapage.MangaPageManga;
import com.github.doomsdayrs.jikan4java.types.main.top.Top;
import com.github.doomsdayrs.jikan4java.types.main.top.TopListing;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FragmentTwo extends Fragment/*, AppCompatActivity*/ {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_two,container,false);

        try {
            initRecyclerView();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IncompatibleEnumException e) {
            e.printStackTrace();
        }

        return view;
    }

    private void initRecyclerView() throws InterruptedException, ExecutionException, IncompatibleEnumException {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}

