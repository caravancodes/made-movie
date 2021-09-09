package id.frogobox.cataloguemovie.ui.activities;

import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import id.frogobox.cataloguemovie.R;
import id.frogobox.cataloguemovie.sources.local.MovieCrud;
import id.frogobox.cataloguemovie.models.Favorite;
import id.frogobox.cataloguemovie.models.Movie;

import static id.frogobox.cataloguemovie.utils.ConstantsVar.Constants.PATH_URL_IMAGE;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_poster";
    public static final String EXTRA_FAVORITE = "extra_favorite";
    private String stringMovieId, stringRating, stringPopularity,
            stringTitle, stringDate, stringOverview, stringBackDropPath;
    private boolean isFavorite = false;
    private Menu mMenu = null;
    private MovieCrud mMovieCrud;
    private Movie extraMovie;
    private Favorite extraFavorite;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // -----------------------------------------------------------------------------------------
        ImageView poster = findViewById(R.id.poster);
        TextView title = findViewById(R.id.titleTextVIew);
        TextView date = findViewById(R.id.dateTextView);
        TextView overview = findViewById(R.id.overviewTextView);
        TextView rating = findViewById(R.id.ratingTextView);
        TextView popularity = findViewById(R.id.popularityTextView);
        scrollView = findViewById(R.id.scrollViews);
        // -----------------------------------------------------------------------------------------
        mMovieCrud = new MovieCrud(this);
        getExtraData();
        // -----------------------------------------------------------------------------------------
        Picasso.get().load(PATH_URL_IMAGE+stringBackDropPath).into(poster);
        title.setText(stringTitle);
        date.setText(stringDate);
        overview.setText(stringOverview);
        rating.setText(stringRating);
        popularity.setText(stringPopularity);
        setTitle(stringTitle);
    }

    public void getExtraData() {

        extraMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        extraFavorite = getIntent().getParcelableExtra(EXTRA_FAVORITE);
        // -----------------------------------------------------------------------------------------
        if (extraMovie != null) {
            stringMovieId = String.valueOf(extraMovie.getMovie_id());
            stringRating = String.valueOf(extraMovie.getVote_average());
            stringPopularity = String.valueOf(extraMovie.getPopularity());
            stringTitle = extraMovie.getTitle();
            stringDate = extraMovie.getRelease_date();
            stringOverview = extraMovie.getOverview();
            stringBackDropPath = extraMovie.getBackdrop_path();
            setFavoriteState(stringMovieId);
        }
        // -----------------------------------------------------------------------------------------
        if (extraFavorite != null) {
            stringMovieId = String.valueOf(extraFavorite.getMovie_id());
            stringRating = String.valueOf(extraFavorite.getVote_average());
            stringPopularity = String.valueOf(extraFavorite.getPopularity());
            stringTitle = extraFavorite.getTitle();
            stringDate = extraFavorite.getRelease_date();
            stringOverview = extraFavorite.getOverview();
            stringBackDropPath = extraFavorite.getBackdrop_path();
            setFavoriteState(stringMovieId);
        }
        // -----------------------------------------------------------------------------------------
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_favorite, menu);
        mMenu = menu;
        setIconFavorite();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_favorite){
            if (isFavorite) {
                setRemoveFromFavorite(stringMovieId);
            } else {
                setAddToFavorite();
            }

            isFavorite = !isFavorite;
            setIconFavorite();
        }
        return super.onOptionsItemSelected(item);
    }


    private void setIconFavorite(){
        if (isFavorite) {
            mMenu.getItem(0).setIcon(R.drawable.ic_added_to_favorite);
        } else {
            mMenu.getItem(0).setIcon(R.drawable.ic_add_to_favorite);
        }
    }

    private void setAddToFavorite(){
        try {
            if (extraMovie != null) {
                if (mMovieCrud.insertDataMovie(extraMovie)) {
                    showSnackbarMessage(R.string.toast_add);
                } else {
                    showSnackbarMessage(R.string.toast_add_fail);
                }
            }

            if (extraFavorite != null) {
                if (mMovieCrud.insertDataFavorite(extraFavorite)) {
                    showSnackbarMessage(R.string.toast_add);
                } else {
                    showSnackbarMessage(R.string.toast_add_fail);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setRemoveFromFavorite(String mId){
        try {
            if (mMovieCrud.deleteDataId(mId)){
                showSnackbarMessage(R.string.toast_remove);
            } else {
                showSnackbarMessage(R.string.toast_remove_fail);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setFavoriteState(String mId){
        isFavorite = mMovieCrud.getFavoriteMovieId(mId).getCount() != 0;
    }

    private void showSnackbarMessage(int message){
        Snackbar.make(scrollView, message, Snackbar.LENGTH_SHORT).show();
    }

}