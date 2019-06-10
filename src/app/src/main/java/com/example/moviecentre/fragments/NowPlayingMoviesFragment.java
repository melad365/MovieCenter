package com.example.moviecentre.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moviecentre.MovieDetailsActivity;
import com.example.moviecentre.MovieDetailsLoader;
import com.example.moviecentre.R;
import com.example.moviecentre.models.MovieDetails;
import com.example.moviecentre.ui.PosterResize;
import com.example.moviecentre.ui.MovieRecyclerAdapter;
import com.example.moviecentre.ui.RecyclerViewTouchListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.moviecentre.Constant.API_PAR_KEY;
import static com.example.moviecentre.Constant.MOVIE_API_URL;
import static com.example.moviecentre.Constant.API_URL_ADD_NOW_PLAYING;
import static com.example.moviecentre.Constant.LAYOUT_MANAGER_STATE_KEY;
import static com.example.moviecentre.Constant.LOADER_MANAGER_ID;
import static com.example.moviecentre.Constant.MOVIE_INTENT_KEY;
import static com.example.moviecentre.Constant.PERSONAL_MOVIES_DB_API_KEY;
import static com.example.moviecentre.Constant.QUERY_REGION;
import static com.example.moviecentre.Constant.REGION_PAR_KEY;

//Loader Manager  is used in the background
public class NowPlayingMoviesFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<MovieDetails>> {
    //Views
    @BindView(R.id.recycle_view_movies) RecyclerView recycle_view_movies;
    @BindView(R.id.tv_no_connection) TextView tv_no_connection;

    //Declarations
    private MovieRecyclerAdapter movieRecyclerAdapter;
    private GridLayoutManager layoutManager;

    private List<MovieDetails> moviesList;

    private Parcelable layoutManagerState;


    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference myRef = database.getReference("Movies");



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        if (savedInstanceState != null) {
            layoutManagerState = savedInstanceState.getParcelable(LAYOUT_MANAGER_STATE_KEY);
        }



        View rootView = inflater.inflate(R.layout.fragment_movies, container,false);
        ButterKnife.bind(this, rootView);

        tv_no_connection.setVisibility(View.INVISIBLE);

        //RecyclerView setup
        int numberOfColumns = PosterResize.calculateNoOfColumns(getActivity());
        layoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
        recycle_view_movies.setLayoutManager(layoutManager);

        fetchMoviesData();


        recycle_view_movies.addOnItemTouchListener(new RecyclerViewTouchListener
                (getActivity(),recycle_view_movies, new RecyclerViewTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        //Open movie details intent
                        MovieDetails movieDetails = moviesList.get(position);
                        Context context = getActivity();
                        Intent movieDetailsIntent = new Intent(context, MovieDetailsActivity.class);
                        movieDetailsIntent.putExtra(MOVIE_INTENT_KEY, movieDetails);
                        startActivity(movieDetailsIntent);

                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));
        return rootView;
    }


    @Override
    public Loader<List<MovieDetails>> onCreateLoader(int id, Bundle args) {
        return new MovieDetailsLoader(getActivity(), createUrlRequest());
    }

    @Override
    public void onLoadFinished(Loader<List<MovieDetails>> loader, List<MovieDetails> data) {
        if (data != null && !data.isEmpty()) {
            moviesList = data;
            movieRecyclerAdapter = new MovieRecyclerAdapter(moviesList);
            recycle_view_movies.setAdapter(movieRecyclerAdapter);
            layoutManager.onRestoreInstanceState(layoutManagerState);
        }else{
            tv_no_connection.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<MovieDetails>> loader) {
        movieRecyclerAdapter = new MovieRecyclerAdapter(new ArrayList<MovieDetails>());
    }

    private void fetchMoviesData() {
        boolean isConnected = checkInternetConnection();

        if (isConnected) {
            tv_no_connection.setVisibility(View.INVISIBLE);
            android.support.v4.app.LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_MANAGER_ID, null, NowPlayingMoviesFragment.this);
        }else{
            tv_no_connection.setVisibility(View.VISIBLE);
        }

    }

    private boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    private String createUrlRequest() {
        Uri baseUri = Uri.parse(MOVIE_API_URL);
        Uri.Builder builder = baseUri.buildUpon();
        builder.appendPath(API_URL_ADD_NOW_PLAYING)
                .appendQueryParameter(API_PAR_KEY, PERSONAL_MOVIES_DB_API_KEY)
                .appendQueryParameter(REGION_PAR_KEY, QUERY_REGION);
        //Log.i("SexyUrl",builder.build().toString());
        return builder.build().toString();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(outState!=null){
            if(layoutManager!=null){
                layoutManagerState = layoutManager.onSaveInstanceState();
                outState.putParcelable(LAYOUT_MANAGER_STATE_KEY, layoutManagerState);
            }
        }
    }


}
