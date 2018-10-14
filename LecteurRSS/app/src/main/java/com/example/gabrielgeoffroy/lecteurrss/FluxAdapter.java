package com.example.gabrielgeoffroy.lecteurrss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FluxAdapter extends ArrayAdapter<Chaine> {

    List<Chaine> chaines;

    public FluxAdapter(Context context, int resource, List<Chaine> objects) {
        super(context, resource, objects);
        this.chaines = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.flux, parent, false);
        }

        ImageView image = convertView.findViewById(R.id.imageFlux);
        TextView titre = convertView.findViewById(R.id.titreFlux);
        TextView nbArticles = convertView.findViewById(R.id.articlesNonLus);
        ImageButton supprimer = convertView.findViewById(R.id.supprimerFlux);

        // AJOUTER LES AUTRES INFORMATIONS À AJOUTER
        titre.setText(chaines.get(position).titre);

        return convertView;
    }
}
