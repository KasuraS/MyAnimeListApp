package com.example.myanimelistapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myanimelistapp.R;
import com.example.myanimelistapp.activity.AnimeDescActivity;
import com.github.doomsdayrs.jikan4java.types.main.user.listing.animelist.AnimeList;
import com.github.doomsdayrs.jikan4java.types.main.user.listing.animelist.AnimeListAnime;;

import java.util.ArrayList;
import java.util.List;

public class AnimeListAdapter extends RecyclerView.Adapter<AnimeListAdapter.ViewHolder> implements Filterable {
    private static final String TAG = "AnimeListAdapter";

    private List<AnimeListAnime> animeList;
    private List<AnimeListAnime> filteredAnimeList;
    private Context mContext;

    // Constructor for personal anime list
    public AnimeListAdapter(Context context, AnimeList list) {
        mContext = context;
        filteredAnimeList = list.animes;
        animeList = new ArrayList<>(filteredAnimeList);
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

        final AnimeListAnime currentItem = filteredAnimeList.get(position);

        holder.imageName.setText(currentItem.title);

        Glide.with(mContext)
                .asBitmap()
                .load(currentItem.image_url)
                .into(holder.image);

        holder.parentLayout.setOnClickListener(view -> {
            Log.d(TAG, "onClick: clicked on: " + currentItem.title);
            Toast.makeText(mContext, currentItem.title, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(mContext, AnimeDescActivity.class);
            intent.putExtra("image_url", currentItem.image_url);
            intent.putExtra("title", currentItem.title);
            intent.putExtra("mal_id", currentItem.mal_id);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return filteredAnimeList.size(); }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if(charString.isEmpty()) {
                    filteredAnimeList = animeList;
                } else {
                    ArrayList<AnimeListAnime> animes = new ArrayList<>();
                    for(AnimeListAnime x : animeList){
                        if(x.title.toLowerCase().contains(charString.toLowerCase())) {
                            animes.add(x);
                        }
                    }
                    filteredAnimeList = animes;
                }

                FilterResults results = new FilterResults();
                results.values = filteredAnimeList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredAnimeList = (ArrayList<AnimeListAnime>) results.values;
                notifyDataSetChanged(); // refresh the list with filtered data
            }
        };
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
