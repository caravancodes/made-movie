package id.frogobox.cataloguemovie.sources.local;

/*
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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import id.frogobox.cataloguemovie.sources.local.DataContract.DataEntry;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Deklarasi Nama DatabaseHelper dan Versinya --------------------------------------------------------
    private static final String DATABASE = DataContract.DB;
    private static final int DATABASE_VERSION = 1;
    // ---------------------------------------------------------------------------------------------

    // Constants ini gunanya adalah untuk mendapatkan fungsi dari library SQLiteDatabase ------------
    // ada dua macam, "WriteableDatabase" dan "ReadableDatabase"
    // ---------------------------------------------------------------------------------------------

    public DatabaseHelper(Context context) {
        super(context, DATABASE, null, DATABASE_VERSION);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        onCreate(sqLiteDatabase);
    }

    // Disini Code Untuk Create Table di database SQLite -------------------------------------------
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String sql_create_table = "CREATE TABLE IF NOT EXISTS " + DataEntry.TABLE_NAME + " (" +
                    DataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DataEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL," +
                    DataEntry.COLUMN_VOTE_COUNT + " INTEGER NOT NULL," +
                    DataEntry.COLUMN_VOTE_AVERAGE + " DOUBLE NOT NULL," +
                    DataEntry.COLUMN_TITLE + " TEXT NOT NULL," +
                    DataEntry.COLUMN_POPULARITY + " DOUBLE NOT NULL," +
                    DataEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL," +
                    DataEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL," +
                    DataEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL," +
                    DataEntry.COLUMN_OVERVIEW + " TEXT NOT NULL," +
                    DataEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL);";
            db.execSQL(sql_create_table);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ---------------------------------------------------------------------------------------------

    // Untuk mengupgrade table ---------------------------------------------------------------------
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql_drop_table = "DROP TABLE IF EXISTS " + DataEntry.TABLE_NAME;
        db.execSQL(sql_drop_table);
        onCreate(db);
    }
    // ---------------------------------------------------------------------------------------------

}
