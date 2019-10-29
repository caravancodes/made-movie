package id.frogobox.favoritemovie.helpers;

import android.content.Context;
import android.database.Cursor;

import id.frogobox.favoritemovie.helpers.DataContract.DataEntry;

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
public class MovieCrudHelper {

    private Context context;

    public MovieCrudHelper(Context context) {
        this.context = context;
    }

    // ---------------------------------------------------------------------------------------------
    // Constants projection ini gunanya untuk memilih column pada database
    // Guna variable projection ini sama seperti * pada SQL
    private String projection[] = {DataEntry._ID,
            DataEntry.COLUMN_MOVIE_ID,
            DataEntry.COLUMN_VOTE_COUNT,
            DataEntry.COLUMN_VOTE_AVERAGE,
            DataEntry.COLUMN_TITLE,
            DataEntry.COLUMN_POPULARITY,
            DataEntry.COLUMN_POSTER_PATH,
            DataEntry.COLUMN_BACKDROP_PATH,
            DataEntry.COLUMN_ORIGINAL_TITLE,
            DataEntry.COLUMN_OVERVIEW,
            DataEntry.COLUMN_RELEASE_DATE
    };
    // ---------------------------------------------------------------------------------------------

    public boolean deleteDataId(String mID) {
        String selection = DataEntry.COLUMN_MOVIE_ID + " = '" + mID + "'";
        int resultUri = context.getContentResolver().delete(
                DataEntry.CONTENT_URI,
                selection,
                null);
        return resultUri != 0;
    }

    public Cursor getFavoriteMovieId(String mID){
        String selection = DataEntry.COLUMN_MOVIE_ID + " = '" + mID + "'";
        return context.getContentResolver().query(
                DataEntry.CONTENT_URI,
                projection,
                selection,
                null,
                null);
    }

    // Method Cursor untuk menarik semua data sementara dari database ------------------------------
    // dalam method ini menggambil semua data tanpa arguments
    public Cursor getFavoriteMovie(){
        return context.getContentResolver().query(
                DataEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }
    // ---------------------------------------------------------------------------------------------

}
