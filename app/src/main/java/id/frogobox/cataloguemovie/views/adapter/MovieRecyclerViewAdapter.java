package id.frogobox.cataloguemovie.views.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import id.frogobox.cataloguemovie.R;
import id.frogobox.cataloguemovie.models.dataclass.Movie;
import id.frogobox.cataloguemovie.views.activities.DetailActivity;

import static id.frogobox.cataloguemovie.helpers.ConstantsVar.Constants.PATH_URL_IMAGE;

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * CatalogueMovie
 * Copyright (C) 08/11/2018.
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
public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Movie> data;
    private int layoutType;

    public MovieRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void addItem(ArrayList<Movie> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public void setLayoutType(int mLayoutType){
        this.layoutType = mLayoutType;
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

        Picasso.get().load(PATH_URL_IMAGE+data.get(position).getPoster_path()).into(holder.moviePoster);
        holder.movieTitle.setText(data.get(position).getTitle());
        holder.movieOverview.setText(data.get(position).getOverview());
        holder.movieDate.setText(data.get(position).getRelease_date());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentData = new Intent(context, DetailActivity.class);
                Movie parcelMovie = data.get(position);
                intentData.putExtra(DetailActivity.EXTRA_MOVIE, parcelMovie);
                context.startActivity(intentData);
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layoutType, parent, false);
        return new MovieRecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}