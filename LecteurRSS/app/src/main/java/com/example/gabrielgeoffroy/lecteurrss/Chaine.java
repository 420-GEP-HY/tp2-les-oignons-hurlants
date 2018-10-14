package com.example.gabrielgeoffroy.lecteurrss;

import android.media.Image;

import java.util.List;

/**
 *
 */
public class Chaine {
    //region Variables
    public String titre;
    public String auteur;
    public String description;
    public String language;
    public Image image;
    public List<Article> articles;

    public Chaine(String titre) {
        this.titre = titre;
    }
    //endregion

    //region Méthodes

    //endregion

}
