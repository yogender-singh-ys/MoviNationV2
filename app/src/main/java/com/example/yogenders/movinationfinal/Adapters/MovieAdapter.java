package com.example.yogenders.movinationfinal.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yogenders.movinationfinal.Models.Movie;
import com.example.yogenders.movinationfinal.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MovieAdapter extends ArrayAdapter<Movie> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<Movie> mData = new ArrayList<Movie>();

    public MovieAdapter(Context mContext, int layoutResourceId, ArrayList<Movie> mData) {
        super(mContext, layoutResourceId, mData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mData = mData;
    }


    /**
     * Updates grid data and refresh grid items.
     * @param mData
     */

    public void setGridData(ArrayList<Movie> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) row.findViewById(R.id.movie_item);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Movie item = mData.get(position);

        Picasso.with(mContext)
                .load(item.getImage())
                .placeholder(R.drawable.preview)
                .error(R.drawable.img_error)
                .into(holder.imageView);


        return row;
    }

    static class ViewHolder {
        TextView titleTextView;
        ImageView imageView;
    }
}