package com.example.myanimelistapp;

import android.app.ListActivity;
import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.github.doomsdayrs.jikan4java.core.Connector;
import com.github.doomsdayrs.jikan4java.types.main.user.User;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FragmentFour extends Fragment implements View.OnClickListener{

    protected static User user;
    protected static String username;
    private View view;
    private Button mButton;
    private Button AnimeListButton;
    private Button MangaListButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_four,container,false);
        View not_found = inflater.inflate(R.layout.not_found, container, false);

        //prevent username for being null
        if(username == null){
            mButton = not_found.findViewById(R.id.button_username_query);
            mButton.setOnClickListener(this);
            return not_found;
        }

        user = getUserByUsername(username);

        if(user == null) {
            mButton = not_found.findViewById(R.id.button_username_query);
            mButton.setOnClickListener(this);
            return not_found;
        }

        AnimeListButton = view.findViewById(R.id.button_to_animelist);
        AnimeListButton.setOnClickListener(this);

        TextView username = (TextView) view.findViewById(R.id.user_name);
        username.setText(user.username);

        TextView last_online = (TextView) view.findViewById(R.id.user_last_online);
        username.setText(user.last_online);

        ImageView user_image = (ImageView) view.findViewById(R.id.user_img);

        Glide.with(getActivity())
                .asBitmap()
                .load(user.image_url)
                .into(user_image);

        TextView user_gender = (TextView) view.findViewById(R.id.user_gender);
        user_gender.setText("Gender: " + user.gender);

        TextView user_location = (TextView) view.findViewById(R.id.user_location);
        if(user.location == null){
            user_location.setText("Current location : Isekai");
        } else {
            user_location.setText("Current location: " + user.location);
        }

        // Anime stats
        ListView anime_stats = (ListView) view.findViewById(R.id.user_anime_stats);
        ArrayList<String> array_anime_stats = new ArrayList<>();
        array_anime_stats.add("Watching: " + user.animeStats.get(0).watching);
        array_anime_stats.add("Completed: " + user.animeStats.get(0).completed);
        array_anime_stats.add("Dropped: " + user.animeStats.get(0).dropped);
        array_anime_stats.add("On hold: " + user.animeStats.get(0).on_hold);
        array_anime_stats.add("PTW :" + user.animeStats.get(0).plan_to_watch);

        ArrayAdapter ad_anime = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, array_anime_stats);
        anime_stats.setAdapter(ad_anime);

        //Manga stats
        ListView manga_stats = (ListView) view.findViewById(R.id.user_manga_stats);
        ArrayList<String> array_manga_stats = new ArrayList<>();
        array_manga_stats.add("Reading: " + user.mangaStats.get(0).reading);
        array_manga_stats.add("Completed: " + user.mangaStats.get(0).completed);
        array_manga_stats.add("Dropped: " + user.mangaStats.get(0).dropped);
        array_manga_stats.add("On hold: " + user.mangaStats.get(0).on_hold);
        array_manga_stats.add("PTR :" + user.mangaStats.get(0).plan_to_read);

        ArrayAdapter ad_manga = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, array_manga_stats);
        manga_stats.setAdapter(ad_manga);

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        username = name;
    }


    @Override
    public void onClick(View v) {
        FragmentTransaction ft;
        switch (v.getId()) {
            case R.id.button_username_query:
                EditText name = getActivity().findViewById(R.id.username_query);
                String name_string = name.getText().toString();
                username = name_string;
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                ft =  getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
                break;
            case R.id.button_to_animelist:
                ft =  getFragmentManager().beginTransaction();
                ft.replace(R.id.containerMain, new ListFragment()).commit();
                break;
        }
    }
}
