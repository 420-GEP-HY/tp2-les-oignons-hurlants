package com.example.gabrielgeoffroy.lecteurrss;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * @author Arnaud Bégin, Carelle Chagnon, Gabriel Geoffroy, David Poissant Samson
 * Classe permettant de faire afficher les chaines dans un layout custom
 */
public class FluxAdapter extends ArrayAdapter<Chaine> {

    // Variables
    List<Chaine> chaines;
    Bitmap bitmap;

    /**
     * Constructeur de l'adapteur de chaine
     * @param context Contexte de l'activité
     * @param resource 0
     * @param objects Liste de chaines
     */
    public FluxAdapter(Context context, int resource, List<Chaine> objects) {
        super(context, resource, objects);
        this.chaines = objects;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.flux, parent, false);
        }

        // Associations des composantes
        final ImageView image = convertView.findViewById(R.id.imageFlux);
        TextView titre = convertView.findViewById(R.id.titreFlux);
        TextView nbArticles = convertView.findViewById(R.id.articlesNonLus);
        ImageButton supprimer = convertView.findViewById(R.id.supprimerFlux);

        // Rend l'image cliquable
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView)parent).performItemClick(v, position, 0);
            }
        });

        // Rend le titre cliquable
        titre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView)parent).performItemClick(v, position, 0);
            }
        });

        // Rend le nombre d'articles à lire cliquable
        nbArticles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView)parent).performItemClick(v, position, 0);
            }
        });

        // Rend le bouton supprimer cliquable
        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView)parent).performItemClick(v, position, 0);
            }
        });

        // AJOUTER LES AUTRES INFORMATIONS À AJOUTER
        titre.setText(chaines.get(position).titre);

        if (chaines.get(position).articles.isEmpty())
        {
            nbArticles.setText("Aucun article non lu.");
        }
        else{
            nbArticles.setText(Integer.toString(chaines.get(position).articles.size()));
        }

        //Afficher l'image de la chaine
        if(chaines.get(position).urlImage != null)
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        bitmap = getBitmapFromUrl(new URL(chaines.get(position).urlImage));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            if(bitmap != null)
                image.setImageBitmap(bitmap);
        }

        return convertView;
    }

    /**
     * Méthode permettant de charger un image depuis son URL
     * @param url L'URL de l'image
     * @return L'image de la chaine
     * @throws IOException
     */
    protected Bitmap getBitmapFromUrl(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoInput(true);
        connection.connect();

        InputStream input = connection.getInputStream();

        return BitmapFactory.decodeStream(input);
    }
}
