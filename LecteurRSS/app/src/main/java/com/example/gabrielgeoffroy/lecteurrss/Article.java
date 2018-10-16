package com.example.gabrielgeoffroy.lecteurrss;

/**
 *@author Arnaud Bégin, Carelle Chagnon, Gabriel Geoffroy, David Poissant Samson
 * Classe permettant de contenir les informations d'un article.
 */
public class Article {

    //region Variables
    public String titre;
    public String description;
    public String pubDate;
    public String link;
    public String guid;

    /**
     * Constructeur
     * @param titre Le titre de l'article
     * @param description La description de l'article
     * @param link Le lien de l'article
     */
    public Article(String titre, String description, String link, String guid, String pubDate) {
        this.titre = titre;
        this.description = description;
        this.link = link;
    }

    public Article(){};
    //endregion

    //region Méthodes

    //endregion

}
