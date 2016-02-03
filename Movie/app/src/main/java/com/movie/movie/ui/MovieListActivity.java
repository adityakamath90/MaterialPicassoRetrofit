package com.movie.movie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.movie.movie.R;
import com.movie.movie.adapter.MovieAdapter;
import com.movie.movie.dto.Movie;
import com.movie.movie.manager.RecyclerItemClickListener;
import com.movie.movie.network.RestClient;
import com.movie.movie.utilities.Constants;
import com.movie.movie.utilities.NetworkUtils;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Aditya on 03/02/16.
 */
public class MovieListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private List<Movie> mMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        initViews();
        if (NetworkUtils.isAvailable(MovieListActivity.this)) {
            initiateMovieListApi();
        } else {
            NetworkUtils.showNoNetworkAlertForHome(MovieListActivity.this);
        }
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.movie_list);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MovieListActivity.this));
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(MovieListActivity.this, new RecyclerItemClickListener
                        .OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if (mMovieList != null && !mMovieList.isEmpty()) {
                            Movie movie = mMovieList.get(position);
                            if (NetworkUtils.isAvailable(MovieListActivity.this)) {
                                startMovieDetailActivity(movie);
                            } else {
                                NetworkUtils.showNoNetworkAlertForHome(MovieListActivity.this);
                            }
                        }

                    }
                })
        );

    }

    private void startMovieDetailActivity(Movie movie) {

        if (movie != null) {
            Intent movieDetailIntent = new Intent(this, MovieDetailActivity.class);
            movieDetailIntent.putExtra(Constants.BundleKeys.MOVIE, movie);
            startActivity(movieDetailIntent);
        }

    }

    private void initiateMovieListApi() {

        showProgress();
        RestClient.getMovieAoi().getMovieList().enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Response<List<Movie>> response) {
                hideProgress();
                if (response.isSuccess()) {
                    mMovieList = response.body();
                    mRecyclerView.setAdapter(new MovieAdapter(MovieListActivity.this, mMovieList));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                hideProgress();
            }
        });
    }

    private void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        //Put activity in backstack to prevent onCreate calling again for the time being.
        moveTaskToBack(true);
    }
}
