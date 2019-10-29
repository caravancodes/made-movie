package id.frogobox.cataloguemovie.models.databases;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

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

public class DataContract {
    // Class ini tujuannya untuk menjadi object table

    private DataContract() {
    }

    // Content URI ---------------------------------------------------------------------------------
    public static final String DB = "frogomovie.db"; // Nama Database
    public static final String CONTENT_AUTHORITY = "id.frogobox.cataloguemovie"; // Nama Domain Aplikasi
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_DATA = "data_movie"; // Isinya sama dengan Table Name
    // ---------------------------------------------------------------------------------------------

    // Deklarasi Elemen Table ----------------------------------------------------------------------
    public static final class DataEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_DATA);
        // -----------------------------------------------------------------------------------------
        public static final String TABLE_NAME = "data_movie"; // Deklarasi Nama Table
        public static final String _ID  = BaseColumns._ID; // Dekalarasi ID
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        // -----------------------------------------------------------------------------------------
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DATA;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DATA;
    }
    // ---------------------------------------------------------------------------------------------

}
