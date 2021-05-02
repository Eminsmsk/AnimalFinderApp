package com.example.animalfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button buttonPlay;
    private TextToSpeech tts;
    private MediaPlayer mediaPlayer;
    private static MainActivity instance = null;

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        mediaPlayer = new MediaPlayer();
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                    tts.setSpeechRate(0.75f);
                    UtteranceProgressListener listener = new UtteranceProgressListener() {

                        @Override
                        public void onStart(String utteranceId) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onError(String utteranceId) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onDone(String utteranceId) {
                            // TODO Auto-generated method stub
                            //start MediaPlayer when speaking is done
                            mediaPlayer = MediaPlayer.create(MainActivity.this,getResources().getIdentifier(utteranceId, "raw", getPackageName()));
                            mediaPlayer.start();
                        }
                    };
                    tts.setOnUtteranceProgressListener(listener);

                }
            }
        });


        buttonPlay = findViewById(R.id.buttonPlay);

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Game.class));

                //finish();
            }
        });

    }
    public TextToSpeech getTts() {
        return tts;
    }
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}