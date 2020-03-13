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
