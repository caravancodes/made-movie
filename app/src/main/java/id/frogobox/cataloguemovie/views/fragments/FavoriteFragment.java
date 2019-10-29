package id.frogobox.cataloguemovie.views.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import id.frogobox.cataloguemovie.R;
import id.frogobox.cataloguemovie.models.databases.MovieCrud;
import id.frogobox.cataloguemovie.presenters.LoadMovieAsync;
import id.frogobox.cataloguemovie.views.adapter.FavoriteRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private FavoriteRecyclerViewAdapter adapter;
    private RecyclerView mRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private boolean isGrid = false;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
        ProgressBar progressBar = rootView.findViewById(R.id.favProgressBar);
        TextView textViewEmpty = rootView.findViewById(R.id.textFav);
        mRecyclerView = rootView.findViewById(R.id.favRecyclerView);
        // -----------------------------------------------------------------------------------------
        adapter = new FavoriteRecyclerViewAdapter(getContext());
        // -----------------------------------------------------------------------------------------
        progressBar.setVisibility(View.INVISIBLE);
        textViewEmpty.setVisibility(View.INVISIBLE);
        MovieCrud movieCrud = new MovieCrud(getContext());
        // -----------------------------------------------------------------------------------------
        mRecyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        linearLayoutManager = new LinearLayoutManager(getContext());
        dividerItemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        // -----------------------------------------------------------------------------------------
        LoadMovieAsync mAsync = new LoadMovieAsync(movieCrud, adapter);
        mAsync.setView(progressBar, textViewEmpty);
        mAsync.execute();
        setLayoutState();
        // -----------------------------------------------------------------------------------------
        return rootView;
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

    private void setLayoutState(){
        if (isGrid) {
            setListView();
        } else {
            setGridView();
        }

        isGrid = !isGrid;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        getActivity().getMenuInflater().inflate(R.menu.menu_app_bar_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_layout:
                setLayoutState();
                break;
            case R.id.menu_setting:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
            default:
                break;
        }

        return false;
    }

}