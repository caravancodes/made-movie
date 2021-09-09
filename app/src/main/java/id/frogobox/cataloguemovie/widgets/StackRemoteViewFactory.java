package id.frogobox.cataloguemovie.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import id.frogobox.cataloguemovie.R;
import id.frogobox.cataloguemovie.sources.local.MovieCrud;
import id.frogobox.cataloguemovie.models.Favorite;

import static id.frogobox.cataloguemovie.utils.ConstantsVar.Constants.PATH_URL_IMAGE;

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
public class StackRemoteViewFactory implements
        RemoteViewsService.RemoteViewsFactory {

    private ArrayList<Favorite> mWidgetItems = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;

    public StackRemoteViewFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    private void getMovieData(){
        MovieCrud crudHelper = new MovieCrud(mContext);
        Cursor cursor = crudHelper.getFavoriteMovie();
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                Favorite favorite = new Favorite(cursor);
                mWidgetItems.add(favorite);
                cursor.moveToNext();
            }
        }
        cursor.close();
    }

    public void onCreate() {
        getMovieData();
    }


    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();
        getMovieData();
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews mRemoteViews = new RemoteViews(mContext.getPackageName(), R.layout.content_item_widget);
        // -----------------------------------------------------------------------------------------
        mRemoteViews.setTextViewText(R.id.textViewWidget, mWidgetItems.get(position).getTitle());
        try {
            Bitmap b = Picasso.get().load(PATH_URL_IMAGE+mWidgetItems.get(position).getBackdrop_path()).get();
            mRemoteViews.setImageViewBitmap(R.id.imageViewWidget, b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // -----------------------------------------------------------------------------------------
        Bundle extras = new Bundle();
        extras.putInt(FavoriteBannerWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        // -----------------------------------------------------------------------------------------
        mRemoteViews.setOnClickFillInIntent(R.id.imageViewWidget, fillInIntent);
        // -----------------------------------------------------------------------------------------
        return mRemoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
