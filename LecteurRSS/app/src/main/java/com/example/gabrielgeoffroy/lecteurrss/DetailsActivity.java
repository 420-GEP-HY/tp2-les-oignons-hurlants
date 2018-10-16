package com.example.gabrielgeoffroy.lecteurrss;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

/**
 * @author Arnaud Bégin, Carelle Chagnon, Gabriel Geoffroy, David Poissant Samson
 * Activité permettant d'afficher les détails d'un article.
 */
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

        //Va chercher article dans les extras du intent
        Intent intent = getIntent();
        final Article a = intent.getParcelableExtra("article"); //new Article("test", "test", "test", "http://www.podtrac.com/pts/redirect.mp4/cdn.twit.tv/video/sn/sn0684/sn0684_h264m_1280x720_1872.mp4", "video");

        if (a != null){
            titre.setText(a.titre);
            contenuTexte.setText(a.description);
            if (a.mediaLink != null && a.mediaLink != "" && a.mediaType != null && a.mediaType != ""){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        switch(a.mediaType){
                            case "audio":
                                contenuSonore.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        contenuSonore.setEnabled(false);
                                        MediaPlayer mPlayer = new MediaPlayer();
                                        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                        try{
                                            mPlayer.setDataSource(a.mediaLink);
                                            mPlayer.prepare();
                                            mPlayer.start();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                            @Override
                                            public void onCompletion(MediaPlayer mp) {
                                                contenuSonore.setEnabled(true);
                                            }
                                        });
                                    }
                                });
                                contenuSonore.setVisibility(View.VISIBLE);
                                break;
                            case "video":
                                contenuVideo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setDataAndType(Uri.parse(a.mediaLink), "video/*");
                                        startActivity(intent);
                                    }
                                });
                                contenuVideo.setVisibility(View.VISIBLE);
                                break;
                            default:
                                break;
                        }
                    }
                }).run();
            }
        }
    }


}
