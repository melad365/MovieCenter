package com.example.moviecentre.ui;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.moviecentre.R;
import com.example.moviecentre.models.MovieDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.moviecentre.Constant.POSTER_PATH;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MoviesHolder>{

    private List<MovieDetails> moviesList;

    public MovieRecyclerAdapter(List<MovieDetails> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MoviesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_recycler_item, parent, false);
        return new MoviesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MoviesHolder holder, int position) {
        MovieDetails movieDetails = moviesList.get(position);
        if(movieDetails.getPoster().isEmpty() || movieDetails.getPoster().equalsIgnoreCase("null")){
            holder.iv_movie_image.setImageResource(R.drawable.no_poster_available);
        }else{
            String createPosterPath = POSTER_PATH + movieDetails.getPoster();
            Context context = holder.iv_movie_image.getContext();
            Picasso.with(context).load(createPosterPath).into(holder.iv_movie_image);
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class MoviesHolder extends RecyclerView.ViewHolder{

        private ImageView iv_movie_image;

        public MoviesHolder(View itemView) {
            super(itemView);
            iv_movie_image = itemView.findViewById(R.id.iv_movie_image);
        }
    }

    public void addItem(MovieDetails exercise){
        moviesList.add(exercise);
        notifyItemInserted(moviesList.size() -1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}