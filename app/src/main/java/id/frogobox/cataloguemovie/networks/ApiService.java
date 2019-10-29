package id.frogobox.cataloguemovie.networks;

import id.frogobox.cataloguemovie.models.dataclass.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static id.frogobox.cataloguemovie.helpers.ConstantsVar.Constants.QUERY_API_KEY;
import static id.frogobox.cataloguemovie.helpers.ConstantsVar.Constants.QUERY_SEARCH_NAME;
import static id.frogobox.cataloguemovie.helpers.ConstantsVar.Constants.URL_NOW_PLAYING;
import static id.frogobox.cataloguemovie.helpers.ConstantsVar.Constants.URL_POPULAR;
import static id.frogobox.cataloguemovie.helpers.ConstantsVar.Constants.URL_SEARCH;
import static id.frogobox.cataloguemovie.helpers.ConstantsVar.Constants.URL_UP_COMING;

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * CatalogueMovie
 * Copyright (C) 13/01/2019.
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
public interface ApiService {

    @GET(URL_POPULAR)
    Call<Result> getPopularMovie(@Query(QUERY_API_KEY) String apiKey);

    @GET(URL_SEARCH)
    Call<Result> getSearchMovie(@Query(QUERY_API_KEY) String apiKey,
                                @Query(QUERY_SEARCH_NAME) String query);

    @GET(URL_NOW_PLAYING)
    Call<Result> getNowPlayingMovie(@Query(QUERY_API_KEY) String apiKey);

    @GET(URL_UP_COMING)
    Call<Result> getUpComingMovie(@Query(QUERY_API_KEY) String apiKey);

}
