package id.frogobox.cataloguemovie.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.frogobox.cataloguemovie.R;
import id.frogobox.cataloguemovie.models.Movie;
import id.frogobox.cataloguemovie.models.Result;
import id.frogobox.cataloguemovie.sources.remote.ApiClient;
import id.frogobox.cataloguemovie.sources.remote.ApiService;
import id.frogobox.cataloguemovie.utils.LoadLayoutState;
import id.frogobox.cataloguemovie.ui.adapter.MovieRecyclerViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.frogobox.cataloguemovie.utils.ConstantsVar.Constants.API_KEY;
import static id.frogobox.cataloguemovie.utils.ConstantsVar.Constants.STATE_KEY_FRAGMENT_NOWPLAYING;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieNowplayingFragment extends Fragment {

    private ProgressBar mProgressBar;
    private MovieRecyclerViewAdapter adapter;
    private ArrayList<Movie> mArrayMovieNow = new ArrayList<>();
    private LoadLayoutState loadLayoutState = new LoadLayoutState();

    public MovieNowplayingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_now_playing, container, false);
        RecyclerView mRecyclerView = rootView.findViewById(R.id.nowRecyclerView);
        mProgressBar = rootView.findViewById(R.id.nowProgressBar);
        // -----------------------------------------------------------------------------------------
        mRecyclerView.hasFixedSize();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        adapter = new MovieRecyclerViewAdapter(getContext());
        // -----------------------------------------------------------------------------------------
        loadLayoutState.setmRecyclerView(mRecyclerView);

        loadLayoutState.setAdapter(adapter);
        loadLayoutState.setLinearLayoutManager(linearLayoutManager);
        loadLayoutState.setGridLayoutManager(gridLayoutManager);
        // -----------------------------------------------------------------------------------------
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        if (hasConnection()) {
            Call<Result> callResultNow = apiService.getNowPlayingMovie(API_KEY);
            callResultNow.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    mArrayMovieNow.clear();
                    List<Movie> listPopular = response.body().getResults();
                    mArrayMovieNow.addAll(listPopular);
                    adapter.addItem(mArrayMovieNow);
                    // -----------------------------------------------------------------------------
                    mProgressBar.setVisibility(View.INVISIBLE);
                    loadLayoutState.setLayoutState();
                }
                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Toast.makeText(getContext(), R.string.toast_fail_network, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            getSavedInstanceState(savedInstanceState);
            Toast.makeText(getContext(), R.string.toast_no_internet, Toast.LENGTH_SHORT).show();
        }

        getSavedInstanceState(savedInstanceState);

        return rootView;
    }

    private boolean hasConnection(){
        ConnectivityManager connect = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connect.getActiveNetworkInfo() != null;
    }

    private void getSavedInstanceState(Bundle savedInstanceState){
        if (savedInstanceState != null){
            mProgressBar.setVisibility(View.INVISIBLE);
            mArrayMovieNow = savedInstanceState.getParcelableArrayList(STATE_KEY_FRAGMENT_NOWPLAYING);
            adapter.addItem(mArrayMovieNow);
            loadLayoutState.setLayoutState();
        }
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
                loadLayoutState.setLayoutState();
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(STATE_KEY_FRAGMENT_NOWPLAYING, mArrayMovieNow);
        super.onSaveInstanceState(outState);
    }

}