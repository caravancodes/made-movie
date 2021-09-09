package id.frogobox.cataloguemovie.utils;

import id.frogobox.cataloguemovie.BuildConfig;

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * CatalogueMovie
 * Copyright (C) 10/01/2019.
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
public class ConstantsVar {

    public ConstantsVar() {
    }

    public static final class Constants{

        public static final String STATE_KEY_FRAGMENT_UPCOMING = "state_result_upcoming";
        public static final String STATE_KEY_FRAGMENT_SEARCH = "state_result_search";
        public static final String STATE_KEY_FRAGMENT_NOWPLAYING = "state_result_nowplaying";
        // -----------------------------------------------------------------------------------------
        public static final String API_KEY = BuildConfig.TMDB_API_KEY;
        // -----------------------------------------------------------------------------------------
        public static final String BASE_URL = BuildConfig.TMDB_BASE_URL;
        public static final String PATH_URL_IMAGE = BuildConfig.TMDB_PATH_URL_IMAGE;
        // -----------------------------------------------------------------------------------------
        public static final String URL_POPULAR = BuildConfig.TMDB_URL_POPULAR;
        public static final String URL_SEARCH = BuildConfig.TMDB_URL_SEARCH;
        public static final String URL_NOW_PLAYING = BuildConfig.TMDB_URL_NOW_PLAYING;
        public static final String URL_UP_COMING = BuildConfig.TMDB_UP_COMING;
        // -----------------------------------------------------------------------------------------
        public static final String QUERY_API_KEY = "api_key";
        public static final String QUERY_SEARCH_NAME = "query";
        // -----------------------------------------------------------------------------------------
        public static final String PACKAGE_ROOT = "id.frogobox.cataloguemovie";
        public static final String PACKAGE_PATH_ACTIVITIES = PACKAGE_ROOT+".views.activities";
        public static final String PATH_MAIN_ACTIVITY = PACKAGE_PATH_ACTIVITIES+".MainActivity";
        // -----------------------------------------------------------------------------------------
        public static final String TIME_DAILY_REMINDER = "07:00";
        public static final String TIME_DAILY_RELEASE = "08:00";
        public static final String FORMAT_DATE = "yyyy-MM-dd";
        public static final String HAS_BEEN_RELEASE = "has been release today!";
        // -----------------------------------------------------------------------------------------

    }
}