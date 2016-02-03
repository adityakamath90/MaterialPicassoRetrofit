package com.movie.movie.ui;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.movie.movie.R;
import com.movie.movie.dto.Movie;
import com.movie.movie.utilities.Constants;
import com.squareup.picasso.Picasso;

/**
 * Created by Aditya on 03/02/16.
 */
public class MovieDetailActivity extends AppCompatActivity {

    private ImageView mLargeImage;
    private Toolbar mToolbar;
    private TextView mDescription;
    private CollapsingToolbarLayout mCollapsingToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Movie movie = getMovieExtra();
        initViews();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setToolbarTitle(movie);
        downloadLargeMovieImage(movie);
        setMovieDescription(movie);
    }

    private void setMovieDescription(Movie movie) {
        if (movie == null)
            return;
        ;

        mDescription.setText(getString(R.string.lorem, movie.getGenre(), movie.getRating(), movie
                .getReleaseYear()));
    }

    private void downloadLargeMovieImage(final Movie movie) {
        //Wait for image view to draw itself in the xml hierarchy
        mLargeImage.post(new Runnable() {
            @Override
            public void run() {
                Picasso.with(MovieDetailActivity.this).load(movie.getImage()).resize(mLargeImage
                        .getWidth
                        (), mLargeImage.getHeight()).centerCrop().placeholder(new ColorDrawable
                        (ContextCompat.getColor(MovieDetailActivity.this, R
                                .color.colorHighlight))).into(mLargeImage);
            }
        });

    }

    private void setToolbarTitle(final Movie movie) {
        if (movie == null)
            return;
        mCollapsingToolbar.setExpandedTitleTextAppearance(R.style.Toolbar_TitleText);
        mCollapsingToolbar.setCollapsedTitleTextAppearance(R.style.Toolbar_TitleText_Collpase);
        getSupportActionBar().setTitle(movie.getTitle());
    }

    private void initViews() {
        mLargeImage = (ImageView) findViewById(R.id.largeImage);
        mToolbar = (Toolbar) findViewById(R.id.toolBarView);
        mDescription = (TextView) findViewById(R.id.description);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.main_collapsing);
    }

    public Movie getMovieExtra() {
        Movie movie = null;
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Constants.BundleKeys.MOVIE)) {
                movie = intent.getParcelableExtra(Constants.BundleKeys.MOVIE);
            }
        }
        return movie;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
