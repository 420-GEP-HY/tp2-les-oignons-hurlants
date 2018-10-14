package com.example.gabrielgeoffroy.lecteurrss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arnaud Bégin, Carelle Chagnon, Gabriel Geoffroy, David Poissant Samson
 * Activité permettant d'afficher les chaines (flux), d'en ajouter et d'en supprimer.
 */
public class FluxActivity extends AppCompatActivity {

    // Variables
    Lecteur lecteur = new Lecteur();
    List<Chaine> flux =  new ArrayList<Chaine>();
    ArrayList<Chaine> chaines;
    ListView listView;
    ImageButton ajouter;
    ImageButton supprimer;
    EditText lien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flux);

        // Association des composantes
        listView = this.findViewById(R.id.listeFlux);
        ajouter = this.findViewById(R.id.ajouter);
        supprimer = this.findViewById(R.id.supprimerFlux);
        lien = this.findViewById(R.id.lienHTTP);

        chaines = new ArrayList<Chaine>();

        // Ajout des chaines dans l'ArrayList
        for (Chaine chaine:flux){
            chaines.add(new Chaine(chaine.titre, chaine.lien, chaine.description, chaine.urlImage));
        }

        // Permet d'afficher les chaines dans le layout
        ArrayAdapter<Chaine> aa = new FluxAdapter(this, 0, chaines);
        listView.setAdapter(aa);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                // Supprimer un flux
                if(view.getId() == R.id.supprimerFlux)
                {
                    flux.remove(position);
                    mettreAJourVue();
                }
                else {
                    // Créer une nouvelle activité (afficher les articles)
                    Intent articles = new Intent(getApplicationContext(), ArticlesActivity.class);
                    startActivity(articles);
                }
            }
        });

        // Permet d'ajouter et d'afficher un flux
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lien.getText() != null)
                {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    flux.add(lecteur.separerInfoChaine(lecteur.lireUrl(lien.getText().toString())));
                                    FluxActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mettreAJourVue();
                                        }
                                    });
                                } catch (XmlPullParserException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
            }
        });
    }

    /**
     * Méthode permettant de mettre à jour la vue
     */
    private void mettreAJourVue()
    {
        chaines.clear();
        for (Chaine chaine : flux) {
            chaines.add(new Chaine(chaine.titre, chaine.lien, chaine.description, chaine.urlImage));
        }

        ArrayAdapter<Chaine> aa = new FluxAdapter(FluxActivity.this, 0, chaines);
        listView.setAdapter(aa);
    }
}
