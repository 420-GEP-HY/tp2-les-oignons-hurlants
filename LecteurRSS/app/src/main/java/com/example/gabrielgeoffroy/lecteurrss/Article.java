package com.example.gabrielgeoffroy.lecteurrss;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */
public class Article implements Parcelable {

    //region Variables
    public String titre;
    public String description;
    public String pubDate;
    public String link;
    public String guid;

    public Article(String titre, String description, String link) {
        this.titre = titre;
        this.description = description;
        this.link = link;
    }

    protected Article(Parcel in) {
        titre = in.readString();
        description = in.readString();
        pubDate = in.readString();
        link = in.readString();
        guid = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(titre);
            dest.writeString(description);
            dest.writeString(link);
    }
    //endregion

    //region Méthodes

    //endregion

}
