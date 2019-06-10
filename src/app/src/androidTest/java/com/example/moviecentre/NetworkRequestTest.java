package com.example.moviecentre;

import com.example.moviecentre.models.MovieDetails;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class NetworkRequestTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();



    @Test
    public void testFetchMovieDataTrue() {
        List<MovieDetails> movieList = NetworkRequest.fetchMovieData("https://api.themoviedb.org/3/movie/now_playing?api_key=3a99e9b91f001ce52e943ec17f668e45");
        MovieDetails md = movieList.get(0);
        assertTrue(md instanceof MovieDetails);
    }
    @Test(expected = NullPointerException.class)
    public void testFetchMovieDataFalse() {
        List<MovieDetails> movieList = NetworkRequest.fetchMovieData("https://api.themoviedb.org/3/movie/now_playing?api_key=8484848");
        MovieDetails md = movieList.get(0);

    }
}

