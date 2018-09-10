package com.hokagelab.www.cataloguemovie.api;

import com.hokagelab.www.cataloguemovie.model.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieClient {

    @GET("movie/popular")
    Call<MovieResponse> reposMovieList(@Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MovieResponse> reposSearch(@Query("api_key") String apiKey, @Query("query") String movies);

    @GET("movie/now_playing")
    Call<MovieResponse> reposNowPlaying(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<MovieResponse> reposUpComing(@Query("api_key") String apiKey);
}
