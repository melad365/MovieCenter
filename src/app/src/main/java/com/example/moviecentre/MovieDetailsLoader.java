package com.example.moviecentre;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.moviecentre.models.MovieDetails;

import java.util.List;

public class MovieDetailsLoader extends AsyncTaskLoader<List<MovieDetails>> {

    private final String url;

    public MovieDetailsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<MovieDetails> loadInBackground() {
        if (url == null) return null;
        return NetworkRequest.fetchMovieData(url);
    }
}
