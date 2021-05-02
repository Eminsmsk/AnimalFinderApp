package com.example.animalfinder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Game extends AppCompatActivity implements CallBackInterface{
    private RecyclerView gameRV;
    private AnimalsAdapter adapter;
    private TextView textViewPoints;
    private TextView textViewLevel;
    private TextView textViewQuestion;
    private TextView textViewTime;
    private List<Animal> animalList;
    ArrayList<Animal> options = new ArrayList<>();
    private int level=1;
    private int point=0;
    private int count=0;
    private int question=0;
    private int time = 20;
    private final int decreaseBy = 2;
    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameRV = findViewById(R.id.gameRV);
        textViewPoints = findViewById(R.id.textViewPoints);
        textViewLevel = findViewById(R.id.textViewLevel);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewTime = findViewById(R.id.textViewTime);
        animalList = new ArrayList<>();



        animalList.add(new Animal("bear","bear","bear"));
        animalList.add(new Animal("bee","bee","bee"));
        animalList.add(new Animal("canary","canary","canary"));
         animalList.add(new Animal("cat","cat","cat"));
        animalList.add(new Animal("cow","cow","cow"));
       animalList.add(new Animal("cricket","cricket","cricket"));
        animalList.add(new Animal("dog","dog","dog"));
        animalList.add(new Animal("donkey","donkey","donkey"));
        animalList.add(new Animal("elephant","elephant","elephant"));
        animalList.add(new Animal("frog","frog","frog"));
        animalList.add(new Animal("goat","goat","goat"));
        animalList.add(new Animal("goose","goose","goose"));
        animalList.add(new Animal("horse","horse","horse"));
        animalList.add(new Animal("lion","lion","lion"));
        animalList.add(new Animal("monkey","monkey","monkey"));
        animalList.add(new Animal("owl","owl","owl"));
        animalList.add(new Animal("pig","pig","pig"));
        animalList.add(new Animal("raven","raven","raven"));
        animalList.add(new Animal("rooster","rooster","rooster"));
        animalList.add(new Animal("sheep","sheep","sheep"));
        animalList.add(new Animal("wolf","wolf","wolf"));
        Collections.shuffle(animalList); // ilk 15 i soruları oluşturuyor

        loadQuestion();


    }




    public void loadQuestion(){
        if(!options.isEmpty())
            options.clear();


        int numOfAnimals=0;
        switch (level){
            case 1: numOfAnimals=2; break;
            case 2: numOfAnimals=3; break;
            case 3: numOfAnimals=4; break;
            case 4: numOfAnimals=6; break;
            case 5: numOfAnimals=8; break;
        }

        for (int i = 0; i <numOfAnimals-1 ; i++) {
            Random rand = new Random();
            int randomNum = rand.nextInt(21) ;
            if(!options.contains(animalList.get(randomNum)) && !animalList.get(question).getAnimalName().equals(animalList.get(randomNum).getAnimalName())){
                options.add(animalList.get(randomNum));
            }
            else
                i--;
        }
        options.add(animalList.get(question));
        Collections.shuffle(options);
        gameRV.setHasFixedSize(true);
        gameRV.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        adapter = new AnimalsAdapter(this,options);
        gameRV.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        String q = "Find the "+animalList.get(question).getAnimalName()+", "
                +animalList.get(question).getAnimalName()+" makes the sound...(listen)";

        textViewQuestion.setText(q);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,animalList.get(question).getSoundName());

        MainActivity.getInstance().getTts().speak(q,TextToSpeech.QUEUE_ADD, params);



        countDownTimer = new CountDownTimer(time*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                textViewTime.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                textViewTime.setText("0");
                countDownTimer.cancel();
                MainActivity.getInstance().getTts().stop();
                MainActivity.getInstance().getMediaPlayer().release();
                MainActivity.getInstance().getTts().speak("Time is up!",TextToSpeech.QUEUE_FLUSH, null);
               // MainActivity.getInstance().getTts().stop();
                Intent intent = new Intent(Game.this,Result.class);
                intent.putExtra("point",point);
                startActivity(intent);
                finish();
            }
        }.start();


        question += 1;
    }

    public Boolean checkAnswer(String answer){

        if(MainActivity.getInstance().getTts().isSpeaking())
            MainActivity.getInstance().getTts().stop();

       MainActivity.getInstance().getMediaPlayer().release();

       countDownTimer.cancel();

        if(animalList.get(question-1).getAnimalName().equals(answer)){
            point+=1;
            count+=1;
            textViewPoints.setText(String.valueOf(point)+" Points");

            MainActivity.getInstance().getTts().speak("You got it!",TextToSpeech.QUEUE_ADD, null);
            return true;


        }else{

            MainActivity.getInstance().getTts().speak("Wrong answer!",TextToSpeech.QUEUE_ADD, null);
            Intent intent = new Intent(Game.this,Result.class);
            intent.putExtra("point",point);
            startActivity(intent);
            finish();
            return false;
        }


    }

    public void countConsecutive(){

        //Game is completed without wrong answer.
        if(count==3 && level==5){
            MainActivity.getInstance().getTts().speak("Great! You aced it!",TextToSpeech.QUEUE_FLUSH, null);
            Intent intent = new Intent(Game.this,Result.class);
            intent.putExtra("point",point);
            startActivity(intent);
            finish();
        }

        else if(count != 3){
            loadQuestion();
        }else{
            level+=1;
            count=0;
            time-=decreaseBy;
            textViewLevel.setText("Level "+String.valueOf(level));
            loadQuestion();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();
        MainActivity.getInstance().getTts().stop();
        MainActivity.getInstance().getMediaPlayer().release();
    }

}