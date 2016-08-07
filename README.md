# Overview
This Android app displays a movie poster in `GridView` and allows to click on poster to get more information about the movie in a new `Activity`.
Allows to sort the movie grid by popular and top rated, and also you can mark you favourite movie as Fav for offline mode.

# Includes
- `GridView` example
- Creating and Using a Custom `ArrayAdapter` for `GridView`
- `Parcelables` and `onSaveInstanceState()` 
- How to Use the Movie Database API
- ActiveAndroid DB library
- Implicit `Intent` & Explicit `Intent`
- Master-Detail layout implemented using fragments for Tablets
- Fragments
- loading image with Picasso

# How to use App 
- You will use the API from themoviedb.org. If you don’t already have an account, you will need to create one in order to request an API Key. 
- Download and replace your key in /Assets/Asset.java 
````
public class Asset {
    ...
    public static final String API_KEY = "REPLACE API KEY HERE";
    ...
}    
````
- Gradle compile and enjoy.

# Known issues
- Needs better UI for Detailed page 

# Suggestions
Any kind of code enhancement and improvements are highly appreciated. Please fork or create a brach. Thanks :-)


