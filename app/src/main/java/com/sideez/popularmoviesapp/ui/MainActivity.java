
/*
 * Copyright (c) 2015. Sideez Inc.
 */

package com.sideez.popularmoviesapp.ui;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sideez.popularmoviesapp.moviedb.Movie;
import com.sideez.popularmoviesapp.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Movie[] mMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String order = "popularity.desc";

        final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
        final String QUERY_PARAM = "sort_by";
        final String API_QUERY_PARAM = "api_key";
        final String API_KEY = getString(R.string.api_key);

        String movieDBURL = BASE_URL + QUERY_PARAM + "=" + order + "&" + API_QUERY_PARAM + "=" + API_KEY;

        Log.i(TAG, "URL: " + movieDBURL);

        if (isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(movieDBURL).build();
            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {

                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            mMovies = getMovieDetails(jsonData);
                        } else {
                            alertUserAboutError();
                        }

                    } catch (IOException | JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }

                }
            });

        }

    }

    private void alertUserAboutError() {

        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");

    }

    private Movie[] getMovieDetails(String jsonData) throws JSONException {

        JSONObject data = new JSONObject(jsonData);
        JSONArray results = data.getJSONArray("results");

        Movie[] movies = new Movie[results.length()];

        for (int i = 0; i < results.length(); i++) {
            JSONObject jsonMovie = results.getJSONObject(i);

            Movie movie = new Movie();
            movie.setMovieID(jsonMovie.getInt("id"));
            movie.setOverview(jsonMovie.getString("overview"));
            movie.setPoster(jsonMovie.getString("poster_path"));
            movie.setRating(jsonMovie.getDouble("vote_average"));
            movie.setReleaseDate(jsonMovie.getString("release_date"));
            movie.setTitle(jsonMovie.getString("original_title"));

            movies[i] = movie;

        }

        return movies;
    }

    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        boolean isAvailable = false;

        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        } else {
            Toast.makeText(this, "Network unavailable!", Toast.LENGTH_LONG).show();
        }

        return isAvailable;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}