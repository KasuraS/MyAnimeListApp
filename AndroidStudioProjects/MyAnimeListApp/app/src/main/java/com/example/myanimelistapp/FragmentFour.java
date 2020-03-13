package com.example.myanimelistapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.github.doomsdayrs.jikan4java.core.Connector;
import com.github.doomsdayrs.jikan4java.types.main.user.User;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FragmentFour extends Fragment {

    private User user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four,container,false);

        user = getUserByUsername("KasuraS");

        TextView username = (TextView) view.findViewById(R.id.user_name);
        username.setText(user.username);

        TextView last_online = (TextView) view.findViewById(R.id.user_last_online);
        username.setText(user.last_online);

        ImageView user_image = (ImageView) view.findViewById(R.id.user_img);

        Glide.with(getActivity())
                .asBitmap()
                .load(user.image_url)
                .into(user_image);

        return view;
    }

    private User getUserByUsername(String username) {
        try{
            User user = new Connector().userRetrieve(username).get();
            return user;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
