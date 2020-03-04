package com.example.myanimelistapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.doomsdayrs.jikan4java.core.search.TopSearch;
import com.github.doomsdayrs.jikan4java.enums.top.Tops;
import com.github.doomsdayrs.jikan4java.types.main.top.Top;
import com.github.doomsdayrs.jikan4java.types.main.top.TopListing;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class MainFragment extends Fragment {
    private onFragmentBtnSelected listener;
    private ListView topAnimeListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        Button btn = view.findViewById(R.id.loadBtn);

        /*topAnimeListView = view.findViewById(R.id.animeListView);

        List<TopListing> topAnimeList;

        //Trendings parametre
        try{
            CompletableFuture<Top> core = new TopSearch().searchTop(Tops.ANIME);
            int a = 0;
            while(!core.isDone())a++;

            Top result = core.get();
            topAnimeList = result.topListings;

            final ArrayAdapter<TopListing> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, topAnimeList);
            topAnimeListView.setAdapter(adapter);

            for (TopListing x: result.topListings) {
                System.out.println("===");
                System.out.println(x.title);
                System.out.println(x.rank);
                System.out.println(x.url);
            }
        }
        catch (Exception err){
            System.out.println(err);
        }*/

        /*btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                listener.onButtonSelected();
            }
        });*/
        return view;
    }
/*
    @Override
    public void onAttach(@NonNull Context ctx){
        super.onAttach(ctx);
        if(ctx instanceof onFragmentBtnSelected) {
            listener = (onFragmentBtnSelected) ctx;
        }
        else{
            throw new ClassCastException(ctx.toString() + " must implement listener");
        }
    }*/

    public interface onFragmentBtnSelected{
        void onButtonSelected();
    }
}
