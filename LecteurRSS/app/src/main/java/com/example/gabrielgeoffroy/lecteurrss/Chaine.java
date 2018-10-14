package com.example.gabrielgeoffroy.lecteurrss;

import android.graphics.Bitmap;

import java.util.List;

/**
 *
 */
public class Chaine {
    //region Variables
    public String titre;
    public String description;
    public String lien;
    public String urlImage;
    public Bitmap image;
    public List<Article> articles;

    public Chaine() {

    }

    public Chaine(String titre){
        this.titre = titre;
    }

    public Chaine(String titre, String lien, String description){
        this.titre = titre;
        this.lien = lien;
        this.description = description;
    }

    public Chaine(String titre, String lien, String description, String image){
        this.titre = titre;
        this.lien = lien;
        this.description = description;
        this.urlImage = image;
    }
    //endregion

    //region Méthodes

    //endregion

}
