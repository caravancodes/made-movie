package id.frogobox.cataloguemovie.presenters;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import id.frogobox.cataloguemovie.R;
import id.frogobox.cataloguemovie.views.adapter.MovieRecyclerViewAdapter;

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
public class LoadLayoutState {

    private MovieRecyclerViewAdapter adapter;
    private RecyclerView mRecyclerView;
    private DividerItemDecoration dividerItemDecoration;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private boolean isGrid;

    public LoadLayoutState() {
        this.isGrid = false;
    }

    public void setAdapter(MovieRecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    public void setmRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    public void setDividerItemDecoration(DividerItemDecoration dividerItemDecoration) {
        this.dividerItemDecoration = dividerItemDecoration;
    }

    public void setGridLayoutManager(GridLayoutManager gridLayoutManager) {
        this.gridLayoutManager = gridLayoutManager;
    }

    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    private void setGridView(){
        adapter.setLayoutType(R.layout.content_item_grid);
        mRecyclerView.removeItemDecoration(dividerItemDecoration);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    private void setListView(){
        adapter.setLayoutType(R.layout.content_item_list);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    public void setLayoutState(){
        if (isGrid) {
            setListView();
        } else {
            setGridView();
        }
        isGrid = !isGrid;
    }

}
