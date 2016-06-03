package com.example.yogenders.movinationfinal.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.yogenders.movinationfinal.Models.Comment;
import com.example.yogenders.movinationfinal.R;

import java.util.ArrayList;


public class CommentAdapter extends ArrayAdapter<Comment> {

    private Context context;
    private int resource;
    private ArrayList<Comment> mmCommentData ;


    public CommentAdapter(Context context, int resource, ArrayList<Comment> mmCommentData) {
        super(context, resource, mmCommentData);
        this.context = context;
        this.resource = resource;
        this.mmCommentData = mmCommentData;

    }

    public void updateData(ArrayList<Comment> mmCommentData)
    {
        this.mmCommentData = mmCommentData;
        this.notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView comment;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.comment = (TextView) row.findViewById(R.id.content);
            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        Comment item = mmCommentData.get(position);

        holder.comment.setText(item.getContent());

        return row;
    }
}
