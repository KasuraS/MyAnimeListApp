package com.example.myanimelistapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {
    private onFragmentBtnSelected listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        Button btn = view.findViewById(R.id.loadBtn);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                listener.onButtonSelected();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context ctx){
        super.onAttach(ctx);
        if(ctx instanceof onFragmentBtnSelected) {
            listener = (onFragmentBtnSelected) ctx;
        }
        else{
            throw new ClassCastException(ctx.toString() + " must implement listener");
        }
    }

    public interface onFragmentBtnSelected{
        void onButtonSelected();
    }
}
