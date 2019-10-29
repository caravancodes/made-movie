package id.frogobox.favoritemovie.activities;

import android.database.Cursor;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import id.frogobox.favoritemovie.R;
import id.frogobox.favoritemovie.adapter.FavoriteRecyclerViewAdapter;
import id.frogobox.favoritemovie.helpers.MovieCrudHelper;

public class MainActivity extends AppCompatActivity {

    private FavoriteRecyclerViewAdapter adapter;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private MovieCrudHelper movieCrudHelper;
    private TextView textViewEmpty;
    private Cursor list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.favRecyclerView);
        progressBar = findViewById(R.id.favProgressBar);
        textViewEmpty = findViewById(R.id.textFav);
        // -----------------------------------------------------------------------------------------
        movieCrudHelper = new MovieCrudHelper(this);
        adapter = new FavoriteRecyclerViewAdapter(this);
        // -----------------------------------------------------------------------------------------
        progressBar.setVisibility(View.INVISIBLE);
        textViewEmpty.setVisibility(View.INVISIBLE);
        // -----------------------------------------------------------------------------------------
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration divider = new DividerItemDecoration(this, mLayoutManager.getOrientation());
        // -----------------------------------------------------------------------------------------
        adapter.setData(list);
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
        // -----------------------------------------------------------------------------------------
        new LoadNoteAsync().execute();

    }

    private class LoadNoteAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return movieCrudHelper.getFavoriteMovie();
        }

        @Override
        protected void onPostExecute(Cursor notes) {
            super.onPostExecute(notes);
            progressBar.setVisibility(View.GONE);

            list = notes;
            adapter.setData(list);
            if (list.getCount() == 0){
                textViewEmpty.setVisibility(View.VISIBLE);
            }
        }
    }

}
