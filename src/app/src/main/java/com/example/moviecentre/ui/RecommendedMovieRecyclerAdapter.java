package com.example.moviecentre.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moviecentre.R;
import com.example.moviecentre.models.Recommended;

import java.util.List;

public class RecommendedMovieRecyclerAdapter extends RecyclerView.Adapter<RecommendedMovieRecyclerAdapter.ExerciseViewHolder> {

    private List<Recommended> recommendedMovieList;

    public RecommendedMovieRecyclerAdapter(List<Recommended> recommendedMovieList) {
        this.recommendedMovieList = recommendedMovieList;
    }

    @NonNull
    @Override
    public RecommendedMovieRecyclerAdapter.ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_recycler_item, parent, false);
        return new RecommendedMovieRecyclerAdapter.ExerciseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecommendedMovieRecyclerAdapter.ExerciseViewHolder holder, final int position) {
        Recommended recommended = recommendedMovieList.get(position);
        holder.tv_recommended_movie.setText(recommended.getTitle());
    }

    @Override
    public int getItemCount() {
        return recommendedMovieList.size();
    }

    public void addItem(Recommended recommended){
        recommendedMovieList.add(recommended);
        notifyItemInserted(recommendedMovieList.size() -1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public class ExerciseViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_recommended_movie;

        public ExerciseViewHolder(final View itemView) {
            super(itemView);

            tv_recommended_movie = itemView.findViewById(R.id.tv_recommended_movie);

        }
    }


}