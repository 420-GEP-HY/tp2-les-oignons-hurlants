package com.example.gabrielgeoffroy.lecteurrss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.util.List;

/**
 * @author Arnaud Bégin, Carelle Chagnon, Gabriel Geoffroy, David Poissant Samson
 * Activité permettant d'afficher les articles contenus dans un flux.
 */
public class ArticlesActivity extends AppCompatActivity {

    List<String> lus;
    List<Article> articles;
    LectureEcriture le;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        Intent intent = getIntent();
        articles = intent.getParcelableArrayListExtra("articles");
        le = new LectureEcriture();
        try {
            lus = le.LireArticlesLus("lus.bin", getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
