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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myanimelistapp.fragment.FragmentTwo;
import com.example.myanimelistapp.R;
import com.example.myanimelistapp.activity.AnimeDescActivity;
import com.example.myanimelistapp.activity.MangaDescActivity;
import com.github.doomsdayrs.jikan4java.types.main.top.TopListing;

import java.util.ArrayList;
import java.util.List;

public class TopListAdapter extends RecyclerView.Adapter<TopListAdapter.ViewHolder> implements Filterable {
    private static final String TAG = "RecyclerViewAdapter";

    private List<TopListing> topList;
    private List<TopListing> filteredTopList;
    private Context mContext;
    private Class fragment;

    // Constructor for top ranking list
    public TopListAdapter(Context context, ArrayList<TopListing> list, Fragment frag) {
        fragment = frag.getClass();
        mContext = context;
        filteredTopList = list;
        topList = new ArrayList<>(filteredTopList);
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

        final TopListing currentItem = filteredTopList.get(position);

        holder.imageName.setText(currentItem.title);

        Glide.with(mContext)
                .asBitmap()
                .load(currentItem.image_url)
                .into(holder.image);

        holder.parentLayout.setOnClickListener(view -> {
            Log.d(TAG, "onClick: clicked on: " + currentItem.title);
            Toast.makeText(mContext, currentItem.title, Toast.LENGTH_SHORT).show();

            Intent intent;
            if(fragment == FragmentTwo.class){
                intent = new Intent(mContext, AnimeDescActivity.class);
            }
            else {
                intent = new Intent(mContext, MangaDescActivity.class);
            }
            intent.putExtra("image_url", currentItem.image_url);
            intent.putExtra("title", currentItem.title);
            intent.putExtra("mal_id", currentItem.mal_id);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return filteredTopList.size();
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if(charString.isEmpty()) {
                    filteredTopList = topList;
                } else {
                    ArrayList<TopListing> tp = new ArrayList<>();
                    for(TopListing x : topList){
                        if(x.title.toLowerCase().contains(charString.toLowerCase())) {
                            tp.add(x);
                        }
                    }
                    filteredTopList = tp;
                }
                FilterResults results = new FilterResults();
                results.values = filteredTopList;
                results.count = filteredTopList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredTopList = (ArrayList<TopListing>) results.values;
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
