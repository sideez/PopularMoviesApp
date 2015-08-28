package com.sideez.popularmoviesapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

}
