package com.example.yogenders.movinationfinal.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yogenders.movinationfinal.Models.Video;
import com.example.yogenders.movinationfinal.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class VideoAdapter extends ArrayAdapter<Video> {

    private Context context;
    private int resource;
    private ArrayList<Video> mVideoData ;

    public VideoAdapter(Context context, int resource, ArrayList<Video> mmVideoData) {
        super(context, resource, mmVideoData);
        this.context = context;
        this.resource = resource;
        this.mVideoData = mmVideoData;

    }

    public void updateData(ArrayList<Video> mmVideoData)
    {
        this.mVideoData = mmVideoData;
        this.notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView imageView;
        TextView name;
        TextView country;
        TextView type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();

            holder.imageView = (ImageView) row.findViewById(R.id.video_item);
            holder.name = (TextView) row.findViewById(R.id.name);
            holder.country = (TextView) row.findViewById(R.id.country);
            holder.type = (TextView) row.findViewById(R.id.type);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Video item = mVideoData.get(position);

        Picasso.with(context)
                .load(item.getThumbnail())
                .placeholder(R.drawable.yt_d_2)
                .error(R.drawable.img_error)
                .into(holder.imageView);

        holder.name.setText(item.getName());
        holder.country.setText(item.getCountry());
        holder.type.setText(item.getType());

        return row;
    }
}
