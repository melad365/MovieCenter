package com.example.moviecentre.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moviecentre.R;
import com.example.moviecentre.models.Recommended;
import com.example.moviecentre.ui.RecommendedMovieRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommendedFragment extends Fragment {

    //Members Declaration
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    //Declarations
    RecommendedMovieRecyclerAdapter adapter;
    List<Recommended>  recommendedMovieList;

    @BindView(R.id.recycle_view_movies)
    RecyclerView recycle_view_movies;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies, container,false);
        ButterKnife.bind(this, rootView);


        //Members Initialization
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Recommended");


        //Views initialization
        recycle_view_movies.setHasFixedSize(true);
        recycle_view_movies.setLayoutManager(new LinearLayoutManager(getActivity()));
        recommendedMovieList = new ArrayList<>();
        adapter = new RecommendedMovieRecyclerAdapter(recommendedMovieList);
        recycle_view_movies.setAdapter(adapter);

        attachDatabaseReadListener();



        return rootView;
    }



    //Listener to retrieve and keep up to date with any changes in the Firebase database to update
    private void attachDatabaseReadListener(){
        recommendedMovieList.clear();
        if(childEventListener == null){
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //recommendedMovieList.clear();
                    // Get the data of the new insertion in the parameter, it will deserialize the data from the database
                    //It does that because the fields matches what is in the database
                    Recommended recommended = dataSnapshot.getValue(Recommended.class);
                    recommendedMovieList.add(recommended);
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    Recommended recommended2 = dataSnapshot.getValue(Recommended.class);
                    //recommendedMovieList.add(recommended2);
                    //adapter.notifyDataSetChanged();
                }
                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            };
            databaseReference.addChildEventListener(childEventListener);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(getActivity(),"OnStart",Toast.LENGTH_SHORT).show();

    }

}