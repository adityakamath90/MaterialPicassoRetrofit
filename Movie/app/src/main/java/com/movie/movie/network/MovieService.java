package com.movie.movie.network;

import com.movie.movie.dto.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Aditya on 03/02/16.
 */
public interface MovieService {

    @GET("movies.json")
    Call<List<Movie>> getMovieList();

}
