package com.example.gabrielgeoffroy.lecteurrss;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

/**
 *@author Arnaud Bégin, Carelle Chagnon, Gabriel Geoffroy, David Poissant Samson
 * Classe permettant de contenir les informations d'un flux RSS.
 */
public class Chaine implements Serializable{
    //region Variables
    public String titre;
    public String description;
    public String lien;
    public String urlImage;
    public Bitmap image;
    public List<Article> articles;

    /**
     * Constructeur de base
     */
    public Chaine() {

    }

    /**
     * Constructeur de chaine avec ses informations.
     * @param titre Le titre de la chaine
     * @param lien L'URL de la chaine
     * @param description Le description de la chaine
     * @param image L'URL de l'image de la chaine
     */
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
