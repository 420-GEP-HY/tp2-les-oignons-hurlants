package com.example.gabrielgeoffroy.lecteurrss;

/**
 *
 */
public class Article {

    //region Variables
    public String titre;
    public String description;
    public String pubDate;
    public String link;
    public String guid;
    //endregion

    //region Méthodes
    public Article(String titre, String description, String link) {
        this.titre = titre;
        this.description = description;
        this.link = link;
    }

    //endregion

}
