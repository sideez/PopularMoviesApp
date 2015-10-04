package com.sideez.popularmoviesapp.ui;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sideez.popularmoviesapp.R;
import com.sideez.popularmoviesapp.moviedb.Movie;
import com.sideez.popularmoviesapp.moviedb.MovieReview;
import com.sideez.popularmoviesapp.moviedb.MovieTrailer;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {

    private final static String TAG = MovieDetailsActivity.class.getSimpleName();

    private Movie mMovie;
    private MovieTrailer[] mMovieTrailers;
    private MovieReview[] mMovieReviews;

    @Bind(R.id.posterThumbnailImageView) ImageView mPosterThumbnail;
    @Bind(R.id.originalTitleTextView) TextView mTitle;
    @Bind(R.id.releaseDateTextView) TextView mReleaseDate;
    @Bind(R.id.ratingTextView) TextView mRating;
    @Bind(R.id.overviewTextView) TextView mOverview;
    @Bind(R.id.favButton) Button mFavButton;
    @Bind(R.id.trailersListView) ListView mTrailersListView;
    @Bind(R.id.reviewsListView) ListView mReviewsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mMovie = intent.getParcelableExtra(Intent.EXTRA_TEXT);

        mTitle.setText(mMovie.getTitle());
        mReleaseDate.setText(mMovie.getReleaseDate());
        mRating.setText(mMovie.getRating());
        mOverview.setText(mMovie.getOverview());
        Picasso.with(this)
                .load(mMovie.getPoster())
                .placeholder(R.drawable.poster_loading)
                .error(R.drawable.poster_not_found)
                .into(mPosterThumbnail);

        fetchMovieTrailers();
        fetchMovieReviews();
    }

    private void fetchMovieReviews() {


        String movieID = mMovie.getMovieID() + "";
        final String API_KEY = getString(R.string.api_key);
        final String API_KEY_QUARY_PRAM = "api_key";
        String movieReviewUrl = "http://api.themoviedb.org/3/movie/"
                + movieID + "/reviews" + "?" + API_KEY_QUARY_PRAM + "=" + API_KEY;

        Log.i(TAG, movieReviewUrl);

        if (isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(movieReviewUrl).build();
            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {

                    try {
                        String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            mMovieReviews = getMovieReviews(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    displayReviews(mMovieReviews);
                                }
                            });
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

    private void displayReviews(MovieReview[] movieReviews) {

    }

    private MovieReview[] getMovieReviews(String jsonData) throws JSONException {

        JSONObject data = new JSONObject(jsonData);
        JSONArray results = data.getJSONArray("results");

        MovieReview[] movieReviews = new MovieReview[results.length()];

        for (int i = 0; i < results.length(); i++) {
            JSONObject jsonMovie = results.getJSONObject(i);

            MovieReview movieReview = new MovieReview();
            movieReview.setAuthorName(jsonMovie.getString("author"));
            movieReview.setContent(jsonMovie.getString("content"));

            movieReviews[i] = movieReview;

        }

        return movieReviews;

    }

    private void fetchMovieTrailers() {

        String movieID = mMovie.getMovieID() + "";
        final String API_KEY = getString(R.string.api_key);
        final String API_KEY_QUARY_PRAM = "api_key";
        String movieTrailerUrl = "http://api.themoviedb.org/3/movie/"
                + movieID + "/videos" + "?" + API_KEY_QUARY_PRAM + "=" + API_KEY;

        if (isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(movieTrailerUrl).build();
            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {

                    try {
                        String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            mMovieTrailers = getMovieTrailers(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    displayTrailers(mMovieTrailers);
                                }
                            });
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

    private void displayTrailers(MovieTrailer[] movieTrailers) {

        String[] daysOfTheWeek = {"Sunday", "Monday", "Tuesday"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, daysOfTheWeek);
        mTrailersListView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(mTrailersListView);
    }

    private MovieTrailer[] getMovieTrailers(String jsonData) throws JSONException {

        JSONObject data = new JSONObject(jsonData);
        JSONArray results = data.getJSONArray("results");

        MovieTrailer[] movieTrailers = new MovieTrailer[results.length()];

        for (int i = 0; i < results.length(); i++) {
            JSONObject jsonMovie = results.getJSONObject(i);

            MovieTrailer movieTrailer = new MovieTrailer();
            movieTrailer.setKey(jsonMovie.getString("key"));
            movieTrailer.setTrailerTitle(jsonMovie.getString("name"));

            movieTrailers[i] = movieTrailer;

        }

        return movieTrailers;

    }

    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        boolean isAvailable = false;

        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        } else {
            Toast.makeText(this, "Network unavailable!", Toast.LENGTH_SHORT).show();
        }

        return isAvailable;

    }

    private void alertUserAboutError() {

        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");

    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView
     **** Credit: http://stackoverflow.com/users/2660283/arshu
     **** Stackoverflow: http://stackoverflow.com/questions/18367522/android-list-view-inside-a-scroll-view
     ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}
