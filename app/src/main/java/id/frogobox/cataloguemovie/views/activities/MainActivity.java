package id.frogobox.cataloguemovie.views.activities;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import id.frogobox.cataloguemovie.R;
import id.frogobox.cataloguemovie.models.dataclass.Movie;
import id.frogobox.cataloguemovie.models.dataclass.Result;
import id.frogobox.cataloguemovie.networks.ApiClient;
import id.frogobox.cataloguemovie.networks.ApiService;
import id.frogobox.cataloguemovie.views.adapter.ViewPagerAdapter;
import id.frogobox.cataloguemovie.views.notifications.AlarmReceiver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.frogobox.cataloguemovie.helpers.ConstantsVar.Constants.API_KEY;
import static id.frogobox.cataloguemovie.helpers.ConstantsVar.Constants.FORMAT_DATE;
import static id.frogobox.cataloguemovie.helpers.ConstantsVar.Constants.HAS_BEEN_RELEASE;
import static id.frogobox.cataloguemovie.helpers.ConstantsVar.Constants.TIME_DAILY_RELEASE;
import static id.frogobox.cataloguemovie.helpers.ConstantsVar.Constants.TIME_DAILY_REMINDER;


public class MainActivity extends AppCompatActivity {

    // ---------------------------------------------------------------------------------------------
    private MenuItem prevMenuItem = null;
    private ViewPager mViewPager;
    private BottomNavigationView bottomNavigationView;
    // ---------------------------------------------------------------------------------------------
    private int DAILY_REMINDER_NOTIFICAITION_ID = 1;
    private String DAILY_REMINDER_CHANNEL_ID = "REMINDER";
    private CharSequence DAILY_REMINDER_CHANNEL_NAME = "DAILY_REMINDER";
    // ---------------------------------------------------------------------------------------------
    private int DAILY_RELEASE_NOTIFICAITION_ID = 2;
    private String DAILY_RELEASE_CHANNEL_ID = "RELEASE_";
    private CharSequence DAILY_RELEASE_CHANNEL_NAME = "DAILY_RELEASE_";
    // ---------------------------------------------------------------------------------------------
    private AlarmReceiver alarm = new AlarmReceiver();
    private ArrayList<Movie> mArrayMovieNow = new ArrayList<>();
    // ---------------------------------------------------------------------------------------------
    private final int ID_REPEATING_DAILY = 100;
    private final int ID_REPEATING_RELEASE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.title_nowplaying);
        // -----------------------------------------------------------------------------------------
        mViewPager = findViewById(R.id.viewpager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        ViewPagerAdapter mPagerAdapter = new ViewPagerAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        // -----------------------------------------------------------------------------------------
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.menu_now:
                        mViewPager.setCurrentItem(0);
                        setTitle(R.string.title_nowplaying);
                        break;
                    case R.id.menu_up:
                        mViewPager.setCurrentItem(1);
                        setTitle(R.string.title_upcoming);
                        break;
                    case R.id.menu_search:
                        mViewPager.setCurrentItem(2);
                        setTitle(R.string.title_search);
                        break;
                    case R.id.menu_favorites:
                        mViewPager.setCurrentItem(3);
                        setTitle(R.string.title_fav);
                        break;
                }
                return false;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false);
                else
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);

                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

                switch (position) {
                    case 0:
                        setTitle(R.string.title_nowplaying);
                        break;
                    case 1:
                        setTitle(R.string.title_upcoming);
                        break;
                    case 2:
                        setTitle(R.string.title_search);
                        break;
                    case 3:
                        setTitle(R.string.title_fav);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setDailyReminder();
        setDailyRelease();

    }


    private boolean hasConnection() {
        ConnectivityManager connect = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connect.getActiveNetworkInfo() != null;
    }

    private void setDailyReminder() {

        alarm.setRepeatingAlarm(this,
                getResources().getString(R.string.notification_daily_reminder_title),
                getResources().getString(R.string.notification_daily_reminder_text),
                TIME_DAILY_REMINDER,
                DAILY_REMINDER_NOTIFICAITION_ID,
                DAILY_REMINDER_CHANNEL_ID,
                DAILY_REMINDER_CHANNEL_NAME,
                ID_REPEATING_DAILY);

    }

    private void setDailyRelease() {

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        if (hasConnection()) {
            Call<Result> callResultNow = apiService.getNowPlayingMovie(API_KEY);
            callResultNow.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    mArrayMovieNow.clear();
                    List<Movie> listPopular = response.body().getResults();
                    mArrayMovieNow.addAll(listPopular);
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), R.string.toast_fail_network, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), R.string.toast_no_internet, Toast.LENGTH_SHORT).show();
        }

        // -----------------------------------------------------------------------------------------
        String DateToday = new SimpleDateFormat(FORMAT_DATE).format(new Date());

        for (int i = 0; i < mArrayMovieNow.size(); i++) {

            if (mArrayMovieNow.get(i).getRelease_date().equalsIgnoreCase(DateToday)) {
                // ---------------------------------------------------------------------------------
                String textRelease = mArrayMovieNow.get(i).getTitle() + HAS_BEEN_RELEASE;
                String channelName = DAILY_RELEASE_CHANNEL_NAME + mArrayMovieNow.get(i).getTitle().toUpperCase();
                // ---------------------------------------------------------------------------------
                alarm.setRepeatingAlarm(this,
                        getResources().getString(R.string.notification_daily_reminder_title),
                        textRelease,
                        TIME_DAILY_RELEASE,
                        DAILY_RELEASE_NOTIFICAITION_ID + i,
                        DAILY_RELEASE_CHANNEL_ID + i,
                        channelName,
                        ID_REPEATING_RELEASE);
            }

        }
    }

}