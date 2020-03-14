package com.example.myanimelistapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.doomsdayrs.jikan4java.core.search.TopSearch;
import com.github.doomsdayrs.jikan4java.enums.top.Tops;
import com.github.doomsdayrs.jikan4java.exceptions.IncompatibleEnumException;
import com.github.doomsdayrs.jikan4java.types.main.top.Top;
import com.github.doomsdayrs.jikan4java.types.main.top.TopListing;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FragmentThree extends Fragment{
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_three,container,false);

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
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), retrieveTrending(), this);
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_manga);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private ArrayList<TopListing> retrieveTrending() throws
            IncompatibleEnumException, ExecutionException, InterruptedException {
        CompletableFuture<Top> core = new TopSearch().searchTop(Tops.MANGA);
        int a = 0;
        while(!core.isDone())a++;
        Top result = core.get();
        return result.topListings; // Gets the top ranking mangas
    }
}
