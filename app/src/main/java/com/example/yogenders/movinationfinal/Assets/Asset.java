package com.example.yogenders.movinationfinal.Assets;


import com.example.yogenders.movinationfinal.Models.Comment;
import com.example.yogenders.movinationfinal.Models.Movie;
import com.example.yogenders.movinationfinal.Models.Video;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Asset {


    public static final String API_KEY = "a9228d58b56b46a0e9d3b8add62b6555";

    // URL VARS
    public static final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/";
    public static final String MOVIE_IMAGE_URL = "http://image.tmdb.org/t/p/w342/";
    public static final String MOVIE_POPULAR = "movie/popular";
    public static final String MOVIE_TOP_RATED = "movie/top_rated";

    // JSON Node names for Movie data
    public static final String PAGE = "page";
    public static final String TOTAL_PAGE = "total_pages";
    public static final String RESULTS = "results";
    public static final String POSTER_PATH = "poster_path";
    public static final String MOVIE_ID = "id";

    public static final String ORIGINAL_TITLE = "original_title";
    public static final String OVERVIEW = "overview";
    public static final String RELEASE_DATE = "release_date";
    public static final String BACKDROP_PATH = "backdrop_path";
    public static final String VOTE_COUNT = "vote_count";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String POPULARITY = "popularity";

    public static final String YT_COUNTRY = "iso_3166_1";
    public static final String YT_LANG = "iso_639_1";
    public static final String YT_KEY = "key";
    public static final String YT_NAME = "name";
    public static final String YT_SITE = "site";
    public static final String YT_TYPE = "type";


    public static final String AUTHOR = "author";
    public static final String CONTENT = "content";

    public static String MENU_ITEM_SELECTED = "POPULAR";

    public static Boolean API_CALL = false;
    public static Integer CURRENT_PAGE_COUNT = 1;
    public static Integer TOTAL_PAGE_COUNT = 1;

    public static String URL_POPULAR = "POPULAR";
    public static String URL_TOP = "TOP";



    public static String decideAPIURL(String apiURL) {
        String BaseURL = null;

        if(apiURL.equals("POPULAR")){
            BaseURL = MOVIE_BASE_URL.concat(MOVIE_POPULAR).concat("?api_key=").concat(API_KEY).concat("&page=");
        }
        else if (apiURL.equals("TOP"))
        {
            BaseURL = MOVIE_BASE_URL.concat(MOVIE_TOP_RATED).concat("?api_key=").concat(API_KEY).concat("&page=");
        }

       return BaseURL;
    }

    // added comment
    public static void resetPaging() {
        TOTAL_PAGE_COUNT = 1;
        CURRENT_PAGE_COUNT = 1;
    }

    public static String getVideoFetchUrl(String Key) {
        String ytUrl = MOVIE_BASE_URL.concat("movie/").concat(Key).concat("/videos?api_key=").concat(API_KEY);
        return ytUrl;

    }

    public static String getCommentFetchUrl(String Key) {
        String ytUrl = MOVIE_BASE_URL.concat("movie/").concat(Key).concat("/reviews?api_key=").concat(API_KEY);
        return ytUrl;

    }

    public static String fetchDataFromURL(String urlStr) {
       try {

           URL url = new URL(urlStr);
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

       }catch (Exception e){
           return null;
       }
    }

    public static ArrayList<Video> getVideos(String jsonDataStr) {
        JSONArray results = null;
        ArrayList<Video> videosArray = new ArrayList<Video>() ;

        try{
            JSONObject jsonObj = new JSONObject(jsonDataStr);
            results = jsonObj.getJSONArray(Asset.RESULTS);
            if( results.length() != 0){
                for (int i = 0; i < results.length(); i++) {

                    JSONObject c = results.getJSONObject(i);

                    String key = c.getString(Asset.YT_KEY);

                    String country = c.getString(Asset.YT_COUNTRY);
                    String lang = c.getString(Asset.YT_LANG);
                    String name = c.getString(Asset.YT_NAME);
                    String site = c.getString(Asset.YT_SITE);
                    String type = c.getString(Asset.YT_TYPE);

                    Video video = new Video(country,lang,key,name,site,type);
                    videosArray.add(video);

                }
                return videosArray;
            }else {
                return null;
            }
        }
        catch (Exception e){
            return null;
        }
    }

    public static ArrayList<Movie> getMovies(String jsonDataStr) {
        JSONArray results = null;
        ArrayList<Movie> movieArrayListArray = new ArrayList<Movie>() ;

        try{
            JSONObject jsonObj = new JSONObject(jsonDataStr);
            results = jsonObj.getJSONArray(Asset.RESULTS);
            if( results.length() != 0){
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

                    Movie movie = new Movie();
                    movie.setTitle(movieTitle);
                    movie.setImage(Asset.MOVIE_IMAGE_URL.concat(PosterPath));
                    movie.setBackdrop_path(Asset.MOVIE_IMAGE_URL.concat(backdropPath));
                    movie.setExtraInfo(overview, releaseDate, voteAverage, voteCount, popularity,movie_id);

                    movieArrayListArray.add(movie);
                }
                return movieArrayListArray;
            }else {
                return null;
            }
        }
        catch (Exception e){
            return null;
        }
    }

    public static ArrayList<Comment> getComments(String jsonDataStr) {
        JSONArray results = null;
        ArrayList<Comment> commentArrayListArray = new ArrayList<Comment>() ;

        try{
            JSONObject jsonObj = new JSONObject(jsonDataStr);
            results = jsonObj.getJSONArray(Asset.RESULTS);
            if( results.length() != 0){
                for (int i = 0; i < results.length(); i++) {
                    JSONObject c = results.getJSONObject(i);

                    String author = c.getString(Asset.AUTHOR);
                    String content = c.getString(Asset.CONTENT);

                    Comment comment = new Comment(author,content);
                    commentArrayListArray.add(comment);
                }
                return commentArrayListArray;
            }else {
                return null;
            }
        }
        catch (Exception e){
            return null;
        }
    }






}
