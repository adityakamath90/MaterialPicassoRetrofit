package com.movie.movie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.movie.movie.R;
import com.movie.movie.dto.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Aditya on 03/02/16.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private LayoutInflater mInflater;
    private List<Movie> mMovieList;

    public MovieAdapter(Context context, List<Movie> movieList) {
        mInflater = LayoutInflater.from(context);
        mMovieList = movieList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.movie_item, parent, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        String title = mMovieList.get(position).getTitle();
        String genre = mMovieList.get(position).getGenre().toString();
        String thumbnail = mMovieList.get(position).getImage();

        Context context = mInflater.getContext();
        holder.mMovieName.setText(context.getString(R.string.movie_name, title));
        holder.mMovieGenre.setText(context.getString(R.string.movie_genre, genre));
        Picasso.with(context).load(thumbnail).resize(75, 75).centerCrop().placeholder(R.mipmap
                .ic_launcher).error(R.mipmap.ic_launcher).into(holder.mThumbnailImageView);
    }

    @Override
    public int getItemCount() {
        return (mMovieList != null && !mMovieList.isEmpty()) ? mMovieList.size() : 0;
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView mThumbnailImageView;
        private TextView mMovieName;
        private TextView mMovieGenre;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mThumbnailImageView = (ImageView) itemView.findViewById(R.id.movie_thumbnail);
            mMovieName = (TextView) itemView.findViewById(R.id.movie_name);
            mMovieGenre = (TextView) itemView.findViewById(R.id.movie_genre);
        }
    }
}
