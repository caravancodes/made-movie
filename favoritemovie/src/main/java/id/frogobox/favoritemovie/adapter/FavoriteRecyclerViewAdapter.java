package id.frogobox.favoritemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import id.frogobox.favoritemovie.activities.DetailActivity;
import id.frogobox.favoritemovie.models.Favorite;
import id.frogobox.favoritemovie.R;


/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * CatalogueMovie
 * Copyright (C) 18/01/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Line     : bullbee117
 * Phone    : 081357108568
 * Majors   : D3 Teknik Informatika 2016
 * Campus   : Telkom University
 * -----------------------------------------
 * id.amirisback.frogobox
 */
public class FavoriteRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private Cursor data;
    public static final String PATH_URL_IMAGE = "https://image.tmdb.org/t/p/w342";

    public FavoriteRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setData(Cursor data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView moviePoster;
        TextView movieTitle, movieOverview, movieDate;

        public ViewHolder(View itemView) {
            super(itemView);
            // -------------------------------------------------------------------------------------
            moviePoster = itemView.findViewById(R.id.listPosterImageView);
            movieTitle = itemView.findViewById(R.id.listTitleTextView);
            movieOverview = itemView.findViewById(R.id.listOverviewTextView);
            movieDate = itemView.findViewById(R.id.listDateTextView);
            // -------------------------------------------------------------------------------------
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Favorite favorite = getItem(position);
        Picasso.get().load(PATH_URL_IMAGE+favorite.getPoster_path()).into(holder.moviePoster);
        holder.movieTitle.setText(favorite.getTitle());
        holder.movieOverview.setText(favorite.getOverview());
        holder.movieDate.setText(favorite.getRelease_date());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentData = new Intent(context, DetailActivity.class);
                intentData.putExtra(DetailActivity.EXTRA_FAVORITE, favorite);
                context.startActivity(intentData);
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_favorite, parent, false);
        return new FavoriteRecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }

        return data.getCount();
    }

    private Favorite getItem(int position){
        if (!data.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Favorite(data);
    }
}