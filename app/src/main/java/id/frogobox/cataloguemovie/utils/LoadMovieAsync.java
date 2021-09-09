package id.frogobox.cataloguemovie.utils;

import android.database.Cursor;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import id.frogobox.cataloguemovie.sources.local.MovieCrud;
import id.frogobox.cataloguemovie.ui.adapter.FavoriteRecyclerViewAdapter;

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * CatalogueMovie
 * Copyright (C) 21/01/2019.
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
public class LoadMovieAsync extends AsyncTask<Void, Void, Cursor> {

    private ProgressBar progressBar;
    private TextView textViewEmpty;
    private MovieCrud movieCrud;
    private FavoriteRecyclerViewAdapter adapter;

    public LoadMovieAsync(MovieCrud movieCrud, FavoriteRecyclerViewAdapter adapter) {
        this.movieCrud = movieCrud;
        this.adapter = adapter;
    }

    public void setView(ProgressBar progressBar, TextView textView){
        this.progressBar = progressBar;
        this.textViewEmpty = textView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Cursor doInBackground(Void... voids) {
        return movieCrud.getFavoriteMovie();
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        progressBar.setVisibility(View.GONE);
        if (cursor.getCount() != 0){
            adapter.setData(cursor);
        } else {
            textViewEmpty.setVisibility(View.VISIBLE);
        }
    }
}
