package com.example.yogenders.movinationfinal.Assets;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;

import com.example.yogenders.movinationfinal.Models.Movie;
import com.example.yogenders.movinationfinal.Fragments.MovieFragment;
import com.example.yogenders.movinationfinal.ErrorActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class MovieFetch extends AsyncTask<String, String, String> {

    JSONArray results = null;
    Context context;

    public MovieFetch(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Asset.API_CALL = false;
        MovieFragment.progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    protected String doInBackground(String... urls) {

        try{

            URL url = new URL(urls[0].concat(Asset.CURRENT_PAGE_COUNT.toString()));
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
            return null;
        }




    }

    @Override
    protected void onPostExecute(String result) {

        if (result != null)
        {
            try{
                JSONObject jsonObj = new JSONObject(result);

                Movie item;

                // Getting JSON Array node
                Asset.CURRENT_PAGE_COUNT = Integer.parseInt(jsonObj.getString(Asset.PAGE));
                Asset.TOTAL_PAGE_COUNT = Integer.parseInt(jsonObj.getString(Asset.TOTAL_PAGE));
                results = jsonObj.getJSONArray(Asset.RESULTS);

                for (int i = 0; i < results.length(); i++) {
                    JSONObject c = results.getJSONObject(i);
                    String PosterPath = c.getString(Asset.POSTER_PATH);
                    String movieTitle = c.getString(Asset.ORIGINAL_TITLE);

                    String overview = c.getString(Asset.OVERVIEW);
                    String releaseDate = c.getString(Asset.RELEASE_DATE);
                    String backdropPath = c.getString(Asset.BACKDROP_PATH);
                    String voteCount = c.getString(Asset.VOTE_COUNT);
                    String voteAverage = c.getString(Asset.VOTE_AVERAGE);
                    String popularity = c.getString(Asset.POPULARITY);
                    String movie_id = c.getString(Asset.MOVIE_ID);

                    item = new Movie();
                    item.setTitle(movieTitle);
                    item.setImage(Asset.MOVIE_IMAGE_URL.concat(PosterPath));
                    item.setBackdrop_path(Asset.MOVIE_IMAGE_URL.concat(backdropPath));
                    item.setExtraInfo(overview, releaseDate, voteAverage, voteCount, popularity,movie_id);
                    MovieFragment.mGridData.add(item);

                }

                Asset.CURRENT_PAGE_COUNT++;
                MovieFragment.mMovieAdapter.setGridData(MovieFragment.mGridData);
                MovieFragment.progressBar.setVisibility(View.INVISIBLE);
                Asset.API_CALL= true;



            }catch(JSONException e){
                Intent errorIntent = new Intent (context, ErrorActivity.class);
                errorIntent.putExtra("errorText","Unable to process JSON data :-(");
                context.startActivity(errorIntent);
            }
        }
        else
        {

        }

        MovieFragment.progressBar.setVisibility(View.INVISIBLE);

    }


}
