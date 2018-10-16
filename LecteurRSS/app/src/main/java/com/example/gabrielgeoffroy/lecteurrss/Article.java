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
    public String mediaLink;
    public String mediaType;
    public String guid;

    public Article(String titre, String description, String mediaLink, String mediaType, String guid) {
        this.titre = titre;
        this.description = description;
        this.mediaLink = mediaLink;
        this.mediaType = mediaType;
        this.guid = guid;
    }

    protected Article(Parcel in) {
        titre = in.readString();
        description = in.readString();
        mediaLink = in.readString();
        mediaType = in.readString();
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
            dest.writeString(mediaLink);
            dest.writeString(mediaType);
            dest.writeString(guid);
    }
    //endregion

    //region Méthodes

    //endregion

}
