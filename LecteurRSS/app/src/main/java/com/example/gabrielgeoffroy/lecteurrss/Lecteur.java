package com.example.gabrielgeoffroy.lecteurrss;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Xml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * 1 - Une librairie permettant de lire des flux RSS et désérialiser les données dans un modèle
 objet de votre conception. Ce modèle doit être suffisamment complet pour contenir les
 données nécessaires à la suite du TP.
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
    public InputStream lireUrl(String urlString) throws IOException {
        DocumentBuilder builder;
        Document dom = null;

        URL url = new URL(urlString);
        InputStream inputStream = url.openConnection().getInputStream();
        return inputStream;
    }

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
                            chaine = new Chaine(titre, link, description);
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

    private Bitmap getBitmapFromUrl(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoInput(true);
        connection.connect();

        InputStream input = connection.getInputStream();

        return BitmapFactory.decodeStream(input);
    }
    //endregion
}
