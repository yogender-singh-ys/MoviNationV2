package com.example.yogenders.movinationfinal.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "GridItems")
public class Movie extends Model implements Parcelable {

    @Column(name = "image")
    private String image;

    @Column(name = "overview")
    private String overview;

    @Column(name = "release_date")
    private String release_date;

    @Column(name = "name")
    private String title;

    @Column(name = "backdrop")
    private String backdrop;

    @Column(name = "vote_average")
    private String vote_average;

    @Column(name = "vote_count")
    private String vote_count;

    @Column(name = "popularity")
    private String popularity;

    @Column(name = "movieKeyId")
    private String movieKeyId;

    // constructor
    public Movie() {
        super();
    }

    // fetch all Movie item from database
    public List<Movie> getAllSavedItem()
    {
        return new Select().from(Movie.class).execute();
    }

    public static Movie getMovie(String movieKeyId) {
        return new Select()
                .from(Movie.class)
                .where("movieKeyId = ?", movieKeyId)
                .executeSingle();
    }

    public static void deleteMovie(String movieKeyId){
        new Delete().from(Movie.class)
                    .where("movieKeyId = ?",movieKeyId)
                    .execute();
    }

    /**
     * Methods
     * - all these methods will set values to properties
     */

    public void setImage(String image) {
        this.image = image;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setBackdrop_path(String backdrop_path)
    {
        this.backdrop = backdrop_path;
    }

    public void setExtraInfo(String overview, String release_date, String vote_average, String vote_count, String popularity,String id)
    {
        this.overview = overview;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.popularity = popularity;
        this.movieKeyId = id;

    }

    /**
     * Methods
     * - all these methods will return values of properties
     */

    public String getMovieKeyId(){
        return movieKeyId;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview()
    {
        return  this.overview;
    }

    public String getRelease_date()
    {
        return this.release_date;
    }

    public String getVote_average()
    {
        return  this.vote_average;
    }

    public String getBackdropPath()
    {
        return this.backdrop;

    }

    public String getVote_count()
    {
        return this.vote_count;
    }

    public String getPopularity()
    {
        return this.popularity;
    }


    /**
     * Method
     * - This method convert Object to a Parcelable
     */

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(image);
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeString(backdrop);
        parcel.writeString(vote_average);
        parcel.writeString(vote_count);
        parcel.writeString(popularity);
        parcel.writeString(movieKeyId);
    }

    /**
     * Method
     * - This method retrieves Object from a Parcelable
     */

    private Movie(Parcel in){
        image = in.readString();
        title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        backdrop = in.readString();

        vote_count = in.readString();

        vote_average = in.readString();
        popularity = in.readString();
        movieKeyId = in.readString();

    }

    /**
     * Methods
     * - All these methods helps in using & implementing Parcelable concepts
     */

    @Override
    public int describeContents() {
        return 0;
    }


    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }

    };

}
