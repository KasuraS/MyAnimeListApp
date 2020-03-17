package com.example.myanimelistapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myanimelistapp.R;
import com.example.myanimelistapp.adapter.TopListAdapter;
import com.github.doomsdayrs.jikan4java.core.search.TopSearch;
import com.github.doomsdayrs.jikan4java.enums.top.Tops;
import com.github.doomsdayrs.jikan4java.exceptions.IncompatibleEnumException;
import com.github.doomsdayrs.jikan4java.types.main.top.Top;
import com.github.doomsdayrs.jikan4java.types.main.top.TopListing;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FragmentTwo extends Fragment{
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_two,container,false);

        try {
            initRecyclerView();
        } catch (InterruptedException | ExecutionException | IncompatibleEnumException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void initRecyclerView() throws InterruptedException, ExecutionException, IncompatibleEnumException {
        TopListAdapter adapter = new TopListAdapter(getContext(), retrieveTrending(), this);
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_anime);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        filtrate(adapter);
    }

    private ArrayList<TopListing> retrieveTrending() throws IncompatibleEnumException, ExecutionException, InterruptedException {
        CompletableFuture<Top> core = new TopSearch().searchTop(Tops.ANIME);
        int a = 0;
        while(!core.isDone())a++;
        Top result = core.get();
        return result.topListings; // Gets the top ranking animes
    }

    private void filtrate(TopListAdapter adapter) {
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

