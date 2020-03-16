package com.example.myanimelistapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.doomsdayrs.jikan4java.core.Connector;
import com.github.doomsdayrs.jikan4java.exceptions.IncompatibleEnumException;
import com.github.doomsdayrs.jikan4java.types.main.anime.Anime;
import com.github.doomsdayrs.jikan4java.types.main.top.TopListing;
import com.github.doomsdayrs.jikan4java.types.main.user.listing.animelist.AnimeList;
import com.github.doomsdayrs.jikan4java.types.main.user.listing.animelist.AnimeListAnime;
import com.github.doomsdayrs.jikan4java.types.main.user.listing.mangalist.MangaList;
import com.github.doomsdayrs.jikan4java.types.main.user.listing.mangalist.MangaListManga;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> img_url = new ArrayList<>();
    private ArrayList<Integer> ids = new ArrayList<>();
    private Context mContext;
    private Class fragment;

    public RecyclerViewAdapter(Context context, ArrayList<TopListing> list, Fragment frag) {
        fragment = frag.getClass();
        mContext = context;
        for(TopListing x: list) {
            titles.add(x.title);
            img_url.add(x.image_url);
            ids.add(x.mal_id);
        }
    }

    //Constructor for personal anime list
    public RecyclerViewAdapter(Context context, AnimeList list){
        mContext = context;
        for(AnimeListAnime x : list.animes) {
            titles.add(x.title);
            img_url.add(x.image_url);
            ids.add(x.mal_id);
        }
    }

    //Constructor for personal manga list
    public RecyclerViewAdapter(Context context, MangaList list) {
        mContext = context;
        for(MangaListManga x : list.mangas) {
            titles.add(x.title);
            img_url.add(x.image_url);
            ids.add(x.mal_id);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.imageName.setText(titles.get(position));

        Glide.with(mContext)
                .asBitmap()
                .load(img_url.get(position))
                .into(holder.image);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + titles.get(position));
                Toast.makeText(mContext, titles.get(position), Toast.LENGTH_SHORT).show();

                Intent intent;
                if(fragment == FragmentTwo.class){
                    intent = new Intent(mContext, AnimeDescActivity.class);
                }
                else{
                    intent = new Intent(mContext, MangaDescActivity.class);
                }
                intent.putExtra("image_url", img_url.get(position));
                intent.putExtra("title", titles.get(position));
                intent.putExtra("ID", ids.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView imageName;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
