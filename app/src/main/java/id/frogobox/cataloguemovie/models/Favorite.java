package id.frogobox.cataloguemovie.models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import id.frogobox.cataloguemovie.sources.local.DataContract;

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
public class Favorite implements Parcelable {

    private int id;
    private int movie_id;
    private int vote_count;
    private double vote_average;
    private String title;
    private double popularity;
    private String poster_path;
    private String backdrop_path;
    private String original_title;
    private String overview;
    private String release_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.movie_id);
        dest.writeInt(this.vote_count);
        dest.writeDouble(this.vote_average);
        dest.writeString(this.title);
        dest.writeDouble(this.popularity);
        dest.writeString(this.poster_path);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.original_title);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
    }

    public Favorite() {
    }

    public Favorite(Cursor cursor){

        int idColumnIndex = cursor.getColumnIndex(DataContract.DataEntry._ID);
        int movieIdColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_MOVIE_ID);
        int vote_countColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_VOTE_COUNT);
        int vote_averageColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_VOTE_AVERAGE);
        int titleColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_TITLE);
        int popularityColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_POPULARITY);
        int poster_pathColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_POSTER_PATH);
        int backdrop_pathColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_BACKDROP_PATH);
        int original_titleColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_ORIGINAL_TITLE);
        int overviewColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_OVERVIEW);
        int release_dateColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_RELEASE_DATE);

        this.id = cursor.getInt(cursor.getInt(idColumnIndex));
        this.movie_id = cursor.getInt(movieIdColumnIndex);
        this.vote_count = cursor.getInt(vote_countColumnIndex);
        this.vote_average = cursor.getDouble(vote_averageColumnIndex);
        this.title = cursor.getString(titleColumnIndex);
        this.popularity = cursor.getDouble(popularityColumnIndex);
        this.poster_path = cursor.getString(poster_pathColumnIndex);
        this.backdrop_path = cursor.getString(backdrop_pathColumnIndex);
        this.original_title = cursor.getString(original_titleColumnIndex);
        this.overview = cursor.getString(overviewColumnIndex);
        this.release_date = cursor.getString(release_dateColumnIndex);
    }

    public Favorite(Parcel in) {
        this.id = in.readInt();
        this.movie_id = in.readInt();
        this.vote_count = in.readInt();
        this.vote_average = in.readDouble();
        this.title = in.readString();
        this.popularity = in.readDouble();
        this.poster_path = in.readString();
        this.backdrop_path = in.readString();
        this.original_title = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel source) {
            return new Favorite(source);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };
}