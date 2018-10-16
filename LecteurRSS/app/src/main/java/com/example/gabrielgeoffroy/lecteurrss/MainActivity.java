package com.example.gabrielgeoffroy.lecteurrss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;

/**
 *
 */
public class MainActivity extends AppCompatActivity {
    ListView listView;
    DocumentBuilder builder;
    Document dom;
    String data;
    Lecteur lecteur = new Lecteur();
    List<Article> articles = new ArrayList<>();

    static public String[] urls = {"https://ici.radio-canada.ca/rss/4159",
            "http://www.lapresse.ca/rss.php",
            "http://rss.slashdot.org/Slashdot/slashdotMain",
            "https://www.commentcamarche.net/rss/",
            "http://www.developpez.com/index/rss",
            "http://feeds.twit.tv/sn.xml",
            "http://feeds.twit.tv/sn_video_hd.xml",
            "http://feeds.podtrac.com/9dPm65vdpLL1",
            "http://visualstudiotalkshow.libsyn.com/rss"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = this.findViewById(R.id.listeFlux);

        new Thread(new Runnable() {
            @Override
            public void run() {
                    try {
                        articles = lecteur.separerInfoArticle(lecteur.lireUrl(urls[0]));
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                List<String> titles = new ArrayList<>();
                                for (Article a: articles) {
                                    titles.add(a.titre);
                                }
                                ArrayAdapter<String> aa = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, titles);
                                listView.setAdapter(aa);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }).start();
    }
}
