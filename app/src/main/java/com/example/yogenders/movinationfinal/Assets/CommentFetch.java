package com.example.yogenders.movinationfinal.Assets;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;

import com.example.yogenders.movinationfinal.Adapters.CommentAdapter;
import com.example.yogenders.movinationfinal.CommentActivity;
import com.example.yogenders.movinationfinal.Models.Comment;
import com.example.yogenders.movinationfinal.ErrorActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class CommentFetch extends AsyncTask<String, String, String> {

    protected CommentAdapter commentAdapter;
    protected ArrayList<Comment> mCommentData;
    protected ListView listView;
    JSONArray results = null;
    Context context;

    public CommentFetch( CommentAdapter commentAdapter,ArrayList<Comment> mCommentData, ListView listView,Context context) {
        this.commentAdapter = commentAdapter;
        this.mCommentData = mCommentData;
        this.listView = listView;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... urls) {
        try{
            URL url = new URL(urls[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }

        }catch(Exception e) {

            Intent errorIntent = new Intent (context, ErrorActivity.class);
            errorIntent.putExtra("errorText", "Unable to process DATA URL :-(");
            context.startActivity(errorIntent);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {


        if (result != null)
        {
            try{
                JSONObject jsonObj = new JSONObject(result);

                Comment comment;

                results = jsonObj.getJSONArray(Asset.RESULTS);

                if( results.length() == 0){

                    CommentActivity.no_comment.setVisibility(View.VISIBLE);

                }else {

                    for (int i = 0; i < results.length(); i++) {

                        JSONObject c = results.getJSONObject(i);

                        String author = c.getString(Asset.AUTHOR);
                        String content = c.getString(Asset.CONTENT);

                        comment = new Comment(author,content);
                        mCommentData.add(comment);

                    }

                    commentAdapter.updateData(mCommentData);
                    CommentActivity.progressBar.setVisibility(View.INVISIBLE);

                }




            }catch(JSONException e){
                Intent errorIntent = new Intent (context, ErrorActivity.class);
                errorIntent.putExtra("errorText","Unable to process JSON data :-(");
                context.startActivity(errorIntent);
            }
        }
        else
        {
            Intent errorIntent = new Intent (context, ErrorActivity.class);
            errorIntent.putExtra("errorText","Post execute result is null :-(");
            context.startActivity(errorIntent);
        }

        CommentActivity.progressBar.setVisibility(View.INVISIBLE);

    }

}
