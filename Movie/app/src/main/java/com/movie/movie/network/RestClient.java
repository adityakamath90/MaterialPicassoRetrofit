package com.movie.movie.network;

import com.movie.movie.utilities.Config;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by Aditya on 03/02/16.
 */
public class RestClient {


    public static MovieService getMovieAoi() {
        MovieService api = getRetrofit().create(MovieService.class);
        return api;
    }


    private static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.URL_END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
