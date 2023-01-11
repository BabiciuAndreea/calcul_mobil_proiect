package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class TicTacToe extends AppCompatActivity  implements View.OnClickListener{


    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button[] buttons = new Button[9];
    private Button resetGame, backButton;

    private int playerOneScoreCount, playerTwoScoreCount, rountCount;
    boolean activePlayer;

    int [] gameState = {2,2,2,2,2,2,2,2,2};

    int [][] winningPosition = {
            {0,1,2}, {3,4,5}, {6,7,8}, //linii
            {0,3,6}, {1,4,7}, {2,5,8}, //coloane
            {0,4,8}, {2,4,6} //diagonale
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore =(TextView) findViewById(R.id.playerTwoScore);
        playerStatus = (TextView) findViewById(R.id.playerStatus);

        backButton = (Button) findViewById(R.id.backToMenu);
        resetGame = (Button) findViewById(R.id.resetGame);

        for(int i=0; i < buttons.length; i++){
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        rountCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;


    }

    @Override
    public void onClick(View view) {
        if(!((Button)view).getText().toString().equals("")){
            return;
        }

        String buttonID = view.getResources().getResourceEntryName(view.getId()); //btn_2
        int gameStatePointer= Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length())); //2

        if(activePlayer){
            ((Button)view).setText("X");
            ((Button)view).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer]=0;

        } else {
            ((Button)view).setText("O");
            ((Button)view).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1;

        } rountCount++;

        if(checkWinner()){
            if(activePlayer){
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player One won", Toast.LENGTH_SHORT).show();
                playAgain();
            }else{
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player Two won", Toast.LENGTH_SHORT).show();
                playAgain();

            }

        }else if(rountCount == 9){
            playAgain();
            Toast.makeText(this, "No winner", Toast.LENGTH_SHORT).show();

        } else {
            activePlayer = !activePlayer;
        }

        if(playerOneScoreCount > playerTwoScoreCount){
            playerStatus.setText("Player One is winning");
        } else if(playerTwoScoreCount >playerOneScoreCount){
            playerStatus.setText("Player Two is winning");
        } else {
            playerStatus.setText("");
        }
        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                playerStatus.setText("");
                updatePlayerScore();
            }
        });
    }

    public boolean checkWinner(){
        boolean winnerResult = false;

        for(int [] winninPosition : winningPosition){
            if(gameState[winninPosition[0]] == gameState[winninPosition[1]] &&
                    gameState[winninPosition[1]] == gameState[winninPosition[2]] &&
                      gameState[winninPosition[0]] !=2){

                winnerResult = true;
            }
        }
        return winnerResult;
    }

    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));

    }

    public void playAgain(){
        rountCount = 0;
        activePlayer = true;

        for(int i=0; i<buttons.length;i++){
            gameState[i]=2;
            buttons[i].setText("");
        }
    }

    public void onClickBackToMenu(View view){
        startActivity(new Intent(this, Menu.class));
    }
}