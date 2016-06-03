package com.example.yogenders.movinationfinal.Models;

import android.os.Parcel;
import android.os.Parcelable;


public class Video implements Parcelable {

    private String country,lang, key, name, site, type;

    public Video() {
    }



    public Video (String country, String lang, String key, String name, String site, String type )
    {
        this.country = country;
        this.lang = lang;
        this.key = key;
        this.name = name;
        this.site = site;
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

        parcel.writeString(country);
        parcel.writeString(lang);
        parcel.writeString(key);
        parcel.writeString(name);
        parcel.writeString(site);
        parcel.writeString(type);
    }

    public Video(Parcel in) {

        country = in.readString();
        lang = in.readString();
        key = in.readString();
        name = in.readString();
        site = in.readString();
        type = in.readString();
    }

    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel parcel) {
            return new Video(parcel);
        }

        @Override
        public Video[] newArray(int i) {
            return new Video[i];
        }

    };

    // all get methods

    public String getCountry()
    {
        return country;
    }

    public String getLang()
    {
        return lang;
    }

    public String getKey()
    {
        return key;
    }

    public String getName()
    {
        return name;
    }

    public String getSite()
    {
        return site;
    }

    public String getVideoURL()
    {
        return "https://www.youtube.com/watch?v="+key;
    }

    public String getType()
    {
        return type;
    }

    public String getThumbnail()
    {
        return "https://img.youtube.com/vi/"+key+"/hqdefault.jpg";
    }


}
