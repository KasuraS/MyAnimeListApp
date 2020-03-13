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

import com.github.doomsdayrs.jikan4java.exceptions.IncompatibleEnumException;
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

