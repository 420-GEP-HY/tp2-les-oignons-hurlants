package com.example.gabrielgeoffroy.lecteurrss;

import android.util.Xml;

import org.w3c.dom.Document;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;

/**
 * @author Arnaud Bégin, Carelle Chagnon, Gabriel Geoffroy, David Poissant Samson
 * Une librairie permettant de lire des flux RSS et désérialiser les données dans un modèle objet de votre conception.
 */
public class Lecteur {

    //region Variables
    public String[] urls = {"https://ici.radio-canada.ca/rss/",
            "http://www.lapresse.ca/rss.php",
            "http://rss.slashdot.org/Slashdot/slashdotMain",
            "https://www.commentcamarche.net/rss/",
            "http://www.developpez.com/index/rss",
            "http://feeds.twit.tv/sn.xml",
            "http://feeds.twit.tv/sn_video_hd.xml",
            "http://feeds.podtrac.com/9dPm65vdpLL1",
            "http://visualstudiotalkshow.libsyn.com/rss"};
    //endregion

    //region Méthodes

    /**
     * Méthode permettant de lire le contenu d'une page en passant par son URL.
     * @param urlString URL de la page à lire
     * @return Le contenu de la page
     * @throws IOException
     */
    public InputStream lireUrl(String urlString) throws IOException {
        DocumentBuilder builder;
        Document dom = null;

        URL url = new URL(urlString);
        InputStream inputStream = url.openConnection().getInputStream();
        return inputStream;
    }

    /**
     * Méthode permettant de séparer le contenu d'une page par ses balises.
     * @param inputStream Le contenu de la page
     * @return La liste des items contenus dans la page
     * @throws XmlPullParserException
     * @throws IOException
     */
    public List<Article> separerTexte(InputStream inputStream) throws XmlPullParserException, IOException {
        String titre = "";
        String link = "";
        String description = "";
        boolean isItem = false;
        List<Article> articles = new ArrayList<>();

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                int eventType = parser.getEventType();

                String name = parser.getName();
                if (name == null)
                    continue;

                if (eventType == XmlPullParser.END_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = false;
                    }
                    continue;
                }

                if (eventType == XmlPullParser.START_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = true;
                        continue;
                    }
                }

                String result = "";
                if (parser.next() == XmlPullParser.TEXT) {
                    result = parser.getText();
                    parser.nextTag();
                }

                if (name.equalsIgnoreCase("title")) {
                    titre = result;
                } else if (name.equalsIgnoreCase("link")) {
                    link = result;
                } else if (name.equalsIgnoreCase("description")) {
                    description = result;
                }

                if (titre != null && link != null && description != null) {
                    if (isItem) {
                        Article item = new Article(titre, link, description);
                        articles.add(item);
                    }

                    titre = null;
                    link = null;
                    description = null;
                    isItem = false;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            inputStream.close();
        }
        return articles;
    }

    /**
     * Méthode permettant de recueillir les informations d'une chaine (flux)
     * @param inputStream Le contenu de la page
     * @return La chaine (flux)
     * @throws XmlPullParserException
     * @throws IOException
     */
    public Chaine separerInfoChaine(InputStream inputStream) throws XmlPullParserException, IOException {

        Chaine chaine = new Chaine();

        String titre = "";
        String link = "";
        String description = "";
        String url = "";

        boolean isItem = false;

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                int eventType = parser.getEventType();

                String name = parser.getName();
                if (name == null)
                    continue;

                if (eventType == XmlPullParser.END_TAG) {
                    if (name.equalsIgnoreCase("image")) {
                        isItem = false;
                    }
                    continue;
                }

                if (eventType == XmlPullParser.START_TAG) {
                    if (name.equalsIgnoreCase("channel")) {
                        isItem = true;
                        continue;
                    }
                }

                String result = "";
                if (parser.next() == XmlPullParser.TEXT) {
                    result = parser.getText();
                    parser.nextTag();
                }

                if (name.equalsIgnoreCase("title")) {
                    titre = result;
                } else if (name.equalsIgnoreCase("description")) {
                    description = result;
                }else if (name.equalsIgnoreCase("url")) {
                    url = result;
                }

                if (titre != null && description != null) {
                    if (isItem) {
                        if(url != null) {
                            chaine = new Chaine(titre, link, description, url);
                        } else
                            chaine = new Chaine(titre, link, description, null);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            inputStream.close();
        }

        return chaine;
    }
    //endregion
}
