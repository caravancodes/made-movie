package id.frogobox.cataloguemovie.views.fragments;


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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.frogobox.cataloguemovie.R;
import id.frogobox.cataloguemovie.models.dataclass.Movie;
import id.frogobox.cataloguemovie.models.dataclass.Result;
import id.frogobox.cataloguemovie.networks.ApiClient;
import id.frogobox.cataloguemovie.networks.ApiService;
import id.frogobox.cataloguemovie.presenters.LoadLayoutState;
import id.frogobox.cataloguemovie.views.adapter.MovieRecyclerViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.frogobox.cataloguemovie.helpers.ConstantsVar.Constants.API_KEY;
import static id.frogobox.cataloguemovie.helpers.ConstantsVar.Constants.STATE_KEY_FRAGMENT_SEARCH;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieSearchFragment extends Fragment {

    private ProgressBar mProgressBar;
    private MovieRecyclerViewAdapter adapter;
    private ApiService apiService;
    private ArrayList<Movie> mArrayMovieSearch = new ArrayList<>();
    private LoadLayoutState loadLayoutState = new LoadLayoutState();


    public MovieSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        final EditText findEditText = rootView.findViewById(R.id.mainEditTextCari);
        Button findButton = rootView.findViewById(R.id.mainButtonCari);
        RecyclerView mRecyclerView = rootView.findViewById(R.id.searchRecyclerView);
        mProgressBar = rootView.findViewById(R.id.searchProgressBar);
        // -----------------------------------------------------------------------------------------
        mRecyclerView.hasFixedSize();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        adapter = new MovieRecyclerViewAdapter(getContext());
        // -----------------------------------------------------------------------------------------
        loadLayoutState.setmRecyclerView(mRecyclerView);
        loadLayoutState.setDividerItemDecoration(dividerItemDecoration);
        loadLayoutState.setAdapter(adapter);
        loadLayoutState.setLinearLayoutManager(linearLayoutManager);
        loadLayoutState.setGridLayoutManager(gridLayoutManager);
        // -----------------------------------------------------------------------------------------
        apiService = ApiClient.getClient().create(ApiService.class);
        if (hasConnection()) {
            Call<Result> callResultPopular = apiService.getPopularMovie(API_KEY);
            callResultPopular.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    mArrayMovieSearch.clear();
                    List<Movie> listPopular = response.body().getResults();
                    mArrayMovieSearch.addAll(listPopular);
                    adapter.addItem(mArrayMovieSearch);
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

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieTitle = findEditText.getText().toString();
                resultSearch(movieTitle, savedInstanceState);
            }
        });

        getSavedInstanceState(savedInstanceState);

        return rootView;
    }

    private void resultSearch(String movieTitle, Bundle savedInstanceState){
        mProgressBar.setVisibility(View.VISIBLE);
        if(hasConnection()){
            Call<Result> callResultSearch = apiService.getSearchMovie(API_KEY, movieTitle);
            callResultSearch.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    mArrayMovieSearch.clear();
                    List<Movie> listSearch = response.body().getResults();
                    mArrayMovieSearch.addAll(listSearch);
                    adapter.addItem(mArrayMovieSearch);
                    mProgressBar.setVisibility(View.INVISIBLE);
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
    }

    private boolean hasConnection(){
        ConnectivityManager connect = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connect.getActiveNetworkInfo() != null;
    }

    private void getSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null){
            mProgressBar.setVisibility(View.INVISIBLE);
            mArrayMovieSearch = savedInstanceState.getParcelableArrayList(STATE_KEY_FRAGMENT_SEARCH);
            adapter.addItem(mArrayMovieSearch);
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
        outState.putParcelableArrayList(STATE_KEY_FRAGMENT_SEARCH, mArrayMovieSearch);
        super.onSaveInstanceState(outState);
    }

}