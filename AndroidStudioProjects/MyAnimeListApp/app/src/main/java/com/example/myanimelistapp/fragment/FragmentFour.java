package com.example.myanimelistapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myanimelistapp.R;
import com.github.doomsdayrs.jikan4java.core.Connector;
import com.github.doomsdayrs.jikan4java.types.main.user.User;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FragmentFour extends Fragment implements View.OnClickListener{

    protected static User user;
    protected static String username;
    private Button mButton;
    private Button ListButton;
    private boolean isClicked;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four,container,false);
        View not_found = inflater.inflate(R.layout.not_found,container, false);

        mButton = not_found.findViewById(R.id.button_username_query);
        mButton.setOnClickListener(this);
        System.out.println(isClicked);
        if(username == null){
            if(isClicked == true)
                Toast.makeText(getContext(), R.string.usernameIsNull, Toast.LENGTH_SHORT).show();
            isClicked = false;
            return not_found;
        }

        user = getUserByUsername(username);

        if(user == null) {
            Toast.makeText(getContext(), R.string.userNotFound, Toast.LENGTH_SHORT).show();
            return not_found;
        } else {
            Toast.makeText(getContext(), R.string.loggedin, Toast.LENGTH_SHORT).show();
        }

        // Set header info
        TextView username_in_header = getActivity().findViewById(R.id.username_in_header);
        username_in_header.setText(user.username);
        ImageView profile_photo_in_header = getActivity().findViewById(R.id.profile_photo_in_header);
        Glide.with(getActivity())
                .asBitmap()
                .load(user.image_url)
                .into(profile_photo_in_header);

        // Set user info
        TextView username = view.findViewById(R.id.user_name);
        username.setText(user.username);

        TextView last_online = view.findViewById(R.id.user_last_online);
        last_online.setText("Last Online: "+user.last_online);

        ImageView user_image = view.findViewById(R.id.user_img);
        Glide.with(getActivity())
                .asBitmap()
                .load(user.image_url)
                .into(user_image);

        TextView user_gender = view.findViewById(R.id.user_gender);
        if(user.gender == null){
            user_gender.setText("Gender: Genderless");
        } else {
            user_gender.setText("Gender: " + user.gender);
        }

        TextView user_location = view.findViewById(R.id.user_location);
        if(user.location == null){
            user_location.setText("Current location: Isekai");
        } else {
            user_location.setText("Current location: " + user.location);
        }

        // Anime/Manga stats
        ListView anime_manga_stats = view.findViewById(R.id.anime_manga_stats);
        ArrayList<String> array_anime_manga_stats = new ArrayList<>();
        array_anime_manga_stats.add("Finished: " + user.animeStats.get(0).watching + " anime(s) --- "
                + user.mangaStats.get(0).reading + " manga(s)");
        array_anime_manga_stats.add("Cleared: " + user.animeStats.get(0).completed + " anime(s) --- "
                + user.mangaStats.get(0).completed + " manga(s)");
        array_anime_manga_stats.add("Dropped: " + user.animeStats.get(0).dropped + " anime(s) --- "
                + user.mangaStats.get(0).dropped + " manga(s)");
        array_anime_manga_stats.add("On Hold: " + user.animeStats.get(0).on_hold + " anime(s) --- "
                + user.mangaStats.get(0).on_hold + " manga(s)");
        array_anime_manga_stats.add("Planned: " + user.animeStats.get(0).plan_to_watch + " anime(s) --- "
                + user.mangaStats.get(0).plan_to_read + " manga(s)");

        anime_manga_stats.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, array_anime_manga_stats));
        anime_manga_stats.setEnabled(false);

        // Set listeners to List button
        ListButton = view.findViewById(R.id.button_list);
        ListButton.setOnClickListener(this);

        return view;
    }

    private User getUserByUsername(String username){
        try{
            User user = new Connector().userRetrieve(username).get();
            if(user != null)
                this.Serialize();
            return user;
        } catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User u) {
        user = u;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String name) {
        username = name;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_username_query:
                isClicked = true;
                EditText editText = getActivity().findViewById(R.id.username_query);
                String name_string = editText.getText().toString();
                username = name_string;
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerMain, new FragmentFour())
                        .commit();
                break;
            case R.id.button_list:
                SearchView searchView = getActivity().findViewById(R.id.searchView);
                searchView.setVisibility(View.VISIBLE);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerMain, new ListFragment())
                        .commit();
                break;
        }
    }

    public void Serialize() {
        FileOutputStream fos;
        try {
            fos = getActivity().openFileOutput("user.txt", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(FragmentFour.username);
            os.flush();
            fos.flush();
            os.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
