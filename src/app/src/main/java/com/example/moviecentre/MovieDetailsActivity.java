package com.example.moviecentre;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.LoaderManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviecentre.models.MovieDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.moviecentre.Constant.API_PAR_KEY;
import static com.example.moviecentre.Constant.LAYOUT_MANAGER_STATE_KEY;
import static com.example.moviecentre.Constant.LOADER_MANAGER_ID;

import static com.example.moviecentre.Constant.MOVIE_API_URL;
import static com.example.moviecentre.Constant.QUERY_VIDEOS_REVIEWS;
import static com.example.moviecentre.Constant.POSTER_PATH;
import static com.example.moviecentre.Constant.APPEND_TO_RESPONSE_KEY;

import static com.example.moviecentre.Constant.MOVIE_INTENT_KEY;

public class MovieDetailsActivity extends AppCompatActivity {

    @BindView(R.id.tv_synopsis)
    TextView tv_synopsis;

    @BindView(R.id.iv_poster)
    ImageView iv_poster;

    @BindView(R.id.rating_bar)
    RatingBar rating_bar;

    @BindView(R.id.btn_rating)
    Button btn_rating;

    @BindView(R.id.tv_release_date)
    TextView tv_release_date;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("Movies");

    MovieDetails movieDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        Intent getTheIntentThatStartedThisActivity = getIntent();
        movieDetails = getTheIntentThatStartedThisActivity.getParcelableExtra(MOVIE_INTENT_KEY);

        String createPosterPath = POSTER_PATH + movieDetails.getPoster();

        Picasso.with(this).load(createPosterPath).into(iv_poster);

        tv_synopsis.setText(movieDetails.getSynopsis());

        btn_rating.setText(movieDetails.getVote());

        tv_release_date.setText(movieDetails.getReleaseDate());

        rating_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                float x = ratingBar.getRating();
                if(ratingBar.getRating() > 4){
                    Toast.makeText(getApplicationContext(),String.valueOf(x),Toast.LENGTH_LONG).show();
                    myRef.child(movieDetails.getTitle()).setValue(movieDetails.getTitle());
                }else{
                    Toast.makeText(getApplicationContext(),String.valueOf(x),Toast.LENGTH_LONG).show();
                    myRef.child(movieDetails.getTitle()).removeValue();
                }
                saveRating();
            }
        });

        retrieveRating(); //Restore the rating if some
    }

    private void saveRating(){
        SharedPreferences.Editor editor = getSharedPreferences(movieDetails.getTitle(), MODE_PRIVATE).edit();
        editor.putFloat("rating", rating_bar.getRating());
        editor.apply();
    }

    private void retrieveRating() {
        SharedPreferences prefs = getSharedPreferences(movieDetails.getTitle(), MODE_PRIVATE);
        float restoredRating = prefs.getFloat("rating", 0);
        rating_bar.setRating(restoredRating);
    }
}
