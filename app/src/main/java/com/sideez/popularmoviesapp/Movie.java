/*
 * Copyright (c) 2015. Sideez Inc.
 */
package com.sideez.popularmoviesapp;

/**
 * Created by sideez on 2015-08-13.
 */
public class Movie {
    private String mTitle;
    private String mOverview;
    private String mPoster;
    private double mRating;
    private String mReleaseDate;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public String getPoster() {
        return mPoster;
    }

    public void setPoster(String poster) {
        mPoster = poster;
    }

    public double getRating() {
        return mRating;
    }

    public void setRating(double rating) {
        mRating = rating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }
}
