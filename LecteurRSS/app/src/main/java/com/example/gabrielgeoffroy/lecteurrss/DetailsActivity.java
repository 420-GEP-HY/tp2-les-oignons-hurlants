package com.example.gabrielgeoffroy.lecteurrss;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

public class DetailsActivity extends AppCompatActivity {

    TextView titre;
    TextView contenuTexte;
    Button contenuSonore;
    Button contenuVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        titre = findViewById(R.id.titre);
        contenuTexte = findViewById(R.id.contenuTexte);
        contenuSonore = findViewById(R.id.contenuSonore);
        contenuVideo = findViewById(R.id.contenuVideo);

        Intent intent = getIntent();
        final Article a = intent.getParcelableExtra("article");

        if (a != null){
            titre.setText(a.titre);
            contenuTexte.setText(a.description);
            if (a.link != null && a.link != ""){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Lecteur lecteur = new Lecteur();

                        try {
                            InputStream is = lecteur.lireUrl(a.link);
                            String mimeType = URLConnection.guessContentTypeFromStream(is);
                            mimeType = mimeType.substring(0, mimeType.indexOf('/'));
                            switch(mimeType){
                                case "audio":
                                    MediaPlayer mediaPlayer = new MediaPlayer();
                                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                    mediaPlayer.setDataSource(a.link);
                                    mediaPlayer.prepare();
                                    mediaPlayer.start();
                                    break;
                                case "video":
                                    break;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).run();
            }
        }
    }
}
