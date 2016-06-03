package com.example.yogenders.movinationfinal.Assets;


public class Asset {


    public static final String API_KEY = "REPLACE API KEY HERE";

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



    public static String decideAPIURL(String apiURL)
    {
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


    public static void resetPaging()
    {
        TOTAL_PAGE_COUNT = 1;
        CURRENT_PAGE_COUNT = 1;
    }

    public static String getVideoFetchUrl(String Key)
    {
        String ytUrl = MOVIE_BASE_URL.concat("movie/").concat(Key).concat("/videos?api_key=").concat(API_KEY);
        return ytUrl;

    }

    public static String getCommentFetchUrl(String Key)
    {
        String ytUrl = MOVIE_BASE_URL.concat("movie/").concat(Key).concat("/reviews?api_key=").concat(API_KEY);
        return ytUrl;

    }



}
