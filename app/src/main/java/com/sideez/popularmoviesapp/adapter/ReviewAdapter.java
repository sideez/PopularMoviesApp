/*
 * Copyright (c) 2015. Sideez Inc.
 */

package com.sideez.popularmoviesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sideez.popularmoviesapp.R;
import com.sideez.popularmoviesapp.moviedb.MovieReview;

/**
 * Created by sideez on 2015-10-04.
 */
public class ReviewAdapter extends BaseAdapter {

    private Context mContext;
    private MovieReview[] mMovieReviews;

    public ReviewAdapter(Context context, MovieReview[] movieReviews) {
        mContext = context;
        mMovieReviews = movieReviews;
    }

    @Override
    public int getCount() {
        return mMovieReviews.length;
    }

    @Override
    public Object getItem(int position) {
        return mMovieReviews[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.review_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.reviewTextView = (TextView) convertView.findViewById(R.id.reviewTextView);
            viewHolder.ndashTextView = (TextView) convertView.findViewById(R.id.ndashTextView);
            viewHolder.authorTextView = (TextView) convertView.findViewById(R.id.authorTextView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MovieReview review = mMovieReviews[position];

        viewHolder.reviewTextView.setText(review.getContent());
        viewHolder.ndashTextView.setText("-");
        viewHolder.authorTextView.setText(review.getAuthorName());

        return convertView;
    }

    private static class ViewHolder {
        TextView reviewTextView;
        TextView ndashTextView;
        TextView authorTextView;
    }
}
