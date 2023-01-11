package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Menu extends AppCompatActivity implements View.OnClickListener {

    ImageView guessImg, quizImg, ticTac;
    TextView  quizGame, guessGame, ticTacGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        guessImg = (ImageView) findViewById(R.id.guess);
        guessImg.setOnClickListener(this);

        quizImg = (ImageView) findViewById(R.id.quiz);
        quizImg.setOnClickListener(this);
        ticTac = (ImageView) findViewById(R.id.ticTac);
        ticTac.setOnClickListener(this);

        quizGame = (TextView) findViewById(R.id.quizGame);
        guessGame = (TextView) findViewById(R.id.guessTheNumber);
        ticTacGame = (TextView) findViewById(R.id.ticGame);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.guess:
                startActivity(new Intent(this, Guess.class));
                break;
            case R.id.ticTac:
                startActivity(new Intent(this, TicTacToe.class));
                break;

            case R.id.quiz:
                startActivity(new Intent(this, Quizz.class));
                break;
        }
    }


}