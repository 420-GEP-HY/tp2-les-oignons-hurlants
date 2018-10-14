package com.example.gabrielgeoffroy.lecteurrss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FluxActivity extends AppCompatActivity {

    Lecteur lecteur = new Lecteur();

    // Test (À MODIFIER)
//    Chaine test = new Chaine("test");

    List<Chaine> flux =  new ArrayList<Chaine>();

    ArrayList<Chaine> chaines;
    ListView listView;
    ImageButton ajouter;
    EditText lien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flux);

//        flux.add(test);

        listView = this.findViewById(R.id.listeFlux);
        ajouter = this.findViewById(R.id.ajouter);
        lien = this.findViewById(R.id.lienHTTP);

        chaines = new ArrayList<Chaine>();

        for (Chaine chaine:flux){
            chaines.add(new Chaine(chaine.titre, chaine.lien, chaine.description, chaine.urlImage));
        }

        ArrayAdapter<Chaine> aa = new FluxAdapter(this, 0, chaines);
        listView.setAdapter(aa);

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
                                            chaines.clear();
                                            for (Chaine chaine : flux) {
                                                chaines.add(new Chaine(chaine.titre, chaine.lien, chaine.description, chaine.urlImage));
                                            }

                                            ArrayAdapter<Chaine> aa = new FluxAdapter(FluxActivity.this, 0, chaines);
                                            listView.setAdapter(aa);
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
}
