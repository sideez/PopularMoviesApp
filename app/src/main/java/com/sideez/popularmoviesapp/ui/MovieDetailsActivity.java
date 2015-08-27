package com.sideez.popularmoviesapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.sideez.popularmoviesapp.R;
import com.sideez.popularmoviesapp.moviedb.Movie;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {

    private final static String TAG = MovieDetailsActivity.class.getSimpleName();

    private Movie mMovie;

    @Bind(R.id.posterThumbnailImageView) ImageView mPosterThumbnail;
    @Bind(R.id.originalTitleTextView) TextView mTitle;
    @Bind(R.id.releaseDateTextView) TextView mReleaseDate;
    @Bind(R.id.ratingTextView) TextView mRating;
    @Bind(R.id.overviewTextView) TextView mOverview;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_details, menu);
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
        } else if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
