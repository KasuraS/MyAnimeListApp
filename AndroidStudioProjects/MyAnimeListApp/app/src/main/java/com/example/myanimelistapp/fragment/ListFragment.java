package com.example.myanimelistapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myanimelistapp.R;
import com.example.myanimelistapp.adapter.AnimeListAdapter;
import com.example.myanimelistapp.adapter.MangaListAdapter;
import com.github.doomsdayrs.jikan4java.core.userlisting.AnimeUserListingSearch;
import com.github.doomsdayrs.jikan4java.core.userlisting.MangaUserListingSearch;
import com.github.doomsdayrs.jikan4java.types.main.user.listing.animelist.AnimeList;
import com.github.doomsdayrs.jikan4java.types.main.user.listing.mangalist.MangaList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ListFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        super.onCreate(savedInstanceState);

        TabHost tabs = view.findViewById(R.id.tabhost);
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
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tabs.setOnTabChangedListener(tabId -> {
            Log.d("ListFragment","Current tab:"+tabId);
            if(tabId == "tag1") {
                try {
                    anime_initRecyclerView();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    manga_initRecycleView();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

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

    private void anime_initRecyclerView() throws ExecutionException, InterruptedException {
        AnimeListAdapter adapter = new AnimeListAdapter(getContext(), retrieveAnimeList());
        RecyclerView recyclerView = view.findViewById(R.id.tab_anime_recycleView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SearchView searchView = getActivity().findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.isEmpty()){
                    adapter.getFilter().filter(query);
                    int count = (adapter == null ? 0 : adapter.getItemCount());
                    Toast.makeText(getContext(), "Found "+count+" result(s).", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void manga_initRecycleView() throws ExecutionException, InterruptedException {
        MangaListAdapter adapter = new MangaListAdapter(getContext(), retrieveMangaList());
        RecyclerView recyclerView = view.findViewById(R.id.tab_manga_recycleView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SearchView searchView = getActivity().findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.isEmpty()){
                    adapter.getFilter().filter(query);
                    int count = (adapter == null ? 0 : adapter.getItemCount());
                    Toast.makeText(getContext(), "Found "+count+" result(s).", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}
