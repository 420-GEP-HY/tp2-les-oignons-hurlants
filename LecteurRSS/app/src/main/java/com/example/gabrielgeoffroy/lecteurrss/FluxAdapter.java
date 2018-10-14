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

public class FluxAdapter extends ArrayAdapter<Chaine> {

    List<Chaine> chaines;
    Bitmap bitmap;

    public FluxAdapter(Context context, int resource, List<Chaine> objects) {
        super(context, resource, objects);
        this.chaines = objects;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.flux, parent, false);
        }

        final ImageView image = convertView.findViewById(R.id.imageFlux);
        TextView titre = convertView.findViewById(R.id.titreFlux);
        TextView nbArticles = convertView.findViewById(R.id.articlesNonLus);
        ImageButton supprimer = convertView.findViewById(R.id.supprimerFlux);
        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView)parent).performItemClick(v, position, 0);
            }
        });

        // AJOUTER LES AUTRES INFORMATIONS À AJOUTER
        titre.setText(chaines.get(position).titre);
//        nbArticles.setText(chaines.get(position));

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
            {
                image.setImageBitmap(bitmap);
            }
        }

        return convertView;
    }

    private Bitmap getBitmapFromUrl(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoInput(true);
        connection.connect();

        InputStream input = connection.getInputStream();

        return BitmapFactory.decodeStream(input);
    }
}
