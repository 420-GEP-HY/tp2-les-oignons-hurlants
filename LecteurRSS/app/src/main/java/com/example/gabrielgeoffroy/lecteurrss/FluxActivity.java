package com.example.gabrielgeoffroy.lecteurrss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FluxActivity extends AppCompatActivity {

    // Test (À MODIFIER)
    Chaine test = new Chaine("test");

    Chaine[] flux = {
            test
    };

    ArrayList<Chaine> chaines;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flux);

        listView = this.findViewById(R.id.listeFlux);

        chaines = new ArrayList<Chaine>();

        for (Chaine chaine:flux){
            chaines.add(new Chaine(chaine.titre));
        }

        ArrayAdapter<Chaine> aa = new FluxAdapter(this, 0, chaines);

        listView.setAdapter(aa);
    }
}
