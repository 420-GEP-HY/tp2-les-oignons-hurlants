package com.example.gabrielgeoffroy.lecteurrss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arnaud Bégin, Carelle Chagnon, Gabriel Geoffroy, David Poissant Samson
 * Activité permettant d'afficher les articles contenus dans un flux.
 */
public class ArticlesActivity extends AppCompatActivity {

    List<String> lus;
    ArrayList<Article> articles;
    LectureEcriture le;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        Intent intent = getIntent();
        articles = intent.getParcelableArrayListExtra("articles");
        le = new LectureEcriture();
        lv = findViewById(R.id.listView);
        List<String> titres = new ArrayList<String>();
        for (Article a : articles)
            titres.add(a.titre);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, titres);
        lv.setAdapter(aa);

        try {
            lus = le.LireArticlesLus("lus.bin", getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    // Créer une nouvelle activité (afficher les articles)
                    Intent détails = new Intent(getApplicationContext(), DetailsActivity.class);
                    Article a = articles.get(position);
                    détails.putExtra("article",a);
                    startActivity(détails);
            }
        });
    }

}
