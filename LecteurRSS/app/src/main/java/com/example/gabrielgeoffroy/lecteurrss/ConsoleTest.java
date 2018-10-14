package com.example.gabrielgeoffroy.lecteurrss;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ConsoleTest {
    static Lecteur lecteur = new Lecteur();
    static public String[] urls = {"https://ici.radio-canada.ca/rss/4159",
            "http://www.lapresse.ca/rss.php",
            "http://rss.slashdot.org/Slashdot/slashdotMain",
            "https://www.commentcamarche.net/rss/",
            "http://www.developpez.com/index/rss",
            "http://feeds.twit.tv/sn.xml",
            "http://feeds.twit.tv/sn_video_hd.xml",
            "http://feeds.podtrac.com/9dPm65vdpLL1",
            "http://visualstudiotalkshow.libsyn.com/rss"};

    public static void main(String[] argv) throws XmlPullParserException {
        try {
//            List<Article> articles = lecteur.separerTexte(lecteur.lireUrl(urls[0]));
//            for (Article a:articles) {
//                System.out.println(a);
//            }
            Chaine chaine = lecteur.separerInfoChaine(lecteur.lireUrl(urls[0]));
            System.out.println(chaine.titre);
            System.out.println(chaine.description);
            System.out.println(chaine.lien);
            System.out.println(chaine.urlImage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
