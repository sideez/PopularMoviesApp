/*
 * Copyright (c) 2015. Sideez Inc.
 */

package com.sideez.popularmoviesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sideez.popularmoviesapp.R;
import com.sideez.popularmoviesapp.moviedb.MovieTrailer;

/**
 * Created by sideez on 2015-10-04.
 */
public class TrailerAdapter extends BaseAdapter {

    private Context mContext;
    private MovieTrailer[] mMovieTrailers;

    public TrailerAdapter(Context context, MovieTrailer[] movieTrailers) {
        mContext = context;
        mMovieTrailers = movieTrailers;
    }

    @Override
    public int getCount() {
        return mMovieTrailers.length;
    }

    @Override
    public Object getItem(int position) {
        return mMovieTrailers[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.trailer_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.playIconImageView = (ImageView) convertView.findViewById(R.id.playIconImageView);
            viewHolder.trailerTitleTextView = (TextView) convertView.findViewById(R.id.trailerTitleTextView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MovieTrailer trailer = mMovieTrailers[position];

        viewHolder.playIconImageView.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        viewHolder.trailerTitleTextView.setText(trailer.getTrailerTitle());

        return convertView;
    }

    private static class ViewHolder {
        ImageView playIconImageView;
        TextView trailerTitleTextView;
    }
}
