/*
 * Copyright (c) 2015. Sideez Inc.
 */
package com.sideez.popularmoviesapp.moviedb;

/**
 * Created by sideez on 2015-08-13.
 */
public class Movie {

    private int mMovieID;
    private String mTitle;
    private String mOverview;
    private String mPoster;
    private double mRating;
    private String mReleaseDate;

    public Movie() {

    }

    public int getMovieID() {
        return mMovieID;
    }

    public void setMovieID(int movieID) {
        mMovieID = movieID;
    }

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
        mPoster = "http://image.tmdb.org/t/p/w185" + poster;
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

    // Binding data for parcel

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(mMovieID);
//        dest.writeString(mTitle);
//        dest.writeString(mOverview);
//        dest.writeString(mPoster);
//        dest.writeDouble(mRating);
//        dest.writeString(mReleaseDate);
//    }
//
//    private Movie(Parcel in) {
//        mMovieID = in.readInt();
//        mTitle = in.readString();
//        mOverview = in.readString();
//        mPoster = in.readString();
//        mRating = in.readDouble();
//        mReleaseDate = in.readString();
//    }
//
//    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
//        @Override
//        public Movie createFromParcel(Parcel source) {
//            return new Movie(source);
//        }
//
//        @Override
//        public Movie[] newArray(int size) {
//            return new Movie[size];
//        }
//    };
}
