package com.example.animalfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity {
    private TextView textViewMyScore;
    private TextView textViewHighestScore;
    private Button buttonPlayAgain;

    private int point;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewMyScore = findViewById(R.id.textViewMyScore);
        textViewHighestScore = findViewById(R.id.textViewHighestScore);
        buttonPlayAgain = findViewById(R.id.buttonPlayAgain);


        point = getIntent().getIntExtra("point",0);
        SharedPreferences sp = getSharedPreferences("highestScore",MODE_PRIVATE);
        Editor editor = sp.edit();
        int highestScore = sp.getInt("highestScore", 0);

        if(highestScore<point){
            editor.putInt("highestScore",point);
            editor.commit();
        }

        textViewMyScore.setText("Your Score: "+String.valueOf(point));
        textViewHighestScore.setText("The highest score: "+String.valueOf(sp.getInt("highestScore",0)));

        buttonPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Result.this,Game.class));
                finish();
            }
        });




    }
}