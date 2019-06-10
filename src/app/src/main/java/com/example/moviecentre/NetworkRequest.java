package com.example.moviecentre;

import android.util.Log;

import com.example.moviecentre.models.MovieDetails;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

public class NetworkRequest {

    private static final String LOG_TAG = NetworkRequest.class.getName();

    //Http Request
    private static String makeHttpRequest(URL url) throws IOException {

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String jsonResponse = "";

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(1000);
            urlConnection.setConnectTimeout(1500);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.v(LOG_TAG, "Error: Problem occurred in the responding code");
            }

        } catch (Exception e) {
            Log.v(LOG_TAG, "Error: Problem occurred in making http request");
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
            if (inputStream != null) inputStream.close();
        }

        return jsonResponse;
    }

    //Convert InputStream to String
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    //Create URL format
    private static URL createURL(String stringURL) {
        URL ur1 = null;
        try {
            ur1 = new URL(stringURL);
        } catch (MalformedURLException e) {
            Log.v(LOG_TAG, "Error: a problem occurred in creating the URL");
        }
        return ur1;
    }

    /**
     * Fetch Movie Details
     * @param requestJsonUrl Json URL
     * @return List of movie objects
     */
    static List<MovieDetails> fetchMovieData(String requestJsonUrl) {

        String jsonResponse = "";

        URL url = createURL(requestJsonUrl);

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.v(LOG_TAG, "Error: Problem occurred in fetching movie details through the request");
        }

        return JsonMovieDetails.extractJsonMovieData(jsonResponse);
    }


}

