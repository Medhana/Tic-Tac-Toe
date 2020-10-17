package com.example.tictactoe;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button[][] buttons = new Button[3][3];
    private boolean player1turn = true;
    private int roundCount, player1points, player2points;
    private TextView Player1,Player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Player1 = findViewById(R.id.player1);
        Player2 = findViewById(R.id.player2);

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                String buttonID="button"+i+j;
                int resID = getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][j]=findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(!((Button) view).getText().toString().equals("")){
            return;
        }
        if(player1turn){
            ((Button) view).setText("X");
            ((Button) view).setTextColor(Color.parseColor("#BF1313"));
        }
        else {
            ((Button) view).setText("O");
            ((Button) view).setTextColor(Color.parseColor("#2F8A0D"));
        }
        roundCount++;

        if(checkForWin()){
            if(player1turn){
                player1Wins();
            }else{
                player2Wins();
            }
        } else if(roundCount == 9){
            draw();
        }else {
            player1turn = !player1turn;
        }
    }
    private boolean checkForWin(){
        String[][] field = new String[3][3];

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for(int i=0;i<3;i++){
            if(field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")){
                return true;
            }
        }
        for(int i=0;i<3;i++){
            if(field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")){
                return true;
            }
        }
        if(field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")){
            return true;
        }
        if(field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")){
            return true;
        }
        return false;
    }

    private void player1Wins() {
        player1points++;
        Toast.makeText(this,"Player 1 WON!",Toast.LENGTH_LONG).show();
        updatePointsText();
        resetBoard();
    }
    private void player2Wins() {
        player2points++;
        Toast.makeText(this,"Player 2 WON!",Toast.LENGTH_LONG).show();
        updatePointsText();
        resetBoard();
    }
    private void draw() {
        Toast.makeText(this,"Draw! Draw! Draw!",Toast.LENGTH_LONG).show();
        resetBoard();
    }
    private void updatePointsText(){
        Player1.setText("Player 1: "+player1points);
        Player2.setText("Player 2: "+player2points);
    }
    private void resetBoard(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1turn = true;
    }
    private void resetGame(){
        player1points = 0;
        player2points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1points);
        outState.putBoolean("player1turn", player1turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1points = savedInstanceState.getInt("player1Points");
        player2points = savedInstanceState.getInt("player2Points");
        player1turn = savedInstanceState.getBoolean("player1Turn");
    }
}