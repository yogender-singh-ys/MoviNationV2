package com.example.yogenders.movinationfinal.Assets;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;

import com.example.yogenders.movinationfinal.Adapters.VideoAdapter;
import com.example.yogenders.movinationfinal.Models.Video;
import com.example.yogenders.movinationfinal.ErrorActivity;
import com.example.yogenders.movinationfinal.VideoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class VideoFetch  extends AsyncTask<String, String, String> {

    protected VideoAdapter videoAdapter;
    protected ArrayList<Video> mVideoData;
    protected ListView listView;
    JSONArray results = null;
    Context context;



    public VideoFetch( VideoAdapter videoAdapter,ArrayList<Video> mVideoData, ListView listView,Context context) {
        this.videoAdapter = videoAdapter;
        this.mVideoData = mVideoData;
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

                Video video;

                results = jsonObj.getJSONArray(Asset.RESULTS);

                if( results.length() == 0){

                    VideoActivity.no_video.setVisibility(View.VISIBLE);

                }else{

                    for (int i = 0; i < results.length(); i++) {

                        JSONObject c = results.getJSONObject(i);

                        String key = c.getString(Asset.YT_KEY);

                        String country = c.getString(Asset.YT_COUNTRY);
                        String lang = c.getString(Asset.YT_LANG);
                        String name = c.getString(Asset.YT_NAME);
                        String site = c.getString(Asset.YT_SITE);
                        String type = c.getString(Asset.YT_TYPE);

                        video = new Video(country,lang,key,name,site,type);

                        mVideoData.add(video);

                    }

                    videoAdapter.updateData(mVideoData);
                    VideoActivity.progressBar.setVisibility(View.INVISIBLE);
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

        VideoActivity.progressBar.setVisibility(View.INVISIBLE);

    }



}


